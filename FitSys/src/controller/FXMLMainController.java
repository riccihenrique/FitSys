package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.Banco;

public class FXMLMainController implements Initializable {

    @FXML
    private MenuItem btnConf;
    @FXML
    private MenuItem btnSair;
    @FXML
    private Label lbNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       try
       {
           ResultSet rs;
           if(!Banco.getCon().consultar("select * from funcionario").next())
            {
                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLGenFuncionario.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                
                stage.showAndWait();
                 if(!Banco.getCon().consultar("select * from funcionario").next())
                    System.exit(-1);
            }
           
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLLogin.fxml"));
                
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
           
            rs = Banco.getCon().consultar("select * from parametrizacao");
            if(!rs.next())
            {
                root = FXMLLoader.load(getClass().getResource("/view/FXMLParametrizacao.fxml"));

                stage = new Stage();
                scene = new Scene(root);
                stage.setScene(scene);

                stage.showAndWait();
                
                rs = Banco.getCon().consultar("select * from parametrizacao");
                if(!rs.next())
                   System.exit(-1);
            }
            
            lbNome.setText(rs.getString("razao_social"));
       }
       catch(Exception e) { }
    }    

    @FXML
    private void btnConf(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLParametrizacao.fxml"));
                
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();
        
        ResultSet rs = Banco.getCon().consultar("select * from parametrizacao");
        if(rs.next())
            lbNome.setText(rs.getString("razao_social"));
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
    private void btnGerFunc(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLGenFuncionario.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();
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

    @FXML
    private void clkGerarTreino(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLGerTreino.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();
    }

    @FXML
    private void clkAgendarAV(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAgendarAvaliacao.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();
    }

    @FXML
    private void clkEfetuarAV(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLLogin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();   
    }
}
        
       


        
    


