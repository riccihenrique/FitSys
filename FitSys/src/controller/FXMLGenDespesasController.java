package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLGenDespesasController implements Initializable {

    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnApagar;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private AnchorPane pnDados;
    @FXML
    private JFXComboBox<?> cbTpDespesa;
    @FXML
    private JFXTextField tbValor;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private JFXCheckBox chkQuitar;
    @FXML
    private AnchorPane tbDadosQuitado;
    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXTextField tbValorQuitado;
    @FXML
    private TableView<?> tbcDados;
    @FXML
    private TableColumn<?, ?> colCod;
    @FXML
    private TableColumn<?, ?> colTpDespesa;
    @FXML
    private TableColumn<?, ?> colVenc;
    @FXML
    private TableColumn<?, ?> colValor;
    @FXML
    private TableColumn<?, ?> colPaga;
    @FXML
    private AnchorPane pnDadosQuitar;
    @FXML
    private JFXComboBox<?> cbTpDespesa1;
    @FXML
    private JFXTextField tbValorQuitado1;
    @FXML
    private JFXDatePicker dtVencimento1;
    @FXML
    private JFXDatePicker dtPagamento1;
    @FXML
    private JFXTextField tbValor1;
    @FXML
    private JFXButton btCancelarQuitar;
    @FXML
    private JFXButton tbConfirmarQuitar;
    @FXML
    private TableView<?> tbvDadosQuitar;
    @FXML
    private TableColumn<?, ?> colCod1;
    @FXML
    private TableColumn<?, ?> colTpDespesa1;
    @FXML
    private TableColumn<?, ?> colVenc1;
    @FXML
    private TableColumn<?, ?> colValor1;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }

    @FXML
    private void clkNovo(ActionEvent event) {
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
    }

    @FXML
    private void clkApagar(ActionEvent event) {
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
    }

    @FXML
    private void clkTabela(MouseEvent event) {
    }

    @FXML
    private void clkCancelarQuitar(ActionEvent event) {
    }

    @FXML
    private void clkConfirmarQuitar(ActionEvent event) {
    }

    @FXML
    private void clkTabelaQuitar(MouseEvent event) {
    }
}
