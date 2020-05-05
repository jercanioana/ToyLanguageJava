package Repository;

import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class Repository implements IRepository{

    private List<PrgState> prgStateMyList;
    private String logFilePath;
    public Repository(PrgState currentPrg, String l){
        prgStateMyList = new ArrayList<PrgState>();
        prgStateMyList.add(currentPrg);
        this.logFilePath = l;
    }


    @Override
    public void addPrgState(PrgState ps) {
        prgStateMyList.add(ps);
    }

    @Override
    public void logPrgStateExec(PrgState myPrgState) throws IOException{
        PrintWriter logfile;
        logfile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        MyIStack<IStmt> exestack = myPrgState.getStk();
        MyIDictionary<String, Value> symtbl = myPrgState.getSymTable();
        MyIList<Value> output = myPrgState.getOut();
        MyIDictionary<StringValue, BufferedReader> FileTable1 = myPrgState.getFileTable();
        MyIHeap<Integer, Value> heap = myPrgState.getHeap();
        int PrgStateID = myPrgState.getID();
        logfile.println("ID: " + PrgStateID);
        logfile.println("ExeStack:");
        ArrayList<IStmt> list = new ArrayList<IStmt>(exestack.getStack());
        ListIterator<IStmt> iterator = list.listIterator(list.size());

        while(iterator.hasPrevious()){
            logfile.println(iterator.previous().toString());
        }
        logfile.println("SymTable:");
        for(HashMap.Entry<String, Value> e: symtbl.getDictionary().entrySet()){
            logfile.println(e.getKey().toString() + " " + "-->" + " "+ e.getValue().toString());
        }
        logfile.println("Out:");
        for(Value e:output.getList()){
            logfile.println(e.toString());
        }
        logfile.println("FileTable");
        for(HashMap.Entry<StringValue, BufferedReader> e: FileTable1.getDictionary().entrySet()){
            logfile.println(e.getKey().toString());
        }
        logfile.println("Heap");
        for(HashMap.Entry<Integer, Value> e: heap.getDictionary().entrySet()){
            logfile.println(e.getKey().toString());
        }
        logfile.println(". . . . . . . . . . . . . . . . . . . . . . . .");
        logfile.close();

    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStateMyList;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        prgStateMyList = list;
    }
}
