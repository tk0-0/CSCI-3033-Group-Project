import javafx.scene.paint.*;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.event.*;
import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class MainScene {
    @Override
    public void start(Stage PrimaryStage) {
        Henry henry = new Henry();
        Scene sceneOne = henry.getSceneOne();
        Scene sceneTwo = henry.getSceneTwo();

        PrimaryStage.setScene(sceneOne);
        PrimaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
