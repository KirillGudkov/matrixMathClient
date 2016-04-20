package sample.start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.det.Det;
import sample.invert.Invert;
import sample.multOnNumber.MultOnNumber;
import sample.multiplicate.MultipleMatrix;
import sample.slau.Slau;
import sample.trans.Trans;

public class Controller {
    @FXML
    Button slau;
    @FXML
    Button multiplicate;
    @FXML
    Button transpose;
    @FXML
    Button binary;
    @FXML
    Button det;
    @FXML
    Button invert;

    private Stage stage;

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void slau(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../slau/Slau.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        Slau controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Решение СЛАУ");
        stage.setScene(scene);
        controller.initSlau();
    }

    public void multiplicate(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../multOnNumber/MultOnNumber.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        MultOnNumber controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Умножение матрицы на число");
        stage.setScene(scene);
        controller.initMultOnNumber();
    }

    public void binary(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../multiplicate/MultipleMatrix.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        MultipleMatrix controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Бинарные операции");
        stage.setScene(scene);
        controller.initMultipleMatrix();
    }

    public void transpose(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../trans/Trans.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        Trans controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Транспонирование матрицы");
        stage.setScene(scene);
        controller.initTrans();
    }

    public void invert(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../invert/invert.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        Invert controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Нахождение обратной матрицы");
        stage.setScene(scene);
        controller.initInvert();
    }

    public void det(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../det/det.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 500);
        Det controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Нахождение детерминанта");
        stage.setScene(scene);
        controller.initDet();
    }
}