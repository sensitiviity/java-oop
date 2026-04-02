package uni.com;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        ClothesGui gui = new ClothesGui(new Store());
        gui.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}