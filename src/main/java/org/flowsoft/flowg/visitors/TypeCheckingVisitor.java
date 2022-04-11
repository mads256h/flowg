package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class TypeCheckingVisitor implements IVisitor<Type, TypeException>{

    private final static HashMap<TypePair, Type> PLUS_MINUS_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Number);
            put(new TypePair(Type.Point, Type.Point), Type.Point);
        }};

    private final static HashMap<TypePair, Type> MULTIPLY_DIVIDE_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Number);
            put(new TypePair(Type.Point, Type.Number), Type.Point);
        }};

    private final SymbolTable _symbolTable = new SymbolTable();

    public void PrintSymbolTable() {
        System.out.println("Printing symbol table");
        _symbolTable.Print();
    }

    public SymbolTable GetSymbolTable() {
        return _symbolTable;
    }

    @Override
    public Type Visit(StatementListNode statementListNode) throws TypeException {

        for (var child : statementListNode.GetChildren()) {
            child.Accept(this);
        }

        // Statements do not have a type
        return null;
    }

    @Override
    public Type Visit(MoveNode moveNode) throws TypeException {
        var params = moveNode.GetChild().GetChildren();
        if (params.size() != 1) {
            throw new TypeException();
        }

        var type = params.get(0).Accept(this);
        if (type != Type.Point) {
            throw new TypeException();
        }

        return Type.Void;
    }

    @Override
    public Type Visit(ActualParameterListNode actualParameterListNode) throws TypeException {
        //TODO look up symbol table for expected types
        for (var child : actualParameterListNode.GetChildren()) {
            child.Accept(this);
        }
        return null;
    }

    @Override
    public Type Visit(FormalParameterListNode formalParameterListNode) throws TypeException {
        return null;
    }

    @Override
    public Type Visit(FormalParameterNode formalParameterNode) throws TypeException {
        return null;
    }

    @Override
    public Type Visit(DeclarationNode declarationNode) throws TypeException {
        var typeNode = declarationNode.GetFirstNode();
        var identifierNode = declarationNode.GetSecondNode();
        var expressionNode = declarationNode.GetThirdNode();

        var typeNodeType = typeNode.Accept(this);
        var expressionNodeType = expressionNode.Accept(this);
        if (typeNodeType == expressionNodeType) {
            _symbolTable.Enter(identifierNode.GetValue(), typeNodeType);
            return typeNodeType;
        }
        else {
            throw new TypeException();
        }
    }

    @Override
    public Type Visit(FunctionDefinitionNode functionDefinitionNode) throws TypeException {
        var returnType = functionDefinitionNode.GetTypeNode().Accept(this);
        var identifier = functionDefinitionNode.GetIdentifierNode().GetValue();
        var formalParams = functionDefinitionNode.GetFormalParameterListNode();

        _symbolTable.Enter(returnType, identifier, (ArrayList<FormalParameterNode>) formalParams.GetChildren(), functionDefinitionNode.GetStatementListNode());
        return null;
    }

    @Override
    public Type Visit(TypeNode typeNode) {
        return typeNode.GetValue();
    }

    @Override
    public Type Visit(IdentifierNode identifierNode) throws TypeException {
        throw new RuntimeException("This should never be visited");
    }

    @Override
    public Type Visit(NumberLiteralNode numberLiteralNode) {
        return Type.Number;
    }

    @Override
    public Type Visit(BooleanLiteralNode booleanLiteralNode) {
        return Type.Boolean;
    }

    @Override
    public Type Visit(PointNode pointNode) throws TypeException {
        var firstType = pointNode.GetFirstNode().Accept(this);
        var secondType = pointNode.GetSecondNode().Accept(this);
        var thirdType = pointNode.GetThirdNode().Accept(this);

        if (firstType != Type.Number || secondType != Type.Number || thirdType != Type.Number) {
            throw new TypeException();
        }

        return Type.Point;
    }

    @Override
    public Type Visit(PlusExpressionNode plusExpressionNode) throws TypeException {
        var leftType = plusExpressionNode.GetLeftChild().Accept(this);
        var rightType = plusExpressionNode.GetRightChild().Accept(this);
        return PlusMinusTypeCheckExpr(leftType, rightType);
    }

    @Override
    public Type Visit(MinusExpressionNode minusExpressionNode) throws TypeException {
        var leftType = minusExpressionNode.GetLeftChild().Accept(this);
        var rightType = minusExpressionNode.GetRightChild().Accept(this);
        return PlusMinusTypeCheckExpr(leftType, rightType);
    }

    @Override
    public Type Visit(TimesExpressionNode multiplyExpressionNode) throws TypeException {
        var leftType = multiplyExpressionNode.GetLeftChild().Accept(this);
        var rightType = multiplyExpressionNode.GetRightChild().Accept(this);
        return TimesDivideTypeCheckExpr(leftType, rightType);
    }

    @Override
    public Type Visit(DivideExpressionNode divisionExpressionNode) throws TypeException {
        var leftType = divisionExpressionNode.GetLeftChild().Accept(this);
        var rightType = divisionExpressionNode.GetRightChild().Accept(this);
        return TimesDivideTypeCheckExpr(leftType, rightType);
    }

    @Override
    public Type Visit(IdentifierExpressionNode identifierExpressionNode) throws TypeException {
        var identifier = identifierExpressionNode.GetChild().GetValue();
        return _symbolTable.LookupVariable(identifier).Type;
    }

    @Override
    public Type Visit(FunctionCallNode functionCallNode) throws TypeException {
        var identifier = functionCallNode.GetLeftChild().GetValue();
        var paramList = functionCallNode.GetRightChild().GetChildren();

        var functionEntry = _symbolTable.LookupFunction(identifier);
        var expectedTypes = functionEntry.GetFormalParameters();

        if (paramList.size() != expectedTypes.size()) {
            throw new TypeException();
        }

        for (int i = 0; i < paramList.size(); i++) {
            if (paramList.get(i).Accept(this) != expectedTypes.get(i).GetLeftChild().GetValue()) {
                throw new TypeException();
            }
        }

        return functionEntry.GetReturnType();
    }

    @Override
    public Type Visit(AssignmentNode assignmentNode) throws TypeException {
        var identifier = assignmentNode.GetLeftChild().GetValue();

        var identifierType = _symbolTable.LookupVariable(identifier).Type;
        var expressionType = assignmentNode.GetRightChild().Accept(this);

        if (identifierType != expressionType) {
            throw new TypeException();
        }

        return null;
    }

    @Override
    public Type Visit(ForToNode forToNode) throws TypeException {
        var declNode = forToNode.GetFirstNode();
        declNode.Accept(this);
        var declType = declNode.GetFirstNode().GetValue();

        var exprNode = forToNode.GetSecondNode();
        var toType = exprNode.Accept(this);

        if (declType != Type.Number && toType != Type.Number) {
            throw new TypeException();
        }

        forToNode.GetThirdNode().Accept(this);

        return null;
    }

    private Type PlusMinusTypeCheckExpr(Type leftType, Type rightType) throws TypeException {
        return TypePair.TryBothWays(leftType, rightType, PLUS_MINUS_TYPE_MAP);
    }

    private Type TimesDivideTypeCheckExpr(Type leftType, Type rightType) throws TypeException {
        return TypePair.TryBothWays(leftType, rightType, MULTIPLY_DIVIDE_TYPE_MAP);
    }
}
