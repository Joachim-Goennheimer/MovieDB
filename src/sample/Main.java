package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Movie DB");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

        InteractiveModeLoader.saveDataInteractiveMode();
        super.stop();
    }

    public static void main(String[] args) {

        if (args.length > 0){

//            loading data depending on user request
            StaticModeLoader.loadDataStaticMode(args);

            exit();
        }
        else {
            InteractiveModeLoader.loadDataInteractiveMode();
            launch(args);
        }



    }





}
