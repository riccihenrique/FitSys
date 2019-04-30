/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Funcionario;
import util.Banco;

/**
 * FXML Controller class
 *
 * @author Alexandre
 */
public class FXMLGenFuncionarioController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXButton BtnNovo;
    @FXML
    private JFXButton BtnAlterar;
    @FXML
    private JFXButton BtnApagar;
    @FXML
    private JFXButton BtnConfirmar;
    @FXML
    private JFXButton BtnCancelar;
    @FXML
    private AnchorPane pnDados;
    @FXML
    private JFXTextField tbNome;
    @FXML
    private JFXTextField tbCpf;
    @FXML
    private JFXTextField tbCep;
    @FXML
    private JFXTextField tbRua;
    @FXML
    private JFXTextField tbTelefone;
    @FXML
    private JFXTextField tbCidade;
    @FXML
    private JFXTextField tbUf;
    @FXML
    private JFXTextField tbEmail;
    @FXML
    private JFXComboBox<Character> cbNvAcesso;
    @FXML
    private JFXComboBox<String> cbCargo;
    @FXML
    private DatePicker dtpickerNasc;
    @FXML
    private JFXTextField tbSenha;
    @FXML
    private JFXTextField tbPesquisa;
    @FXML
    private JFXButton BtnPesquisar;
    @FXML
    private TableView<Funcionario> tbvDados;
    @FXML
    private TableColumn<Funcionario, String> colCpf;
    @FXML
    private TableColumn<Funcionario, String> colNome;
    @FXML
    private TableColumn<Funcionario, String> colCargo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCpf.setCellValueFactory(new PropertyValueFactory("fun_cpf"));
        colNome.setCellValueFactory(new PropertyValueFactory("fun_nome"));
        colCargo.setCellValueFactory(new PropertyValueFactory("fun_cargo"));
        estadoOriginal();
        

        
    }    

    @FXML
    private void clkBtnNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtnAlterar(ActionEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            Funcionario F = (Funcionario)tbvDados.getSelectionModel().getSelectedItem();
            tbCpf.setText(F.getCpf());
            tbCep.setText(F.getCep());
            tbCidade.setText(F.getCidade());
            tbEmail.setText(F.getEmail());
            tbNome.setText(F.getNome());
            tbRua.setText(F.getRua());
            tbSenha.setText(F.getSenha());
            tbTelefone.setText(F.getTel());
            dtpickerNasc.valueProperty().set(F.getDt_nasc());
            tbUf.setText("");
            alterando();
            cbCargo.getSelectionModel().select(F.getCargo());
            cbNvAcesso.getSelectionModel().select(F.getNivel()); 
        }
    }

    @FXML
    private void clkBtnApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja realmente apagar?");
        if(a.showAndWait().get() == ButtonType.OK)
        {
            Funcionario F = (Funcionario)(tbvDados.getSelectionModel().getSelectedItem());
            a = new Alert(Alert.AlertType.INFORMATION);
            if(F.apagar())
            {
                snackBar("Funcionário deletado com sucesso");
                carregaTabela("");
            }
            else
            {
                a.setContentText("Erro ao deletar Funcionário");
                a.showAndWait();
            }
        }
        
    }

    @FXML
    private void clkBtnConfirmar(ActionEvent event) {
        Funcionario F = new Funcionario(tbCpf.getText(),tbNome.getText(),tbTelefone.getText(),tbRua.getText(),tbCidade.getText(),tbCep.getText(),tbEmail.getText(),dtpickerNasc.getValue(),tbSenha.getText(),cbCargo.getValue(),cbNvAcesso.getValue());
        if(tbCpf.isDisable())//alterando
        {
            if(!F.alterar())
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            else
            {
                JOptionPane.showMessageDialog(null, "Funcionario alterado!");
                estadoOriginal();
            }
        }
        else
        {
            if(!F.gravar())
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            else
            {
                JOptionPane.showMessageDialog(null, "Funcionario cadastrado!");
                estadoOriginal();
            } 
        }
    }

    @FXML
    private void clkBtnCancelar(ActionEvent event) {
        if(!pnDados.isDisable())
        {
            estadoOriginal();
        }
        else
        {
            if(tbCpf.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Não é possível cancelar esta operação");
            else
            {
                Stage stage = (Stage) BtnConfirmar.getScene().getWindow(); //Obtendo a janela atual
                stage.close(); //Fechando o Stage
            }
        }
    }

    @FXML
    private void clkBtnPesquisar(ActionEvent event) {
        carregaTabela("UPPER(fun_nome) like '%" + tbPesquisa.getText().toUpperCase() + "%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tbvDados.getSelectionModel().getSelectedIndex() >=0)
        {
            BtnAlterar.setDisable(false);
            BtnApagar.setDisable(false);
        }
    }
    
   
    private void snackBar(String texto)
    {
        JFXSnackbar snacbar = new JFXSnackbar(pnDados);
        JFXSnackbarLayout layout = new JFXSnackbarLayout(texto);
        layout.setStyle("-fx-backgroundcolor:#FFFFF");
        snacbar.fireEvent(new JFXSnackbar.SnackbarEvent(layout));
    }
    
    private void carregaTabela(String filtro) {
        Funcionario F = new Funcionario();
        List<Funcionario> res = F.get(filtro);
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbvDados.setItems(modelo);
    }
    
    private void alterando()
    {     
          tbCpf.setDisable(true); 
          BtnNovo.setDisable(true);  
          tbPesquisa.setDisable(true);
          pnDados.setDisable(false);
          BtnConfirmar.setDisable(false);
          BtnApagar.setDisable(true);
          BtnAlterar.setDisable(true);
          tbNome.setDisable(false);
          tbNome.requestFocus(); 
     }
    private void estadoEdicao()
    {     
          tbCpf.setDisable(false); 
          BtnNovo.setDisable(true);  
          tbPesquisa.setDisable(true);
          pnDados.setDisable(false);
          BtnConfirmar.setDisable(false);
          BtnApagar.setDisable(true);
          BtnAlterar.setDisable(true);
          tbNome.setDisable(false);
          tbCpf.requestFocus(); 
     }
    
    private void carregaComboBox(){
        //Carrega os combo box
        List <String> dadosCBCargo = new ArrayList();
        dadosCBCargo.add("Gerente");dadosCBCargo.add("Instrutor");dadosCBCargo.add("Recepcionista");
        ObservableList <String> obsListConteudoCB = FXCollections.observableList(dadosCBCargo);
        cbCargo.setItems(obsListConteudoCB);
        
        List <Character> dadosCBNV = new ArrayList();
        //Nivel de acesso do funcionário para tal CRUD,  Total, Apenas gravaçao, Apenas Leitura
        dadosCBNV.add('A');dadosCBNV.add('G');dadosCBNV.add('L');
        ObservableList <Character> obsListConteudoCBNV = FXCollections.observableList(dadosCBNV);
        cbNvAcesso.setItems(obsListConteudoCBNV);
    }
    
    private void estadoOriginal() {
            BtnPesquisar.setDisable(false);
            tbPesquisa.setDisable(false);
            BtnNovo.setDisable(true);
            pnDados.setDisable(true);
            BtnConfirmar.setDisable(true);
            BtnCancelar.setDisable(false);
            BtnApagar.setDisable(true);
            BtnAlterar.setDisable(true);
            BtnNovo.setDisable(false);
            tbCpf.setDisable(true);
            tbNome.setDisable(true);

            ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
            for (Node n : componentes) {
                if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                    ((TextInputControl) n).setText("");
                if (n instanceof ComboBox)
                    ((ComboBox) n).getItems().clear();
            }
            carregaTabela("");
            carregaComboBox();
        }

}
