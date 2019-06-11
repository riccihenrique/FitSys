
package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Funcionario;
import util.Banco;
import util.MaskFieldUtil;

public class FXMLLoginController implements Initializable {

    @FXML
    private JFXTextField tfUser;
    @FXML
    private JFXPasswordField tfSenha;
    @FXML
    private Label lbAviso;
    Funcionario F;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.cpfField(tfUser);
        
        try
        {
            if(!Banco.getCon().consultar("select * from funcionario").next())
            {
                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLGenFuncionario.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Cadastro de Funcuinário");

                stage.showAndWait();
                 if(!Banco.getCon().consultar("select * from funcionario").next())
                    System.exit(-1);
            }
        }
        catch(Exception e){ }
       
    }

    @FXML
    private void clkBtLogin(ActionEvent event) throws SQLException, IOException {

        String sql = "select * from funcionario where fun_cpf = '#1'";
        sql = sql.replaceAll("#1", tfUser.getText().replace(".", "").replace("-", ""));
        ResultSet rs = Banco.getCon().consultar(sql);
        if (rs.next()) {
            F = new Funcionario(rs.getString("fun_cpf"), rs.getString("fun_nome"), rs.getString("fun_cargo"), rs.getString("fun_nivel").charAt(0), rs.getString("fun_senha"));
            if (F.getSenha().equals(tfSenha.getText())) {
                if (F.getNivel() == 'B') {
                    lbAviso.setText("Usuário desativado");
                } 
                else 
                {
                    Parent root = null;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMain.fxml"));
                    root = (Parent) loader.load();
                    FXMLMainController controlador = loader.getController(); 
                    controlador.setFuncionario(F);
                   
                    Stage stage = (Stage) lbAviso.getScene().getWindow(); //Obtendo a janela atual
                    Scene scene = new Scene(root);
                    stage.setTitle("Home");
                    stage.setMaximized(true);
                    stage.setScene(scene);
                    stage.show();
                }
            } 
            else 
            {
                lbAviso.setText("Senha Inválida");
            }

        } else {
            lbAviso.setText("Usuário Inexistente");
        }
    }    

    @FXML
    private void clkEnter(KeyEvent event) throws SQLException, IOException {
        if(event.getCode() == KeyCode.ENTER)
            clkBtLogin(null);
    }
}
