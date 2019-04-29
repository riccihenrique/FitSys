package controller;

import groovy.sql.DataSet;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.Banco;

public class FXMLMainController implements Initializable {

    @FXML
    private MenuItem btnConf;
    @FXML
    private MenuItem btnSair;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       try
       {
            if(!Banco.getCon().consultar("select * from parametrizacao").next())
            {
                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLParametrizacao.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                
                stage.showAndWait();
                 if(!Banco.getCon().consultar("select * from parametrizacao").next())
                    System.exit(-1);
            }
       }
       catch(Exception e)
       {
           
       }
    }    

    @FXML
    private void btnConf(ActionEvent event) {
    }

    @FXML
    private void btnSair(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void btnGerAluno(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLGenAluno.fxml"));
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();        
    }

    @FXML
    private void btnGerFunc(ActionEvent event) {
    }

    @FXML
    private void btnGerPacote(ActionEvent event) {
    }

    @FXML
    private void btnBkp(ActionEvent event) {
        Banco.realizaBackup("backup");
    }

    @FXML
    private void btnRestore(ActionEvent event) {
        Banco.realizaBackup("restore");
    }
    
}
