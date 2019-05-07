/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Avaliacao;
import model.Matricula;
import util.Banco;

public class FXMLAgendarAvaliacaoController implements Initializable {

    @FXML
    private DatePicker dtPicker;
    @FXML
    private JFXComboBox<String> cbHorarios;
    @FXML
    private JFXButton BtnNovo;
    @FXML
    private JFXButton BtnAlterar;
    @FXML
    private JFXButton BtnConfirmar;
    @FXML
    private JFXButton BtnCancelar;
    @FXML
    private JFXTextField tbPesquisa;
    @FXML
    private JFXButton BtnPesquisar;
    @FXML
    private TableView<Matricula> tbvDados;
    @FXML
    private TableColumn<Matricula, String> colCpf;
    @FXML
    private TableColumn<Matricula, String> colNome;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        estadoOriginal();
    }    

    @FXML
    private void clkBtnPesquisar(ActionEvent event) {
        carregaTabela("UPPER(alu_nome) like '%" + tbPesquisa.getText().toUpperCase() + "%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tbvDados.getSelectionModel().getSelectedIndex() >=0)
        {
            BtnNovo.setDisable(false);
            BtnAlterar.setDisable(false);
        }
    }
    private void carregaTabela(String filtro) {
        List<Matricula> M = Matricula.getMatriculas(filtro);
        ObservableList<Matricula> modelo;
        modelo = FXCollections.observableArrayList(M);
        tbvDados.setItems(modelo);
    }
    
    private void carregaComboBox()
    {
        List <String> dadosCBHorario = new ArrayList();
        dadosCBHorario.add("08:00");
        dadosCBHorario.add("09:00");
        dadosCBHorario.add("10:00");
        dadosCBHorario.add("11:00");
        dadosCBHorario.add("14:00");
        dadosCBHorario.add("15:00");
        dadosCBHorario.add("16:00");
        dadosCBHorario.add("17:00");
        
        ObservableList <String> obsListConteudoCB = FXCollections.observableList(dadosCBHorario);
        cbHorarios.setItems(obsListConteudoCB);
        
        
    }
    
//     private void alterando()
//    {     
//          tbCpf.setDisable(true); 
//          BtnNovo.setDisable(true);  
//          tbPesquisa.setDisable(true);
//          pnDados.setDisable(false);
//          BtnConfirmar.setDisable(false);
//          BtnApagar.setDisable(true);
//          BtnAlterar.setDisable(true);
//          tbNome.setDisable(false);
//          tbNome.requestFocus(); 
//     }
    private void estadoEdicao()
    {     
          tbPesquisa.setDisable(false); 
          BtnNovo.setDisable(true);  
          tbPesquisa.setDisable(true);
          BtnConfirmar.setDisable(false);
          
          BtnAlterar.setDisable(true);
          cbHorarios.setDisable(false);
          dtPicker.setDisable(false);
     }
    private void estadoOriginal() {
            BtnPesquisar.setDisable(false);
            tbPesquisa.setDisable(false);
            BtnNovo.setDisable(true);
            tbvDados.setDisable(false);
            BtnConfirmar.setDisable(true);
            BtnCancelar.setDisable(false);
            
            BtnAlterar.setDisable(true);
            cbHorarios.setDisable(true);
            dtPicker.setDisable(true);

            cbHorarios.getItems().clear();
            //limpar horas
            tbPesquisa.setText("");
            
            carregaTabela("");
            carregaComboBox();
        }

    @FXML
    private void clkBtnNovo(ActionEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
            estadoEdicao();
    }

    @FXML
    private void clkBtnAlterar(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtnConfirmar(ActionEvent event) {
        if(cbHorarios.getSelectionModel().getSelectedItem()!=null && dtPicker.getValue()!=null)
        {
            Avaliacao AV = new Avaliacao(dtPicker.getValue(),"111111", tbvDados.getSelectionModel().getSelectedItem().getAluno().getCpf());
            
            
                if(!AV.gravar())
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
        if(!tbvDados.isDisable())
        {
            estadoOriginal();
        }
        else
        {
            Stage stage = (Stage) BtnConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
            
        }
    }
    
}
