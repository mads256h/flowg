package org.flowsoft.flowg.nodes;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class IncludeNode {
    FileReader fr = new FileReader("test123.txt");

    public IncludeNode() throws FileNotFoundException{

    }
}
