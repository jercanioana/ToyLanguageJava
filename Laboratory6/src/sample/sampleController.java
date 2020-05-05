package sample;

import View.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class sampleController implements Initializable{

    public static Command command;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button runProgramButton;

    private View view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        view = new View();
        try {
            view.runMenu();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Map<String, Command> commands = view.getCommands();
        ObservableList<String> commandsList = this.listView.getItems();

        for(Map.Entry cmds: commands.entrySet())
        {
            if(!(cmds.getKey().equals("0"))){

                if(cmds.getValue() instanceof RunExample){

                    Command command = (RunExample) cmds.getValue();
                    commandsList.add("Key: " + command.getKey() + " Description: " + command.getDescription());

                }
            }
        }

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void startProgram() throws IOException {
        String selectedProgram = listView.getSelectionModel().getSelectedItem();
        Integer id;
        try {
            id = Integer.parseInt(selectedProgram.substring(5,7));
        }
        catch (Exception e){
            id = Integer.parseInt(selectedProgram.substring(5,6));
        }
        String commandID = id.toString();
        view = new View();
        try {
            view.runMenu();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.command = view.getCommands().get(commandID);
        System.out.println(command);
        runProgramButton.getScene();
        Parent loader = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        runProgramButton.getScene().setRoot(loader);

    }
}
