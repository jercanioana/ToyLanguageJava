package Model;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.*;
import exception.MyException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;


public class PrgState {
    private static int id = 0;
    private int defaultID;
    private IProcTable<String, Pair<List<String>, IStmt>> procTable;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIStack<MyIDictionary<String, Value>> symTables;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> FileTable;
    private MyIHeap<Integer, Value> Heap;

    private IStmt originalProgram; //optional field, but good to have
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> ftbl,MyIHeap<Integer, Value> h,IProcTable<String, Pair<List<String>, IStmt>> pt, IStmt prg){
        defaultID = generateID();
        exeStack=stk;
        symTables = new MyStack<>();
        symTables.push(symtbl);
        procTable = pt;
        out = ot;
        originalProgram=prg.deepCopy();//recreate the entire original prg
        FileTable = ftbl;
        Heap = h;
        stk.push(prg); }

    public PrgState(MyIStack<IStmt> stk, MyIStack<MyIDictionary<String, Value>> symtbl, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> ftbl,MyIHeap<Integer, Value> h,IProcTable<String, Pair<List<String>, IStmt>> pt, IStmt prg){
        defaultID = generateID();
        exeStack=stk;
        symTables = symtbl;
        //symTables.push(symtbl);
        out = ot;
        procTable = pt;
        originalProgram=prg.deepCopy();//recreate the entire original prg
        FileTable = ftbl;
        Heap = h;
        stk.push(prg); }

    public synchronized int generateID(){
        ++id;
        return id;
    }

    public IProcTable<String, Pair<List<String>, IStmt>> getProcTable(){return this.procTable;}
    public int getID(){
        return defaultID;
    }

    public MyIStack<IStmt> getStk() {
        return this.exeStack;
    }
    public void setExeStack(MyIStack<IStmt> stk){
        this.exeStack = stk;
    }


    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.FileTable;
    }
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> filetbl){
        this.FileTable = filetbl;
    }


    public MyIStack<MyIDictionary<String,Value>> getSymTables(){return symTables;}
    void setSymTables(MyIStack<MyIDictionary<String,Value>> t ) {symTables = t;}

    public MyIHeap<Integer, Value> getHeap(){return this.Heap;}
    public void setHeap(MyIHeap<Integer, Value> h) { this.Heap = h;}


    public MyIDictionary<String, Value> getSymTable() {
        return this.symTables.peek();
    }
    public void setSymTable(MyIDictionary<String, Value> stbl){
        this.symTable = stbl;
    }

    public MyIList<Value> getOut(){
        return this.out;
    }
    public void setOut(MyIList<Value> out1){
        out = out1;
    }

    public IStmt getOriginalProgram(){
        return this.originalProgram;
    }
    public void setOriginalProgram(IStmt stmt){
        this.originalProgram = stmt;
    }

    public String toString(){
        return "id: " + id + "Execution stack: " + exeStack.toString() + " " + "Symbol Table: " + symTable.toString() + " " + "Output: " + out.toString();
     }

    public Boolean isNotCompleted(){
        if(!exeStack.isEmpty())
            return true;
        return false;
    }
    public PrgState oneStep() throws MyException{
        if(exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }



}
