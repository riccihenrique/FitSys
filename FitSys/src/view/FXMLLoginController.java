/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.stage.Stage;
import util.Banco;
import util.MaskFieldUtil;

/**
 * FXML Controller class
 *
 * @author Alexandre
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private JFXTextField tfUser;
    @FXML
    private JFXPasswordField tfSenha;
    @FXML
    private Label lbAviso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tfUser.setStyle("-fx-prompt-text-fill: #ffffff");
        tfUser.setStyle("-fx-text-inner-color: #ffffff");
        tfSenha.setStyle("-fx-prompt-text-fill: #ffffff");
        tfSenha.setStyle("-fx-text-inner-color: #ffffff");
        MaskFieldUtil.cpfField(tfUser);
    }

    @FXML
    private void clkBtLogin(ActionEvent event) throws SQLException, IOException {

        String sql = "select * from funcionario where fun_cpf = '#1'";
        sql = sql.replaceAll("#1", tfUser.getText().replace(".", "").replace("-", ""));
        ResultSet rs = Banco.getCon().consultar(sql);
        if (rs.next()) {
            sql = "select * from funcionario where fun_senha = '#1'";
            sql = sql.replaceAll("#1", tfSenha.getText());
            rs = Banco.getCon().consultar(sql);
            if (rs.next()) {
                if (rs.getString("fun_nivel").equals("B")) {
                    lbAviso.setText("Usuário desativado");
                } 
                else 
                {
                    Stage stage = (Stage) lbAviso.getScene().getWindow(); //Obtendo a janela atual
                    stage.close();
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
}
