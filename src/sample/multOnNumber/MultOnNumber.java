package sample.multOnNumber;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.Client;
import sample.Matrix;
import sample.RestrictiveTextField;
import sample.dialog.Dialog;
import sample.response.Response;
import sample.start.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultOnNumber {
    private Stage stage;
    private Pane forLoad = new Pane();
    private int width;
    private int height;
    private Matrix matrix;
    private List<Integer> listOneBox = new ArrayList<>();
    private ObservableList<Integer> observableListOne = FXCollections.observableList(listOneBox);
    private List<Integer> listTwoBox = new ArrayList<>();
    private ObservableList<Integer> observableListTwo = FXCollections.observableList(listTwoBox);

    @FXML
    Pane paneForMatrix;
    @FXML
    ComboBox widthMatrix;
    @FXML
    ComboBox heightMatrix;
    @FXML
    RestrictiveTextField factor;
    @FXML
    Button next;
    @FXML
    AnchorPane anchor;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showLoading () {
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:resources/image/anim.gif");
        ImageView imageView = new ImageView(image);
        imageView.setX(360);
        imageView.setY(180);
        forLoad.setStyle("-fx-background-color: #3399FF; -fx-min-width: 900px; -fx-min-height: 500px;");
        forLoad.getChildren().add(imageView);
        Platform.runLater(()-> {anchor.getChildren().add(forLoad);});
    }

    public void hideLoading () {
        Platform.runLater(()->{anchor.getChildren().remove(forLoad);});
    }

    public void initMultOnNumber () {
        observableListOne.addAll(2, 3, 4, 5, 6);
        observableListTwo.addAll(2, 3, 4, 5, 6);
        widthMatrix.setItems(observableListOne);
        heightMatrix.setItems(observableListTwo);
        matrix = new Matrix(0, 0, paneForMatrix);
        next.setDisable(true);
        factor.setMaxLength(4);
        factor.setRestrict("[0-9.]");
    }

    public void initMatrix () {
        width = Integer.parseInt(widthMatrix.getValue().toString());
        height = Integer.parseInt(heightMatrix.getValue().toString());
        matrix = new Matrix(width, height, paneForMatrix);
        next.setDisable(false);
        throw new NullPointerException();
    }

    public void heightMatrixChange(ActionEvent actionEvent) {
            matrix.removeMatrix(paneForMatrix);
        try {initMatrix();}
        catch (NullPointerException e) {}
    }

    public void widthMatrixChange(ActionEvent actionEvent) {
            matrix.removeMatrix(paneForMatrix);
        try {initMatrix();}
        catch (NullPointerException e) {}
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../start/sample.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 480, 480);
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("matrixMath");
        stage.setX((Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-240);
        stage.setY((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-240);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void goForward(ActionEvent actionEvent) throws Exception{
        if (matrix.checkValue() == 0 && !factor.getText().isEmpty()) {
            Thread resp = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        JSONArray vector = new JSONArray();
                        vector.add(Double.parseDouble(factor.getText()));
                        jsonObject.put("right", vector);
                        jsonObject.put("left", matrix.getList());
                        System.out.println(jsonObject.toString());
                        Client client = new Client();
                        Response response = new Response();
                        response.showResponse(actionEvent, client.initConnection(jsonObject, "multiply",stage), stage, Integer.parseInt(widthMatrix.getValue().toString()), Integer.parseInt(heightMatrix.getValue().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread load = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        showLoading();
                        resp.start();
                        resp.join();
                        hideLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            load.start();
        }
        else {
            Dialog dialog = new Dialog();
            Label label = new Label();
            label.setText("Вы не заполнили одно или несколько полей!");
            dialog.showDialog(stage, label);
        }
    }
}
