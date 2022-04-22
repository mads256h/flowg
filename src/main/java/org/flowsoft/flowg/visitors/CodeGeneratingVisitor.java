package org.flowsoft.flowg.visitors;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.flowsoft.flowg.*;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.controlflow.ForToNode;
import org.flowsoft.flowg.nodes.controlflow.IfElseNode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;
import org.flowsoft.flowg.symboltables.RuntimeSymbolTable;
import org.flowsoft.flowg.symboltables.SymbolTable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class CodeGeneratingVisitor implements IVisitor<ExpressionValue, Exception> {

    private final static HashMap<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> PLUS_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetNumber().add(right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
            put(new TypePair(Type.Point, Type.Point), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetPoint().Add(right.GetPoint()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
        }
    };

    private final static HashMap<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> MINUS_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetNumber().subtract(right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
            put(new TypePair(Type.Point, Type.Point), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetPoint().Subtract(right.GetPoint()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
        }
    };

    private final static HashMap<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> MULTIPLY_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetNumber().multiply(right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });

            put(new TypePair(Type.Point, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetPoint().MultiplyBy(right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
        }
    };

    private final static HashMap<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> DIVIDE_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(BigDecimalUtils.Divide(left.GetNumber(), right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });

            put(new TypePair(Type.Point, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(left.GetPoint().DivideBy(right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
        }
    };

    private final static HashMap<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> EQ_MAP = new HashMap<>() {
        {
            put(new TypePair(Type.Number, Type.Number), (left, right) -> {
                try {
                    return new ExpressionValue(BigDecimalUtils.Equals(left.GetNumber(), right.GetNumber()));
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
            put(new TypePair(Type.Point, Type.Point), (left, right) -> {
                try {
                    if (left.GetPoint().equals(right.GetPoint())) {
                        return new ExpressionValue(true);
                    } else {
                        return new ExpressionValue(false);
                    }
                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
            put(new TypePair(Type.Boolean, Type.Boolean), (left, right) -> {
                try {
                    var boolLeft = left.GetBoolean();
                    var boolRight = right.GetBoolean();

                    if (boolLeft == boolRight) {
                        return new ExpressionValue(true);
                    } else {
                        return new ExpressionValue(false);
                    }

                } catch (TypeException e) {
                    e.printStackTrace();
                    assert(false);
                    return null;
                }
            });
        }
    };

    private RuntimeSymbolTable _symbolTable;

    private final StringBuilder _stringBuilder = new StringBuilder();

    private Point _currentPosition = new Point(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    private BigDecimal _currentExtrusion = new BigDecimal("0");

    public CodeGeneratingVisitor(SymbolTable symbolTable) {
        _symbolTable = new RuntimeSymbolTable(symbolTable, null);
    }

    public String GetCode() {
        return _stringBuilder.toString();
    }

    public RuntimeSymbolTable GetSymbolTable() {
        return _symbolTable;
    }

    @Override
    public ExpressionValue Visit(StatementListNode statementListNode) throws Exception {
        for (var child : statementListNode.GetChildren()) {
            child.Accept(this);
        }
        return null;
    }

    @Override
    public ExpressionValue Visit(MoveNode moveNode) throws Exception {
        var parameters = moveNode.GetChild().GetChildren();
        var point = parameters.get(0).Accept(this).GetPoint();
        _currentPosition = point;

        _stringBuilder
                .append("G0")
                // Don't extrude anything we are moving :)
                .append(" X").append(BigDecimalUtils.ToGCode(point.GetX()))
                .append(" Y").append(BigDecimalUtils.ToGCode(point.GetY()))
                .append(" Z").append(BigDecimalUtils.ToGCode(point.GetZ()))
                .append('\n');

        return new ExpressionValue(Type.Void);
    }

    @Override
    public ExpressionValue Visit(LineNode lineNode) throws Exception {
        var parameters = lineNode.GetChild().GetChildren();
        var point = parameters.get(0).Accept(this).GetPoint();

        // All distances in mm
        var distance = point.Distance(_currentPosition);
        var filamentDiameter = new BigDecimal("2.85");
        var nozzleDiameter = new BigDecimal("0.4");
        var layerHeight = new BigDecimal("0.1");

        var pi = new BigDecimal(Math.PI);
        var filamentRadius = BigDecimalUtils.Divide(filamentDiameter, new BigDecimal(2));


        var filamentVolumeNeeded = distance.multiply(nozzleDiameter).multiply(layerHeight);

        // PI * r²
        var filamentArea = pi.multiply(filamentRadius.pow(2));

        var filamentMm = BigDecimalUtils.Divide(filamentVolumeNeeded, filamentArea);
        _currentExtrusion = _currentExtrusion.add(filamentMm);

        _currentPosition = point;

        _stringBuilder
                .append("G1")
                .append(" E").append(BigDecimalUtils.ToGCode(_currentExtrusion))
                .append(" X").append(BigDecimalUtils.ToGCode(point.GetX()))
                .append(" Y").append(BigDecimalUtils.ToGCode(point.GetY()))
                .append(" Z").append(BigDecimalUtils.ToGCode(point.GetZ()))
                .append('\n');

        return new ExpressionValue(Type.Void);
    }

    @Override
    public ExpressionValue Visit(SqrtNode sqrtNode) throws Exception {
        var parameters = sqrtNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(number.sqrt(BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(SinNode sinNode) throws Exception {
        var parameters = sinNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.sin(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(CosNode cosNode) throws Exception {
        var parameters = cosNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.cos(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(TanNode tanNode) throws Exception {
        var parameters = tanNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.tan(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(ArcsinNode arcsinNode) throws Exception {
        var parameters = arcsinNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.asin(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(ArccosNode arccosNode) throws Exception {
        var parameters = arccosNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.acos(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(ArctanNode arctanNode) throws Exception {
        var parameters = arctanNode.GetChild().GetChildren();
        var number = parameters.get(0).Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.atan(number, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(ActualParameterListNode actualParameterListNode) throws Exception {
        for (var child : actualParameterListNode.GetChildren()) {
            child.Accept(this);
        }
        return null;
    }

    @Override
    public ExpressionValue Visit(FormalParameterListNode formalParameterListNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(FormalParameterNode formalParameterNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(DeclarationNode declarationNode) throws Exception {
        var identifier = declarationNode.GetSecondNode().GetValue();
        var value = declarationNode.GetThirdNode().Accept(this);

        _symbolTable.SetValue(identifier, value);
        return null;
    }

    @Override
    public ExpressionValue Visit(FunctionDefinitionNode functionDefinitionNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(GCodeFuncNode gCodeFuncNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(GCodeCodeNode gCodeCodeNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(TypeNode typeNode) throws Exception {
        throw new RuntimeException("This should never be visited");
    }

    @Override
    public ExpressionValue Visit(IdentifierNode identifierNode) throws Exception {
        throw new RuntimeException("This should never be visited");
    }

    @Override
    public ExpressionValue Visit(NumberLiteralNode numberLiteralNode) throws Exception {
        return new ExpressionValue(numberLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(BooleanLiteralNode booleanLiteralNode) throws Exception {
        return new ExpressionValue(booleanLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(PointNode pointNode) throws Exception {
        var first = pointNode.GetFirstNode().Accept(this);
        var second = pointNode.GetSecondNode().Accept(this);
        var third = pointNode.GetThirdNode().Accept(this);
        return new ExpressionValue(new Point(first.GetNumber(), second.GetNumber(), third.GetNumber()));
    }

    @Override
    public ExpressionValue Visit(PointEntryNode pointEntryNode) throws Exception {
        var point = pointEntryNode.GetLeftChild().Accept(this).GetPoint();
        var entry = pointEntryNode.GetRightChild().GetValue();

        return new ExpressionValue(switch (entry) {
            case "x" -> point.GetX();
            case "y" -> point.GetY();
            case "z" -> point.GetZ();
            default -> throw new IllegalArgumentException();
        });
    }

    @Override
    public ExpressionValue Visit(PlusExpressionNode plusExpressionNode) throws Exception {
        var leftValue = plusExpressionNode.GetLeftChild().Accept(this);
        var rightValue = plusExpressionNode.GetRightChild().Accept(this);

        return TryBoth(leftValue.GetType(), rightValue.GetType(), PLUS_MAP).apply(leftValue, rightValue);
    }

    @Override
    public ExpressionValue Visit(MinusExpressionNode minusExpressionNode) throws Exception {
        var leftValue = minusExpressionNode.GetLeftChild().Accept(this);
        var rightValue = minusExpressionNode.GetRightChild().Accept(this);

        return TryBoth(leftValue.GetType(), rightValue.GetType(), MINUS_MAP).apply(leftValue, rightValue);
    }

    @Override
    public ExpressionValue Visit(TimesExpressionNode multiplyExpressionNode) throws Exception {
        var leftValue = multiplyExpressionNode.GetLeftChild().Accept(this);
        var rightValue = multiplyExpressionNode.GetRightChild().Accept(this);

        return TryBoth(leftValue.GetType(), rightValue.GetType(), MULTIPLY_MAP).apply(leftValue, rightValue);
    }

    @Override
    public ExpressionValue Visit(DivideExpressionNode divisionExpressionNode) throws Exception {
        var leftValue = divisionExpressionNode.GetLeftChild().Accept(this);
        var rightValue = divisionExpressionNode.GetRightChild().Accept(this);

        return TryBoth(leftValue.GetType(), rightValue.GetType(), DIVIDE_MAP).apply(leftValue, rightValue);
    }

    @Override
    public ExpressionValue Visit(PowerExpressionNode powerExpressionNode) throws Exception {
        var leftValue = powerExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightValue = powerExpressionNode.GetRightChild().Accept(this).GetNumber();

        return new ExpressionValue(BigDecimalMath.pow(leftValue, rightValue, BigDecimalUtils.DEFAULT_MATH_CONTEXT));
    }

    @Override
    public ExpressionValue Visit(NotExpressionNode notExpressionNode) throws Exception {
        var childBoolean = notExpressionNode.GetChild().Accept(this).GetBoolean();

        return new ExpressionValue(!childBoolean);
    }

    @Override
    public ExpressionValue Visit(IdentifierExpressionNode identifierExpressionNode) throws Exception {
        var identifier = identifierExpressionNode.GetChild().GetValue();
        var variableEntry = _symbolTable.LookupVariable(identifier);
        return variableEntry;
    }

    @Override
    public ExpressionValue Visit(FunctionCallNode functionCallNode) throws Exception {
        var identifier = functionCallNode.GetLeftChild().GetValue();
        var params = functionCallNode.GetRightChild().GetChildren();

        var functionEntry = _symbolTable.LookupFunction(identifier);
        var formalParams = functionEntry.GetFormalParameters();

        var bodyTable = _symbolTable.Create(functionEntry.GetSymbolTable());

        // Insert values of the formal parameters into the symbol table
        for (int i = 0; i < params.size(); i++) {
            var paramId = formalParams.get(i).GetRightChild().GetValue();
            var value = params.get(i).Accept(this);

            bodyTable.SetValue(paramId, value);
        }

        // Get and run the function body
        var oldSymbolTable = _symbolTable;
        _symbolTable = bodyTable;

        ExpressionValue value = null;

        try {
            var functionBody = functionEntry.GetFunctionBody();
            functionBody.Accept(this);
        }
        catch (ReturnException e) {
            value = e.GetExpressionValue();
        }

        _symbolTable = oldSymbolTable;

        return value;
    }

    @Override
    public ExpressionValue Visit(ReturnNode returnNode) throws Exception {
        var value = new ExpressionValue(Type.Void);
        var child = returnNode.GetChild();
        if (child != null) {
            value = child.Accept(this);
        }

        throw new ReturnException(value);
    }

    @Override
    public ExpressionValue Visit(IfElseNode ifElseNode) throws Exception {
        var booleanValue = ifElseNode.GetFirstNode().Accept(this).GetBoolean();

        if (booleanValue) {
            ifElseNode.GetSecondNode().Accept(this);
        }
        else {
            if (ifElseNode.GetThirdNode() != null) {
                ifElseNode.GetThirdNode().Accept(this);
            }
        }

        return null;
    }

    @Override
    public ExpressionValue Visit(AssignmentNode assignmentNode) throws Exception {
        var identifier = assignmentNode.GetLeftChild().GetValue();
        var value = assignmentNode.GetRightChild().Accept(this);
        _symbolTable.SetValue(identifier, value);

        return null;
    }
    
    @Override
    public ExpressionValue Visit(ForToNode forToNode) throws Exception {
        var declNode = forToNode.GetFirstNode();
        var declIdentifier = declNode.GetSecondNode().GetValue();
        var declValue = declNode.GetThirdNode().Accept(this).GetNumber();

        declNode.Accept(this);

        var expressionValue = forToNode.GetSecondNode().Accept(this).GetNumber();
        if (declValue.compareTo(expressionValue) > 0) {
            return null;
        }

        for (BigDecimal i = declValue; i.compareTo(expressionValue) <= 0; i = i.add(new BigDecimal("1"))) {
            _symbolTable.SetValue(declIdentifier, new ExpressionValue(i));
            var statementNodeList = forToNode.GetThirdNode();
            statementNodeList.Accept(this);
        }

        return null;
    }

    @Override
    public ExpressionValue Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws Exception {
        var leftNumber = greaterThanExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightNumber = greaterThanExpressionNode.GetRightChild().Accept(this).GetNumber();
        return new ExpressionValue(BigDecimalUtils.GreaterThan(leftNumber, rightNumber));
    }

    @Override
    public ExpressionValue Visit(LessThanExpressionNode lessThanExpressionNode) throws Exception {
        var leftNumber = lessThanExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightNumber = lessThanExpressionNode.GetRightChild().Accept(this).GetNumber();
        return new ExpressionValue(BigDecimalUtils.LessThan( leftNumber, rightNumber));
    }

    @Override
    public ExpressionValue Visit(EqualsExpressionNode equalsExpressionNode) throws Exception {
        var leftType = equalsExpressionNode.GetLeftChild().Accept(this).GetType();
        var rightType = equalsExpressionNode.GetRightChild().Accept(this).GetType();

        var leftValue = equalsExpressionNode.GetLeftChild().Accept(this);
        var rightValue = equalsExpressionNode.GetRightChild().Accept(this);

        return TryBoth(leftType, rightType, EQ_MAP).apply(leftValue, rightValue);
    }

    @Override
    public ExpressionValue Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws Exception {
        var leftNumber = greaterThanEqualsExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightNumber = greaterThanEqualsExpressionNode.GetRightChild().Accept(this).GetNumber();
        return new ExpressionValue(BigDecimalUtils.GreaterThanEquals(leftNumber, rightNumber));
    }

    @Override
    public ExpressionValue Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws Exception {
        var leftNumber = lessThanEqualsExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightNumber = lessThanEqualsExpressionNode.GetRightChild().Accept(this).GetNumber();
        return new ExpressionValue(BigDecimalUtils.LessThanEquals(leftNumber, rightNumber));
    }

    @Override
    public ExpressionValue Visit(AndExpressionNode andExpressionNode) throws Exception {
        var leftBool = andExpressionNode.GetLeftChild().Accept(this).GetBoolean();
        var rightBool = andExpressionNode.GetRightChild().Accept(this).GetBoolean();

        return new ExpressionValue(leftBool && rightBool);
    }

    @Override
    public ExpressionValue Visit(OrExpressionNode orExpressionNode) throws Exception {
        var leftBool = orExpressionNode.GetLeftChild().Accept(this).GetBoolean();
        var rightBool = orExpressionNode.GetRightChild().Accept(this).GetBoolean();

        return new ExpressionValue(leftBool || rightBool);
    }

    private static BiFunction<ExpressionValue, ExpressionValue, ExpressionValue> TryBoth(Type left, Type right, Map<TypePair, BiFunction<ExpressionValue, ExpressionValue, ExpressionValue>> map) throws TypeException {
        var pair = new TypePair(left, right);
        if (map.containsKey(pair)) {
            return map.get(pair);
        }

        pair = new TypePair(right, left);
        if (map.containsKey(pair)) {
            var func = map.get(pair);
            return (e1, e2) -> func.apply(e2, e1);
        }

        throw new TypeException();
    }
}
