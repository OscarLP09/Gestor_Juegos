package org.example.gestorjuegosjdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.gestorjuegosjdbc.dao.GameDAO;
import org.example.gestorjuegosjdbc.dao.JdbcUtils;
import org.example.gestorjuegosjdbc.models.Game;
import org.example.gestorjuegosjdbc.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private Button add;
    @FXML
    private TableView<Game> table;
    @FXML
    private TableColumn<Game,String> cTitle;
    @FXML
    private TableColumn<Game,String> cPlatform;
    @FXML
    private TableColumn<Game,String> cDescription;
    @FXML
    private TableColumn<Game,String> cYear;
    @FXML
    private TableColumn<Game,String> cId;
    @FXML
    private Label info;
    @FXML
    private MenuItem menuLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cId.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getId().toString() );
        });

        cTitle.setCellValueFactory( new PropertyValueFactory("title") );

        /*cTitle.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getTitle() );
        });*/
        cPlatform.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getPlatform() );
        });
        cYear.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getYear().toString() );
        });
        cDescription.setCellValueFactory( (row)->{
            return new SimpleStringProperty(row.getValue().getDescription() );
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue == null) return;
           Session.currentGame = newValue;
           GestorJuegos.loadFXML("views/game-view.fxml","Game Pro Manager - "+newValue.getTitle());
        });

        tableRefresh();
    }

    private void tableRefresh() {
        table.getItems().clear();

        new GameDAO(JdbcUtils.getCon()).findByUser(Session.currentUser).forEach((g)->{
            table.getItems().add(g);
        });
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        Session.currentUser=null;
        GestorJuegos.loadFXML("views/login-view.fxml","Game Pro Manager - Login");
    }

    @FXML
    public void addGame(ActionEvent actionEvent) {
        Session.currentGame = null;
        GestorJuegos.loadFXML("views/game-view.fxml","Game Pro Manager - New Game");
    }
}