package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;

public class TypeCheckingVisitor implements IVisitor<Type, TypeException>{

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
    public Type Visit(DeclarationNode declarationNode) throws TypeException {
        var typeNode = declarationNode.GetTypeChild();
        var identifierNode = declarationNode.GetIdentifierChild();
        var expressionNode = declarationNode.GetExpressionChild();

        var typeNodeType = typeNode.Accept(this);
        var expressionNodeType = expressionNode.Accept(this);
        if (typeNodeType == expressionNodeType) {
            System.out.println(
                    declarationNode.GetIdentifierChild().GetValue()
                    + " has type: "
                    + typeNodeType);
            _symbolTable.Enter(identifierNode.GetValue(), typeNodeType);
            return typeNodeType;
        }
        else {
            // TODO: Report type error
            throw new TypeException();
        }
    }

    @Override
    public Type Visit(TypeNode typeNode) {
        return typeNode.GetValue();
    }

    @Override
    public Type Visit(IdentifierNode identifierNode) {
        var variableEntry = _symbolTable.Lookup(identifierNode.GetValue());
        return variableEntry.Type;
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

    private Type PlusMinusTypeCheckExpr(Type leftChild, Type rightChild) throws TypeException {
        if (leftChild != rightChild) {
            throw new TypeException();
        }

        if (leftChild == Type.Boolean) {
            throw new TypeException();
        }

        return leftChild;
    }

    private Type TimesDivideTypeCheckExpr(Type leftChild, Type rightChild) throws TypeException {
        if (leftChild != rightChild) {
            throw new TypeException();
        }

        if (leftChild == Type.Boolean) {
            throw new TypeException();
        }

        return leftChild;
    }
}