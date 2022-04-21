package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.nodes.controlflow.ForToNode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;
import org.flowsoft.flowg.symboltables.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

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

    private final static HashMap<TypePair, Type> GE_LE_EQGE_EQLE_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Boolean);
        }};

    private final static HashMap<TypePair, Type> EQ_TYPE_MAP = new HashMap<>() {
        {
           put(new TypePair(Type.Number, Type.Number), Type.Boolean);
           put(new TypePair(Type.Point, Type.Point), Type.Boolean);
           put(new TypePair(Type.Boolean, Type.Boolean), Type.Boolean);
        }};

    private final static HashMap<TypePair, Type> AND_OR_TYPE_MAP = new HashMap<>() {
        {
           put(new TypePair(Type.Boolean, Type.Boolean), Type.Boolean);
        }};

    private SymbolTable _symbolTable = new SymbolTable(null);
    private Type _functionReturnType = Type.Void;

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
    public Type Visit(LineNode lineNode) throws TypeException {
        var params = lineNode.GetChild().GetChildren();
        if (params.size() != 1) {
            throw new TypeException();
        }

        var type = params.get(0).Accept(this);
        if (type != Type.Point) {
            throw new TypeException();
        }

        return Type.Void;
    }

    // Handles builtin function typechecking on the form number func(number)
    private Type TypeCheckMathFunc(UnaryNode<ActualParameterListNode> funcNode) throws TypeException {
        var params = funcNode.GetChild().GetChildren();
        if (params.size() != 1) {
            throw new TypeException();
        }

        var type = params.get(0).Accept(this);
        if (type != Type.Number) {
            throw new TypeException();
        }

        return Type.Number;
    }

    @Override
    public Type Visit(SqrtNode sqrtNode) throws TypeException {
        return TypeCheckMathFunc(sqrtNode);
    }

    @Override
    public Type Visit(SinNode sinNode) throws TypeException {
        return TypeCheckMathFunc(sinNode);
    }

    @Override
    public Type Visit(CosNode cosNode) throws TypeException {
        return TypeCheckMathFunc(cosNode);
    }

    @Override
    public Type Visit(TanNode tanNode) throws TypeException {
        return TypeCheckMathFunc(tanNode);
    }

    @Override
    public Type Visit(ArcsinNode arcsinNode) throws TypeException {
        return TypeCheckMathFunc(arcsinNode);
    }

    @Override
    public Type Visit(ArccosNode arccosNode) throws TypeException {
        return TypeCheckMathFunc(arccosNode);
    }

    @Override
    public Type Visit(ArctanNode arctanNode) throws TypeException {
        return TypeCheckMathFunc(arctanNode);
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
        var statementList = functionDefinitionNode.GetStatementListNode();

        var parentSymbolTable = _symbolTable.Clone();
        var bodySymbolTable = new SymbolTable(parentSymbolTable);
        for (var param : formalParams.GetChildren()) {
            bodySymbolTable.Enter(param.GetRightChild().GetValue(), param.GetLeftChild().GetValue());
        }

        parentSymbolTable.Enter(returnType, identifier, (ArrayList<FormalParameterNode>) formalParams.GetChildren(), functionDefinitionNode.GetStatementListNode(), bodySymbolTable);
        _symbolTable.Enter(returnType, identifier, (ArrayList<FormalParameterNode>) formalParams.GetChildren(), functionDefinitionNode.GetStatementListNode(), bodySymbolTable);

        var oldFunctionReturnType = _functionReturnType;
        var oldSymbolTable = _symbolTable;
        _functionReturnType = returnType;
        _symbolTable = bodySymbolTable;

        statementList.Accept(this);

        _symbolTable = oldSymbolTable;
        _functionReturnType = oldFunctionReturnType;

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
    public Type Visit(PowerExpressionNode powerExpressionNode) throws TypeException {
        var leftType = powerExpressionNode.GetLeftChild().Accept(this);
        var rightType = powerExpressionNode.GetRightChild().Accept(this);

        if (leftType == rightType && leftType == Type.Number) {
            return Type.Number;
        }

        throw new TypeException();
    }
    
    @Override
    public Type Visit(NotExpressionNode notExpressionNode) throws TypeException {
        var childType = notExpressionNode.GetChild().Accept(this);

        if (childType != Type.Boolean) {
            throw new TypeException();
        }

        return Type.Boolean;
    }

    @Override
    public Type Visit(IdentifierExpressionNode identifierExpressionNode) throws TypeException {
        var identifier = identifierExpressionNode.GetChild().GetValue();
        return _symbolTable.LookupVariable(identifier).GetType();
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
    public Type Visit(ReturnNode returnNode) throws TypeException {
        Type type = Type.Void;
        var child = returnNode.GetChild();
        if (child != null) {
            type = child.Accept(this);
        }

        if (type != _functionReturnType) {
            throw new TypeException();
        }

        return null;
    }

    @Override
    public Type Visit(AssignmentNode assignmentNode) throws TypeException {
        var identifier = assignmentNode.GetLeftChild().GetValue();

        var identifierType = _symbolTable.LookupVariable(identifier).GetType();
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

    @Override
    public Type Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws TypeException {
        var leftType = greaterThanExpressionNode.GetLeftChild().Accept(this);
        var rightType = greaterThanExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, GE_LE_EQGE_EQLE_TYPE_MAP);
    }

    @Override
    public Type Visit(LessThanExpressionNode lessThanExpressionNode) throws TypeException {
        var leftType = lessThanExpressionNode.GetLeftChild().Accept(this);
        var rightType = lessThanExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, GE_LE_EQGE_EQLE_TYPE_MAP);
    }

    @Override
    public Type Visit(EqualsExpressionNode equalsExpressionNode) throws TypeException {
        var leftType = equalsExpressionNode.GetLeftChild().Accept(this);
        var rightType = equalsExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, EQ_TYPE_MAP);
    }

    @Override
    public Type Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws TypeException {
        var leftType = greaterThanEqualsExpressionNode.GetLeftChild().Accept(this);
        var rightType = greaterThanEqualsExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, GE_LE_EQGE_EQLE_TYPE_MAP);
    }

    @Override
    public Type Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws TypeException {
        var leftType = lessThanEqualsExpressionNode.GetLeftChild().Accept(this);
        var rightType = lessThanEqualsExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, GE_LE_EQGE_EQLE_TYPE_MAP);
    }

    @Override
    public Type Visit(AndExpressionNode andExpressionNode) throws TypeException {
        var leftType = andExpressionNode.GetLeftChild().Accept(this);
        var rightType = andExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, AND_OR_TYPE_MAP);
    }

    @Override
    public Type Visit(OrExpressionNode orExpressionNode) throws TypeException {
        var leftType = orExpressionNode.GetLeftChild().Accept(this);
        var rightType = orExpressionNode.GetRightChild().Accept(this);
        return TypePair.TryBothWays(leftType, rightType, AND_OR_TYPE_MAP);
    }

    private Type PlusMinusTypeCheckExpr(Type leftType, Type rightType) throws TypeException {
        return TypePair.TryBothWays(leftType, rightType, PLUS_MINUS_TYPE_MAP);
    }

    private Type TimesDivideTypeCheckExpr(Type leftType, Type rightType) throws TypeException {
        return TypePair.TryBothWays(leftType, rightType, MULTIPLY_DIVIDE_TYPE_MAP);
    }
}
