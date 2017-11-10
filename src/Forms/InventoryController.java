package Forms;

import Data.Inventory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Mosab on 3/17/2017.
 */
public class InventoryController implements Initializable {

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton admin;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton refresh;

    @FXML
    private TextField searchByID;

    @FXML
    private TextField searchByName;

    @FXML
    private TableView<Inventory> table;

    @FXML
    private TableColumn<Inventory, String> id;

    @FXML
    private TableColumn<Inventory, String> material;

    @FXML
    private TableColumn<Inventory, Double> unitPrice;

    @FXML
    private TableColumn<Inventory, String> dateCol;

    @FXML
    private TextField idText;

    @FXML
    private TextField materialName;

    @FXML
    private TextField unitPriceField;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton delete;

    String Smaterial;
    Double SunitPrice;
    String Sdate;

    DatabaseHandler databaseHandler;

    private ObservableList<Inventory> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInctance();
        inicCol();
        loadTableDataFromDB();
    }

    private void inicCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        material.setCellValueFactory(new PropertyValueFactory<>("material"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    void loadTableDataFromDB() {
        //--------Write your DB code here then but the data in the ObservableList that called list--------//

        DatabaseHandler databaseHandler = DatabaseHandler.getInctance();
        String qu = "SELECT * FROM Product";
        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {
            while (resultSet.next()) {
                String Xid = resultSet.getString("id");
                String Xname = resultSet.getString("name");
                String Xprice = resultSet.getString("price");
                String Xdate = resultSet.getString("date");

                list.add(new Inventory(Xid, Xname, Double.parseDouble(Xprice), Xdate));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadTableData();
    }

    void loadTableData() {
        table.getItems().setAll(list);
    }

    @FXML
    void addData(ActionEvent event) {
        String id = "" + gen();
        Smaterial = materialName.getText();
        SunitPrice = Double.parseDouble(unitPriceField.getText());
        Sdate = date.getValue().toString();

        checkInput();

        String qu = "INSERT INTO Product VALUES ("
                + "'" + id + "',"
                + "'" + Smaterial + "',"
                + "'" + SunitPrice + "',"
                + "'" + Sdate + "'"
                + ")";

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Add Success");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Feild");
            alert.showAndWait();
        }

        list.add(new Inventory(id, Smaterial, SunitPrice, Sdate));
        loadTableData();

        clear();

    }

    void checkInput() {
        if (Smaterial.isEmpty() || SunitPrice.toString().isEmpty() || Sdate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Try Enter All feilds");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    void deleteRaw(ActionEvent event) {
        String id = idText.getText();
        String query = "DELETE  FROM Product WHERE id= '" + id + " '";

        if (databaseHandler.execAction(query)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("نجح الحذف");
            alert.showAndWait();
            removeAllRows();
            loadTableDataFromDB();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("فشل الحذف");
            alert.showAndWait();
        }

    }

    @FXML
    void updateData(ActionEvent event) {

        String query = "UPDATE Product SET name = '" + materialName.getText() + "', price = '" + unitPriceField.getText() + "', date = '" + date.getValue().toString() + "'";

        if (databaseHandler.execAction(query)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("نجح التعديل");
            alert.showAndWait();
            removeAllRows();
            loadTableDataFromDB();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("فشل التعديل");
            alert.showAndWait();
        }

    }

    @FXML
    void getData(ActionEvent event) {
        String id = idText.getText();
        String query = "SELECT * FROM Product WHERE id = '" + id + "'";

        ResultSet resultSet = databaseHandler.execQuery(query);

        try {

            String name = "";
            String price = "";
            String Sdate = "";
            while (resultSet.next()) {
                name = resultSet.getString("name");
                price = resultSet.getString("price");
                Sdate = resultSet.getString("date");

            }

            materialName.setText(name);
            unitPriceField.setText(price);
            date.setValue(LocalDate.parse(Sdate));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clear() {
        materialName.setText("");
        unitPriceField.setText("");
    }

    @FXML
    void searchByName(ActionEvent event)
    {
        String id = searchByName.getText();
        if (id.isEmpty())
        {
            list.clear();
            removeAllRows();
            loadTableDataFromDB();
        }
        else
        {
            DatabaseHandler databaseHandler = DatabaseHandler.getInctance();
            String query = "SELECT * FROM Product WHERE name = '" + id + "'";
            ResultSet resultSet = databaseHandler.execQuery(query);

            try {
                list.clear();
                removeAllRows();
                while (resultSet.next()) {
                    String Xid = resultSet.getString("id");
                    String Xname = resultSet.getString("name");
                    String Xprice = resultSet.getString("price");
                    String Xdate = resultSet.getString("date");

                    list.add(new Inventory(Xid, Xname, Double.parseDouble(Xprice), Xdate));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            loadTableData();
        }
    }

    @FXML
    void searchByID(ActionEvent event) {
        String id = searchByID.getText();
        if (id.isEmpty())
        {
            list.clear();
            removeAllRows();
            loadTableDataFromDB();
        }
        else
        {
            DatabaseHandler databaseHandler = DatabaseHandler.getInctance();
            String query = "SELECT * FROM Product WHERE id = '" + id + "'";
            ResultSet resultSet = databaseHandler.execQuery(query);

            try {
                list.clear();
                removeAllRows();
                while (resultSet.next()) {
                    String Xid = resultSet.getString("id");
                    String Xname = resultSet.getString("name");
                    String Xprice = resultSet.getString("price");
                    String Xdate = resultSet.getString("date");

                    list.add(new Inventory(Xid, Xname, Double.parseDouble(Xprice), Xdate));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            loadTableData();
        }

    }

    public void removeAllRows() {
        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
        }
        list.clear();
    }

    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

}
