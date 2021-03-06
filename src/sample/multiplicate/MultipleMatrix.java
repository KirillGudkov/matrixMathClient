package sample.multiplicate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.json.simple.JSONObject;
import sample.Client;
import sample.Matrix;
import sample.dialog.Dialog;
import sample.response.Response;
import sample.start.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleMatrix {
    private Stage stage;
    private Pane forLoad = new Pane();
    private Matrix matrixOne;
    private Matrix matrixTwo;
    private Label labelSign;
    private String valueSign;
    private int widthOne;
    private int heightOne;
    private int widthTwo;
    private int heightTwo;
    private List<Integer> listOneBox = new ArrayList<>();
    private ObservableList<Integer> observableListOne = FXCollections.observableList(listOneBox);
    private List<Integer> listTwoBox = new ArrayList<>();
    private ObservableList<Integer> observableListTwo = FXCollections.observableList(listTwoBox);
    private List<String> listForSign = new ArrayList<>();
    private ObservableList<String> observableListSign = FXCollections.observableList(listForSign);
    @FXML
    ComboBox widthMatrixOne;
    @FXML
    ComboBox heightMatrixOne;
    @FXML
    ComboBox widthMatrixTwo;
    @FXML
    ComboBox heightMatrixTwo;
    @FXML
    Pane paneForOne;
    @FXML
    Pane paneForTwo;
    @FXML
    ComboBox sign;
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

    public void initMultipleMatrix() {
        observableListOne.addAll(2, 3, 4, 5, 6);
        widthMatrixOne.setItems(observableListOne);
        heightMatrixOne.setItems(observableListOne);
        heightMatrixOne.setDisable(true);
        widthMatrixOne.setDisable(true);

        observableListTwo.addAll(2, 3, 4, 5, 6);
        widthMatrixTwo.setItems(observableListTwo);
        heightMatrixTwo.setItems(observableListTwo);
        heightMatrixTwo.setDisable(true);
        widthMatrixTwo.setDisable(true);

        observableListSign.addAll("+", "-", "*");
        sign.setItems(observableListSign);

        matrixOne = new Matrix(0, 0, paneForOne);
        matrixTwo = new Matrix(0, 0, paneForTwo);
        next.setDisable(true);
    }

    public void initMatrixOne() {
        widthOne = Integer.parseInt(widthMatrixOne.getValue().toString());
        heightOne = Integer.parseInt(heightMatrixOne.getValue().toString());
        matrixOne = new Matrix(widthOne, heightOne, paneForOne);
        heightMatrixTwo.setDisable(false);
        widthMatrixTwo.setDisable(false);
        throw new NullPointerException("");
    }

    public void initMatrixTwo() {
        widthTwo = Integer.parseInt(widthMatrixTwo.getValue().toString());
        heightTwo = Integer.parseInt(heightMatrixTwo.getValue().toString());
        matrixTwo = new Matrix(widthTwo, heightTwo, paneForTwo);
        next.setDisable(false);
        throw new NullPointerException();
    }

    public void widthMatrixOneChange(ActionEvent actionEvent) {
        matrixOne.removeMatrix(paneForOne);
        try {initMatrixOne();}
        catch (NullPointerException e) {}
    }

    public void heightMatrixOneChange(ActionEvent actionEvent) {
        matrixOne.removeMatrix(paneForOne);
        try {initMatrixOne();}
        catch (NullPointerException e) {}
    }

    public void heightMatrixTwoChange(ActionEvent actionEvent) {
        matrixTwo.removeMatrix(paneForTwo);
        try {initMatrixTwo();}
        catch (NullPointerException e) {}
    }

    public void widthMatrixTwoChange(ActionEvent actionEvent) {
        matrixTwo.removeMatrix(paneForTwo);
        try {initMatrixTwo();}
        catch (NullPointerException e) {}
    }

    public void signChange(ActionEvent actionEvent) {
        heightMatrixOne.setDisable(false);
        widthMatrixOne.setDisable(false);
    }

    public String getSign () {
        String operation = new String();
        switch (this.sign.getValue().toString()) {
            case "+": {
                operation = "add";
                break;
            }
            case "-": {
                operation = "sub";
                break;
            }
            case "*": {
                operation = "multiply";
                break;
            }
        }

        return operation;
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../start/sample.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 400, 480);
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("matrixMath");
        stage.setX((Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-200);
        stage.setY((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-240);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void goForward(ActionEvent actionEvent) throws Exception{
        if (matrixOne.checkValue() == 0 && matrixTwo.checkValue() == 0) {
            Thread resp = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("left", matrixOne.getList());
                        jsonObject.put("right", matrixTwo.getList());
                        System.out.println(jsonObject.toString());
                        System.out.println(getSign());
                        Client client = new Client();
                        Response response = new Response();
                        response.showResponse(actionEvent, client.initConnection(jsonObject, getSign(), stage), stage,  Integer.parseInt(widthMatrixOne.getValue().toString()), Integer.parseInt(heightMatrixOne.getValue().toString()));
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
