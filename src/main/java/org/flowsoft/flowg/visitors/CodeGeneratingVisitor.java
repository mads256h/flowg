package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.BigDecimalUtils;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final SymbolTable _symbolTable;

    private final StringBuilder _stringBuilder = new StringBuilder();

    public CodeGeneratingVisitor(SymbolTable symbolTable) {
        _symbolTable = symbolTable;
    }

    public String GetCode() {
        return _stringBuilder.toString();
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

        _stringBuilder
                .append("G1")
                // Don't extrude anything we are moving :)
                .append(" E0")
                .append(" X").append(point.GetX())
                .append(" Y").append(point.GetY())
                .append(" Z").append(point.GetZ())
                .append('\n');

        return new ExpressionValue(Type.Void);
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
        var type = declarationNode.GetFirstNode().GetValue();
        var identifier = declarationNode.GetSecondNode().GetValue();
        var value = declarationNode.GetThirdNode().Accept(this);

        _symbolTable.Enter(identifier, type, value);
        return null;
    }

    @Override
    public ExpressionValue Visit(FunctionDefinitionNode functionDefinitionNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(TypeNode typeNode) throws Exception {
        return null;
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
    public ExpressionValue Visit(IdentifierExpressionNode identifierExpressionNode) throws Exception {
        var identifier = identifierExpressionNode.GetChild().GetValue();
        var variableEntry = _symbolTable.LookupVariable(identifier);
        return variableEntry.Value;
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
