package org.flowsoft.flowg.nodes;

public class StringNode {

    private String item;
    private StringNode nextNode;

    public StringNode (String value, StringNode next){
        this.item = value;
        this.nextNode = next;
    }

    public String getValue(){
        return item;
    }

    public StringNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(StringNode newNext){
        nextNode = newNext;
    }
}

