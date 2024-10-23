package org.example.gestorjuegosjdbc;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gestorjuegosjdbc.dao.GameDAO;
import org.example.gestorjuegosjdbc.dao.JdbcUtils;
import org.example.gestorjuegosjdbc.models.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    @javafx.fxml.FXML
    private Button close;
    @javafx.fxml.FXML
    private ImageView image;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerYear;
    @javafx.fxml.FXML
    private ComboBox<String> comboPlatform;
    @javafx.fxml.FXML
    private TextArea txtDescription;
    @javafx.fxml.FXML
    private Label lblTitle;
    @javafx.fxml.FXML
    private TextField txtTitle;

    private GameDAO gameDAO = new GameDAO(JdbcUtils.getCon());
    @javafx.fxml.FXML
    private Button btnSave;

    @javafx.fxml.FXML
    public void close(ActionEvent actionEvent) {
        Session.currentGame=null;
        GestorJuegos.loadFXML("views/main-view.fxml",
                "Game Pro Manager - "+Session.currentUser.getEmail());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboPlatform.getItems().addAll( gameDAO.getPlatforms());

        if(Session.currentGame==null){
            lblTitle.setText( "New Game" );
            spinnerYear.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory( 1980, 2024, 2024,1 )
            );
            return;
        }

        lblTitle.setText( Session.currentGame.getTitle() );
        comboPlatform.setValue(Session.currentGame.getPlatform());
        spinnerYear.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory( 1980, 2024, Session.currentGame.getYear(),1 )
        );
        txtDescription.setText( Session.currentGame.getDescription() );
        txtTitle.setText( Session.currentGame.getTitle() );
        image.setImage( new Image("file:covers/"+Session.currentGame.getImage_url()) );

        //image.setImage( new Image( getClass().getResource("media/moneda.png").toExternalForm() ));
    }

    @javafx.fxml.FXML
    public void saveOrUpdate(ActionEvent actionEvent) {
        if(Session.currentGame==null){

            if( txtTitle.getText().isBlank() ){
                return;
            }

            var g = new Game();
            g.setTitle(txtTitle.getText());
            g.setPlatform(comboPlatform.getValue());
            g.setYear(spinnerYear.getValue());
            g.setDescription(txtDescription.getText());
            g.setUser( Session.currentUser );
            g.setUser_id(Session.currentUser.getId());
            g.setImage_url("image"+(1+Math.round(Math.random()*6)+".jpg"));
            gameDAO.save(g);

            Session.currentGame=null;
            GestorJuegos.loadFXML("views/main-view.fxml",
                    "Game Pro Manager - "+Session.currentUser.getEmail());

        }
    }
}