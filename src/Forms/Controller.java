package Forms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton checkDB;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;


    private String user;
    private String pass;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }


    @FXML
    void changeScene(ActionEvent event) throws IOException {


        Stage stage;
        Parent root;

            if (event.getSource() == login) {
                stage = (Stage) login.getScene().getWindow();

                root = FXMLLoader.load(getClass().getResource("/Forms/Login.fxml"));

                System.out.println("Login");
            } else if (event.getSource() == settings){
                stage = (Stage) settings.getScene().getWindow();

                root = FXMLLoader.load(getClass().getResource("/Forms/Settings.fxml"));

                System.out.println("Settings");
            }else
            {
                stage = (Stage) checkDB.getScene().getWindow();

                root = FXMLLoader.load(getClass().getResource("/Forms/CheckDB.fxml"));

                System.out.println("CheckDB");
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


    }

    @FXML
    void cancel(ActionEvent event)
    {
        Stage stage;
        Parent root;

        stage = (Stage) cancel.getScene().getWindow();

        try
        {
            root = FXMLLoader.load(getClass().getResource("/Forms/MainForm.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void checkLogin(ActionEvent event)
    {
        user = username.getText();
        pass = password.getText();

        checkData(user, pass);

        loadScreen("/Forms/Inventory.fxml", "Inventory");

    }

    private void checkData(String user, String pass)
    {
        //---------Database code here---------//
    }

    void loadScreen(String loc, String title)
    {

        try {
            Parent root= FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
