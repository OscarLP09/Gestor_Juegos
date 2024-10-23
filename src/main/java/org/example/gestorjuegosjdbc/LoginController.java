package org.example.gestorjuegosjdbc;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.gestorjuegosjdbc.dao.JdbcUtils;
import org.example.gestorjuegosjdbc.dao.UserDAO;
import org.example.gestorjuegosjdbc.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private Button btnEnter;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private Button btnExit;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Label info;


    @javafx.fxml.FXML
    public void enterApp(ActionEvent actionEvent) {
        User user = new UserDAO(JdbcUtils.getCon()).validateUser(
                txtEmail.getText(),
                txtPassword.getText());

        if(user == null) {
            info.setText("User not found");
        }else{
            info.setText("User logged in");
            Session.currentUser=user;
            GestorJuegos.loadFXML("views/main-view.fxml","Game Pro Manager - "+user.getEmail());
        }
    }

    @javafx.fxml.FXML
    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}