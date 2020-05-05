package sample;

import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.adt.*;
import View.RunExample;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainWindowController implements Initializable {

    private RunExample command;

    private PrgState currentState;

    private List<PrgState> states;

    @FXML
    private Label programIDLabel;

    @FXML
    private TableView<HeapTableData> heapTable;

    @FXML
    private TableColumn<HeapTableData, Integer> addressColumn;

    @FXML
    private TableColumn<HeapTableData, Value> valueForHeapColumn;

    @FXML
    private TableView<SymTblData> symTable;

    @FXML
    private TableColumn<SymTblData, String> variableNameColumn;

    @FXML
    private TableColumn<SymTblData, Value> valueForSymTableColumn;

    @FXML
    private ListView<String> outTable;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<String> programStatesID;

    @FXML
    private ListView<String> executionStackTable;

    @FXML
    private Button runOneStepButton;

    @FXML
    private Button restartButton;

    @FXML
    private TableView<ProcTableData> procTable;

    @FXML
    private TableColumn<ProcTableData, String> nameColumn;

    @FXML
    private TableColumn<ProcTableData, Pair<List<String>, String>> varsColumn;



    private void initializeTables(){

        this.symTable.getColumns().clear();
        this.heapTable.getColumns().clear();
        this.procTable.getColumns().clear();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        varsColumn.setCellValueFactory(new PropertyValueFactory<>("vars"));
        procTable.getColumns().addAll(nameColumn,varsColumn);

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        valueForHeapColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueForHeapColumn.setMinWidth(100);
        heapTable.getColumns().addAll(addressColumn, valueForHeapColumn);

        variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("var_name"));
        variableNameColumn.setMinWidth(100);

        valueForSymTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTable.getColumns().addAll(variableNameColumn, valueForSymTableColumn);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        command = (RunExample)sampleController.command;
        this.states = command.getCtrl().getMyRepo().getPrgList();
        currentState = this.states.get(0);
        this.command.getCtrl().allStepGUI();
        initializeTables();
        programStatesID.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public void initializeOutTable(PrgState program){
        this.outTable.getItems().clear();
        MyIList<Value> out = program.getOut();
        for(Value output : out.getList()){
            outTable.getItems().add(output.toString());
        }
    }

    public void initializeFileTable(PrgState program){
        this.fileTable.getItems().clear();
        MyIDictionary<StringValue, BufferedReader> ftbl = program.getFileTable();
        for(Map.Entry files : ftbl.getContent().entrySet()){
            fileTable.getItems().add("File name: " + files.getKey().toString() + "->" + files.getValue().toString());
        }
    }

    public void initializeExecutionStackTable(PrgState program){
        this.executionStackTable.getItems().clear();
        MyIStack<IStmt> execstack = program.getStk();
        Stack<?> clonedStack = execstack.clone();
        while(!clonedStack.isEmpty()){
            this.executionStackTable.getItems().add(clonedStack.pop().toString());
        }
    }

    public void initializeProgramStatesID(PrgState program){
        this.programStatesID.getItems().clear();
        List<PrgState> programs = command.getCtrl().getMyRepo().getPrgList();
        for(PrgState program1: programs){
            this.programStatesID.getItems().add(Integer.toString(program1.getID()));
        }
    }

    public void initializeHeapTable(PrgState program){
        this.heapTable.getItems().clear();
        ObservableList<HeapTableData> heapData = FXCollections.observableArrayList();

        MyIHeap<Integer, Value> heap = program.getHeap();
        for(Map.Entry heapElements: heap.getContent().entrySet()) {
            heapData.add(new HeapTableData((Integer) heapElements.getKey(), heapElements.getValue().toString()));
        }
        heapTable.setItems(heapData);

    }

    public void initializeProcTable(PrgState program){
        this.procTable.getItems().clear();
        ObservableList<ProcTableData> procTableData = FXCollections.observableArrayList();
        IProcTable<String, Pair<List<String>, IStmt>> procTable = program.getProcTable();
        for(Map.Entry procElements: procTable.getContent().entrySet()){
            Pair<List<String>, IStmt> pair = procTable.getVal(procElements.getKey().toString());
            String stmt = pair.getValue().toString();
            Pair<List<String>, String> newpair = new Pair<>(pair.getKey(), stmt);
            procTableData.add(new ProcTableData(procElements.getKey().toString(), newpair));
        }
        this.procTable.setItems(procTableData);
    }

    public void initializeSymTable(PrgState program){
        this.symTable.getItems().clear();
        ObservableList<SymTblData> symTableData = FXCollections.observableArrayList();
        MyIDictionary<String, Value> symTableElements = program.getSymTable();
        for(Map.Entry symTableElement : symTableElements.getContent().entrySet()) {
            symTableData.add(new SymTblData((String) symTableElement.getKey(), symTableElement.getValue().toString()));
        }
        symTable.setItems(symTableData);
    }

    public void runOneStep() throws Exception {
        programIDLabel.setText(Integer.toString(command.getCtrl().getMyRepo().getPrgList().size()));
        try{
            initializeProgramStatesID(currentState);
            initializeExecutionStackTable(currentState);
            initializeSymTable(currentState);
            if(!states.isEmpty()){

                initializeFileTable(states.get(0));
                initializeOutTable(states.get(0));
                initializeHeapTable(states.get(0));
                initializeProcTable(states.get(0));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            this.states = command.getCtrl().allStepGUI2();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void initializeForForkStmt() {
        String stateId = programStatesID.getSelectionModel().getSelectedItem();
        PrgState current = command.getCtrl().getMyRepo().getProgramByID(Integer.parseInt(stateId));
        this.currentState = current;
        initializeSymTable(this.currentState);
        initializeExecutionStackTable(this.currentState);
    }

    public void restart() throws Exception{
        Stage primaryStage = (Stage) restartButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("GUI");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }




}
