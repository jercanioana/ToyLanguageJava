package View;
import Controller.Controller;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.adt.*;
import Model.Value.*;
import Model.exp.*;
import Repository.IRepository;
import Repository.Repository;


import java.io.BufferedReader;



class Interpreter {
    public static void main(String[] args){
        IStmt ex1=  new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        MyIStack<IStmt> exestack1 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl1 = new MyDictionary<String, Value>();
        MyIList<Value> output1 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable1 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap1 = new MyHeap<Integer, Value>();
        PrgState prg1 = new PrgState(exestack1,symtbl1,output1, FileTable1, heap1, ex1);
        IRepository repo1 = new Repository(prg1,"log1.txt");
        Controller ctr1 = new Controller(repo1);
        //ctr1.addPrg(prg1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExpr("a"), new ValueExp(new IntValue(1)), '+')), new PrintStmt(new VarExpr("b"))))));
        MyIStack<IStmt> exestack2 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl2 = new MyDictionary<String, Value>();
        MyIList<Value> output2 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable2 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap2 = new MyHeap<Integer, Value>();
        PrgState prg2 = new PrgState(exestack2,symtbl2,output2,FileTable2,heap2, ex2);
        IRepository repo2 = new Repository(prg2,"log2.txt");
        Controller ctr2 = new Controller(repo2);
        //ctr2.addPrg(prg2);


        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExpr("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
        MyIStack<IStmt> exestack3 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl3 = new MyDictionary<String, Value>();
        MyIList<Value> output3 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable3 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap3 = new MyHeap<Integer, Value>();
        PrgState prg3 = new PrgState(exestack3,symtbl3,output3, FileTable3, heap3,ex3);
        IRepository repo3 = new Repository(prg3,"log3.txt");
        Controller ctr3 = new Controller(repo3);
        //ctr3.addPrg(prg3);

        IStmt ex4= new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/test.txt"))),
                        new CompStmt(new openRFile(new VarExpr("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new readFile(new VarExpr("varf"),"varc"),
                                                new CompStmt(new PrintStmt(new VarExpr("varc")),
                                                        new CompStmt(new readFile(new VarExpr("varf"),"varc"),
                                                                new CompStmt(new PrintStmt(new VarExpr("varc")), new closeRFile(new VarExpr("varf"))))))))));
        MyIStack<IStmt> exestack4 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl4 = new MyDictionary<String, Value>();
        MyIList<Value> output4 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable4 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap4 = new MyHeap<Integer, Value>();
        PrgState prg4 = new PrgState(exestack4,symtbl4,output4, FileTable4,heap4, ex4);
        IRepository repo4 = new Repository(prg4,"log4.txt");
        Controller ctr4 = new Controller(repo4);
        //ctr4.addPrg(prg4);



        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new newStmt("v", new ValueExp(new IntValue(20)))
                , new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new newStmt("a", new VarExpr("v")), new CompStmt(new newStmt("v", new ValueExp(new IntValue(30)))
                        , new PrintStmt(new rH(new rH(new VarExpr("a")))))))));
        MyIStack<IStmt> exestack5 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl5 = new MyDictionary<String, Value>();
        MyIList<Value> output5 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable5 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap5 = new MyHeap<Integer, Value>();
        PrgState prg5 = new PrgState(exestack5,symtbl5,output5, FileTable5,heap5, ex5);
        IRepository repo5 = new Repository(prg5,"log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", (new RefType(new IntType()))),
                new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new newStmt("a", new VarExpr("v")),
                                        new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new VarExpr("a")))))));
        MyIStack<IStmt> exestack6 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl6 = new MyDictionary<String, Value>();
        MyIList<Value> output6 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable6 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap6 = new MyHeap<Integer, Value>();
        PrgState prg6 = new PrgState(exestack6,symtbl6,output6, FileTable6,heap6, ex6);
        IRepository repo6 = new Repository(prg6,"log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new newStmt("a", new VarExpr("v")),
                                                new CompStmt(new PrintStmt(new rH(new VarExpr("v"))),
                                                        new PrintStmt(new ArithExp(new rH(new rH(new VarExpr("a"))), new ValueExp(new IntValue(5)), '+')))))));
        MyIStack<IStmt> exestack7 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl7 = new MyDictionary<String, Value>();
        MyIList<Value> output7 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable7 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap7 = new MyHeap<Integer, Value>();
        PrgState prg7 = new PrgState(exestack7,symtbl7,output7, FileTable7,heap7, ex7);
        IRepository repo7 = new Repository(prg7,"log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExpr("v"))),
                        new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                new PrintStmt(new ArithExp(new rH(new VarExpr("v")), new ValueExp(new IntValue(5)), '+' ))))));
        MyIStack<IStmt> exestack8 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl8 = new MyDictionary<String, Value>();
        MyIList<Value> output8 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable8 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap8 = new MyHeap<Integer, Value>();
        PrgState prg8 = new PrgState(exestack8,symtbl8,output8, FileTable8,heap8, ex8);
        IRepository repo8 = new Repository(prg8,"log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new whileStmt(new RelationalExpr(new VarExpr("v"), new ValueExp(new IntValue(0)), ">")
                                , new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithExp(new VarExpr("v"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExpr("v")))));
        MyIStack<IStmt> exestack9 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl9 = new MyDictionary<String, Value>();
        MyIList<Value> output9 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable9 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap9 = new MyHeap<Integer, Value>();
        PrgState prg9 = new PrgState(exestack9,symtbl9,output9, FileTable9,heap9, ex9);
        IRepository repo9 = new Repository(prg9,"log9.txt");
        Controller ctr9 = new Controller(repo9);


        IStmt ex10 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new newStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),new CompStmt(new AssignStmt("v",
                                new ValueExp(new IntValue(32))),new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new rH(new VarExpr("a"))))))),
                                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new rH(new VarExpr("a")))))))));
        MyIStack<IStmt> exestack10 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl10 = new MyDictionary<String, Value>();
        MyIList<Value> output10 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable10 = new MyDictionary<StringValue, BufferedReader>();
        MyIHeap<Integer, Value> heap10 = new MyHeap<Integer, Value>();
        MyIDictionary<String, Type> typeEnv = new MyDictionary<String, Type>();
        try {
            ex10.typecheck(typeEnv);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        PrgState prg10 = new PrgState(exestack10,symtbl10,output10, FileTable10,heap10, ex10);
        IRepository repo10 = new Repository(prg10,"log10.txt");
        Controller ctr10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctr5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctr6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctr7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctr8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctr9));
        menu.addCommand(new RunExample("10",ex10.toString(),ctr10));
        menu.show();
    }
}
