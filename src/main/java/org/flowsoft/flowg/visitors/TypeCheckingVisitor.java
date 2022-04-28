package org.flowsoft.flowg.visitors;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import org.flowsoft.flowg.*;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.INode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.nodes.controlflow.ForToNode;
import org.flowsoft.flowg.nodes.controlflow.IfElseNode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;
import org.flowsoft.flowg.symboltables.SymbolTable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TypeCheckingVisitor implements IVisitor<Type, TypeException> {

    private final static HashMap<TypePair, Type> PLUS_MINUS_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Number);
            put(new TypePair(Type.Void, Type.Number), Type.Number);
            put(new TypePair(Type.Point, Type.Point), Type.Point);
        }
    };

    private final static HashMap<TypePair, Type> MULTIPLY_DIVIDE_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Number);
            put(new TypePair(Type.Point, Type.Number), Type.Point);
        }
    };

    private final static HashMap<TypePair, Type> EQ_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), Type.Boolean);
            put(new TypePair(Type.Point, Type.Point), Type.Boolean);
            put(new TypePair(Type.Boolean, Type.Boolean), Type.Boolean);
        }
    };

    private final static HashMap<TypePair, Type> AND_OR_TYPE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Boolean, Type.Boolean), Type.Boolean);
        }
    };

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
    public Type Visit(IncludeSysNode includeSysNode) throws TypeException {
        String filepath = "include/" + includeSysNode.GetChild().GetValue();

        Yylex yylex;
        try {
            yylex = new Yylex(new FileReader(filepath), filepath);
        } catch (FileNotFoundException e) {
            throw new IncludeNotFoundException(includeSysNode.GetChild().GetValue(), includeSysNode.GetLeft(), includeSysNode.GetRight());
        }

        parser parser = new parser(yylex, new ComplexSymbolFactory());

        Symbol symbol;
        try {
            symbol = parser.parse();
        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(e);
        }

        INode rootNode = (INode) symbol.value;
        rootNode.Accept(this);

        return null;
    }

    @Override
    public Type Visit(IncludeUserNode includeUserNode) throws TypeException {
        String filepath = includeUserNode.GetChild().GetValue();

        Yylex yylex;
        try {
            yylex = new Yylex(new FileReader(filepath), filepath);
        } catch (FileNotFoundException e) {
            throw new IncludeNotFoundException(includeUserNode.GetChild().GetValue(), includeUserNode.GetLeft(), includeUserNode.GetRight());
        }

        parser parser = new parser(yylex, new ComplexSymbolFactory());

        Symbol symbol;
        try {
            symbol = parser.parse();
        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(e);
        }

        INode rootNode = (INode) symbol.value;
        rootNode.Accept(this);

        return null;
    }

    @Override
    public Type Visit(SysStringNode systringNode) throws TypeException {
        return null;
    }

    @Override
    public Type Visit(UserStringNode userStringNode) throws TypeException {
        return null;
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
            throw new ParameterCountException(1, params.size(), moveNode.GetChild().GetLeft(), moveNode.GetChild().GetRight());
        }

        var param = params.get(0);
        var type = param.Accept(this);
        if (type != Type.Point) {
            throw new ExpectedTypeException(Type.Point, type, param.GetLeft(), param.GetRight());
        }

        return Type.Void;
    }

    @Override
    public Type Visit(LineNode lineNode) throws TypeException {
        var params = lineNode.GetChild().GetChildren();
        if (params.size() != 1) {
            throw new ParameterCountException(1, params.size(), lineNode.GetChild().GetLeft(), lineNode.GetChild().GetRight());
        }

        var param = params.get(0);
        var type = param.Accept(this);
        if (type != Type.Point) {
            throw new ExpectedTypeException(Type.Point, type, param.GetLeft(), param.GetRight());
        }

        return Type.Void;
    }

    @Override
    public Type Visit(CWArcNode cwArcNode) throws TypeException {
        var params = cwArcNode.GetChild().GetChildren();
        if (params.size() != 2) {
            throw new ParameterCountException(2, params.size(), cwArcNode.GetChild().GetLeft(), cwArcNode.GetChild().GetRight());
        }

        var firstParam = params.get(0);
        var firstType = firstParam.Accept(this);
        if (firstType != Type.Point) {
            throw new ExpectedTypeException(Type.Point, firstType, firstParam.GetLeft(), firstParam.GetRight());
        }

        var lastParam = params.get(1);
        var lastType = lastParam.Accept(this);
        if (lastType != Type.Point) {
            throw new ExpectedTypeException(Type.Point, lastType, lastParam.GetLeft(), lastParam.GetRight());
        }

        return Type.Void;
    }

    @Override
    public Type Visit(CCWArcNode ccwArcNode) throws TypeException {
        return null;
    }

    // Handles builtin function typechecking on the form number func(number)
    private Type TypeCheckMathFunc(UnaryNode<ActualParameterListNode> funcNode) throws TypeException {
        var params = funcNode.GetChild().GetChildren();
        if (params.size() != 1) {
            throw new ParameterCountException(1, params.size(), funcNode.GetChild().GetLeft(), funcNode.GetChild().GetRight());
        }

        var param = params.get(0);
        var type = param.Accept(this);
        if (type != Type.Number) {
            throw new ExpectedTypeException(Type.Number, type, param.GetLeft(), param.GetRight());
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
            _symbolTable.Enter(identifierNode.GetValue(), typeNodeType, declarationNode.GetLeft(), declarationNode.GetRight());
            return typeNodeType;
        } else {
            throw new ExpectedTypeException(typeNodeType, expressionNodeType, expressionNode.GetLeft(), expressionNode.GetRight());
        }
    }

    @Override
    public Type Visit(FunctionDefinitionNode functionDefinitionNode) throws TypeException {
        var returnType = functionDefinitionNode.GetTypeNode().Accept(this);
        var identifierNode = functionDefinitionNode.GetIdentifierNode();
        var identifier = identifierNode.GetValue();
        var formalParams = functionDefinitionNode.GetFormalParameterListNode();
        var statementList = functionDefinitionNode.GetStatementListNode();

        var parentSymbolTable = _symbolTable.Clone();
        var bodySymbolTable = new SymbolTable(parentSymbolTable);
        for (var param : formalParams.GetChildren()) {
            bodySymbolTable.Enter(param.GetRightChild().GetValue(), param.GetLeftChild().GetValue(), param.GetLeft(), param.GetRight());
        }

        parentSymbolTable.Enter(returnType, identifier, (ArrayList<FormalParameterNode>) formalParams.GetChildren(), functionDefinitionNode.GetStatementListNode(), bodySymbolTable, identifierNode.GetLeft(), identifierNode.GetRight());
        _symbolTable.Enter(returnType, identifier, (ArrayList<FormalParameterNode>) formalParams.GetChildren(), functionDefinitionNode.GetStatementListNode(), bodySymbolTable, identifierNode.GetLeft(), identifierNode.GetRight());

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
        var firstNode = pointNode.GetFirstNode();
        var secondNode = pointNode.GetSecondNode();
        var thirdNode = pointNode.GetThirdNode();
        var firstType = firstNode.Accept(this);
        var secondType = secondNode.Accept(this);
        var thirdType = thirdNode.Accept(this);

        if (firstType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, firstType, firstNode.GetLeft(), firstNode.GetRight());
        }

        if (secondType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, secondType, secondNode.GetLeft(), secondNode.GetRight());
        }

        if (thirdType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, thirdType, thirdNode.GetLeft(), thirdNode.GetRight());
        }

        return Type.Point;
    }

    @Override
    public Type Visit(PointEntryNode pointEntryNode) throws TypeException {
        var expressionNode = pointEntryNode.GetLeftChild();
        var expressionType = expressionNode.Accept(this);
        var identifierNode = pointEntryNode.GetRightChild();
        var identifier = identifierNode.GetValue();

        if (expressionType != Type.Point) {
            throw new ExpectedTypeException(Type.Point, expressionType, expressionNode.GetLeft(), expressionNode.GetRight());
        }

        if (!(identifier.equals("x") || identifier.equals("y") || identifier.equals("z"))) {
            throw new SymbolNotFoundException(identifier, identifierNode.GetLeft(), identifierNode.GetRight());
        }

        return Type.Number;
    }

    @Override
    public Type Visit(PlusExpressionNode plusExpressionNode) throws TypeException {
        var leftType = plusExpressionNode.GetLeftChild().Accept(this);
        var rightType = plusExpressionNode.GetRightChild().Accept(this);

        return PlusMinusTypeCheckExpr(leftType, rightType, plusExpressionNode);
    }

    @Override
    public Type Visit(MinusExpressionNode minusExpressionNode) throws TypeException {
        var leftType = minusExpressionNode.GetLeftChild().Accept(this);
        var rightType = minusExpressionNode.GetRightChild().Accept(this);

        return PlusMinusTypeCheckExpr(leftType, rightType, minusExpressionNode);
    }

    @Override
    public Type Visit(TimesExpressionNode multiplyExpressionNode) throws TypeException {
        var leftType = multiplyExpressionNode.GetLeftChild().Accept(this);
        var rightType = multiplyExpressionNode.GetRightChild().Accept(this);

        return TimesDivideTypeCheckExpr(leftType, rightType, multiplyExpressionNode);
    }

    @Override
    public Type Visit(DivideExpressionNode divisionExpressionNode) throws TypeException {
        var leftType = divisionExpressionNode.GetLeftChild().Accept(this);
        var rightType = divisionExpressionNode.GetRightChild().Accept(this);

        return TimesDivideTypeCheckExpr(leftType, rightType, divisionExpressionNode);
    }

    @Override
    public Type Visit(PowerExpressionNode powerExpressionNode) throws TypeException {
        var leftNode = powerExpressionNode.GetLeftChild();
        var rightNode = powerExpressionNode.GetRightChild();

        var leftType = leftNode.Accept(this);
        var rightType = rightNode.Accept(this);

        if (leftType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, leftType, leftNode.GetLeft(), leftNode.GetRight());
        }

        if (rightType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, rightType, rightNode.GetLeft(), rightNode.GetRight());
        }

        return Type.Number;
    }

    @Override
    public Type Visit(ArithmeticNegationExpressionNode arithmeticNegationExpressionNode) throws TypeException {
        var childNode = arithmeticNegationExpressionNode.GetChild();
        var childType = childNode.Accept(this);

        if (childType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, childType, childNode.GetLeft(), childNode.GetRight());
        }

        return Type.Number;
    }

    @Override
    public Type Visit(NotExpressionNode notExpressionNode) throws TypeException {
        var childNode = notExpressionNode.GetChild();
        var childType = childNode.Accept(this);

        if (childType != Type.Boolean) {
            throw new ExpectedTypeException(Type.Boolean, childType, childNode.GetLeft(), childNode.GetRight());
        }

        return Type.Boolean;
    }

    @Override
    public Type Visit(IdentifierExpressionNode identifierExpressionNode) throws TypeException {
        var identifier = identifierExpressionNode.GetChild().GetValue();
        return _symbolTable.LookupVariable(identifier, identifierExpressionNode.GetLeft(), identifierExpressionNode.GetRight()).GetType();
    }

    @Override
    public Type Visit(FunctionCallNode functionCallNode) throws TypeException {
        var identifierNode = functionCallNode.GetLeftChild();
        var identifier = identifierNode.GetValue();
        var paramNode = functionCallNode.GetRightChild();
        var paramList = paramNode.GetChildren();

        var functionEntry = _symbolTable.LookupFunction(identifier, identifierNode.GetLeft(), identifierNode.GetRight());
        var expectedTypes = functionEntry.GetFormalParameters();

        if (paramList.size() != expectedTypes.size()) {
            throw new ParameterCountException(expectedTypes.size(), paramList.size(), paramNode.GetLeft(), paramNode.GetRight());
        }

        for (int i = 0; i < paramList.size(); i++) {
            var expectedType = expectedTypes.get(i).GetLeftChild().GetValue();
            var actualNode = paramList.get(i);
            var actualType = actualNode.Accept(this);
            if (actualType != expectedType) {
                throw new ExpectedTypeException(expectedType, actualType, actualNode.GetLeft(), actualNode.GetRight());
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
            if (child != null) {
                throw new ExpectedTypeException(_functionReturnType, type, child.GetLeft(), child.GetRight());
            } else {
                throw new ExpectedTypeException(_functionReturnType, type, returnNode.GetLeft(), returnNode.GetRight());
            }
        }

        return null;
    }

    @Override
    public Type Visit(IfElseNode ifElseNode) throws TypeException {
        var expressionNode = ifElseNode.GetFirstNode();
        var expressionType = expressionNode.Accept(this);

        if (expressionType != Type.Boolean) {
            throw new ExpectedTypeException(Type.Boolean, expressionType, expressionNode.GetLeft(), expressionNode.GetRight());
        }

        ifElseNode.GetSecondNode().Accept(this);

        if (ifElseNode.GetThirdNode() != null) {
            ifElseNode.GetThirdNode().Accept(this);
        }

        return null;
    }

    @Override
    public Type Visit(AssignmentNode assignmentNode) throws TypeException {
        var identifier = assignmentNode.GetLeftChild().GetValue();

        var identifierType = _symbolTable.LookupVariable(identifier, assignmentNode.GetLeft(), assignmentNode.GetRight()).GetType();

        var expressionNode = assignmentNode.GetRightChild();
        var expressionType = expressionNode.Accept(this);

        if (identifierType != expressionType) {
            throw new ExpectedTypeException(identifierType, expressionType, expressionNode.GetLeft(), expressionNode.GetRight());
        }

        return null;
    }

    @Override
    public Type Visit(ForToNode forToNode) throws TypeException {
        var declNode = forToNode.GetFirstNode();
        declNode.Accept(this);
        var declType = declNode.GetFirstNode().GetValue();

        var toNode = forToNode.GetSecondNode();
        var toType = toNode.Accept(this);

        if (declType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, declType, declNode.GetLeft(), declNode.GetRight());
        }

        if (toType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, toType, toNode.GetLeft(), toNode.GetRight());
        }

        forToNode.GetThirdNode().Accept(this);

        return null;
    }

    private Type TypeCheckGreaterThanTypeOperators(ExpressionNode left, ExpressionNode right) throws TypeException {
        var leftType = left.Accept(this);
        var rightType = right.Accept(this);

        if (leftType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, leftType, left.GetLeft(), left.GetRight());
        }

        if (rightType != Type.Number) {
            throw new ExpectedTypeException(Type.Number, rightType, right.GetLeft(), right.GetRight());
        }

        return Type.Boolean;
    }

    @Override
    public Type Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws TypeException {
        return TypeCheckGreaterThanTypeOperators(greaterThanExpressionNode.GetLeftChild(), greaterThanExpressionNode.GetRightChild());
    }

    @Override
    public Type Visit(LessThanExpressionNode lessThanExpressionNode) throws TypeException {
        return TypeCheckGreaterThanTypeOperators(lessThanExpressionNode.GetLeftChild(), lessThanExpressionNode.GetRightChild());
    }

    @Override
    public Type Visit(EqualsExpressionNode equalsExpressionNode) throws TypeException {
        var leftType = equalsExpressionNode.GetLeftChild().Accept(this);
        var rightType = equalsExpressionNode.GetRightChild().Accept(this);
        var maybe_type = TypePair.TryBothWays(leftType, rightType, EQ_TYPE_MAP);

        if (maybe_type.isPresent()) {
            return maybe_type.get();
        }

        throw new TypeMismatchException(leftType, rightType, equalsExpressionNode.GetLeft(), equalsExpressionNode.GetRight());
    }

    @Override
    public Type Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws TypeException {
        return TypeCheckGreaterThanTypeOperators(greaterThanEqualsExpressionNode.GetLeftChild(), greaterThanEqualsExpressionNode.GetRightChild());
    }

    @Override
    public Type Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws TypeException {
        return TypeCheckGreaterThanTypeOperators(lessThanEqualsExpressionNode.GetLeftChild(), lessThanEqualsExpressionNode.GetRightChild());
    }

    @Override
    public Type Visit(AndExpressionNode andExpressionNode) throws TypeException {
        var leftType = andExpressionNode.GetLeftChild().Accept(this);
        var rightType = andExpressionNode.GetRightChild().Accept(this);
        var maybe_type = TypePair.TryBothWays(leftType, rightType, AND_OR_TYPE_MAP);

        if (maybe_type.isPresent()) {
            return maybe_type.get();
        }

        throw new TypeMismatchException(leftType, rightType, andExpressionNode.GetLeft(), andExpressionNode.GetRight());
    }

    @Override
    public Type Visit(OrExpressionNode orExpressionNode) throws TypeException {
        var leftType = orExpressionNode.GetLeftChild().Accept(this);
        var rightType = orExpressionNode.GetRightChild().Accept(this);
        var maybe_type = TypePair.TryBothWays(leftType, rightType, AND_OR_TYPE_MAP);

        if (maybe_type.isPresent()) {
            return maybe_type.get();
        }

        throw new TypeMismatchException(leftType, rightType, orExpressionNode.GetLeft(), orExpressionNode.GetRight());
    }

    private Type PlusMinusTypeCheckExpr(Type leftType, Type rightType, ExpressionNode node) throws TypeException {
        var maybe_type = TypePair.TryBothWays(leftType, rightType, PLUS_MINUS_TYPE_MAP);

        if (maybe_type.isPresent()) {
            return maybe_type.get();
        }

        throw new TypeMismatchException(leftType, rightType, node.GetLeft(), node.GetRight());
    }

    private Type TimesDivideTypeCheckExpr(Type leftType, Type rightType, ExpressionNode node) throws TypeException {
        var maybe_type = TypePair.TryBothWays(leftType, rightType, MULTIPLY_DIVIDE_TYPE_MAP);

        if (maybe_type.isPresent()) {
            return maybe_type.get();
        }

        throw new TypeMismatchException(leftType, rightType, node.GetLeft(), node.GetRight());
    }
}
