package Model.adt;

import javafx.util.Pair;

import java.util.List;

public class ProcTableData {
    private String name;
    private Pair<List<String>, String> vars;

    public ProcTableData(String n, Pair<List<String>, String> v){
        name = n;
        vars = v;
    }

    public String getName(){return name;}
    public Pair<List<String>, String> getVars(){return vars;}
    public String toString(){return name + " " + vars;}
}
