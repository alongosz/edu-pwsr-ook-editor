package net.longosz.OokEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        Scene scene = new Scene(new StackPane(codeArea), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ook! Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
