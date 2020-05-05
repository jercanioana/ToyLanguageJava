package View;

import Model.Statement.IStmt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MyGUI extends Application {

    private ListView<IStmt> listview;
    private Label label;

    public void initialize(){
        listview.setItems(getPrg);
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Main window");
        StackPane layout = new StackPane();
        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show();

    }
}
