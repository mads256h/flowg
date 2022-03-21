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
        switch (plusExpressionNode.GetLeftChild().Accept(this)) {
            case Number -> {
                switch (plusExpressionNode.GetRightChild().Accept(this)) {
                    case Number -> {return Type.Number; }
                    //case Boolean -> {return Type.Boolean; }
                }
            }
//            case Point -> {
//                switch (plusExpressionNode.GetRightChild().Accept(this)) {
//                    case Point -> {return Type.Point; }
//                }
//            }
//            case Boolean -> {
//                switch (plusExpressionNode.GetRightChild().Accept(this)) {
//                    case Number, Boolean -> {return Type.Boolean; }
//                }
//            }
        }
        throw new TypeException();
    }

    @Override
    public Type Visit(MinusExpressionNode minusExpressionNode) throws TypeException {
        switch (minusExpressionNode.GetLeftChild().Accept(this)) {
            case Number -> {
                switch (minusExpressionNode.GetRightChild().Accept(this)) {
                    case Number -> { return Type.Number; }
                }
            }
//            case Point -> {
//                switch (minusExpressionNode.GetRightChild().Accept(this)) {
//                    case Point -> { return Type.Point; }
//                }
//            }
        }
        throw new TypeException();
    }

    @Override
    public Type Visit(TimesExpressionNode multiplyExpressionNode) throws TypeException {
        switch (multiplyExpressionNode.GetLeftChild().Accept(this)) {
            case Number -> {
                switch (multiplyExpressionNode.GetRightChild().Accept(this)) {
                    case Number -> { return Type.Number; }
                    //case Point -> { return  Type.Point; }
                }
            }
//            case Point -> {
//                switch (multiplyExpressionNode.GetRightChild().Accept(this)) {
//                    case Number, Point -> { return Type.Point; }
//                }
//            }
        }
        throw new TypeException();
    }

    @Override
    public Type Visit(DivideExpressionNode divisionExpressionNode) throws TypeException {
        switch (divisionExpressionNode.GetLeftChild().Accept(this)) {
            case Number -> {
                switch (divisionExpressionNode.GetRightChild().Accept(this)) {
                    case Number -> { return Type.Number; }
                }
            }
        }
        throw new TypeException();
    }
}