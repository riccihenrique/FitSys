/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Avaliacao;
import model.Funcionario;
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
    private TableColumn<Matricula, String> colNome;
    @FXML
    private TableColumn<Matricula, String> colMat;
    private static Funcionario fun;
    @FXML
    private JFXButton BtnApagar;
    @FXML
    private TableView<Avaliacao> tbvReservas;
    @FXML
    private TableColumn<Avaliacao, String> ColAlu;
    @FXML
    private TableColumn<Avaliacao, String> ColCod;
    @FXML
    private TableColumn<Avaliacao, String> ColHorario;
    @FXML
    private TableColumn<Avaliacao, String> ColData;
    private boolean alterando;
    @FXML
    private Label lbAviso;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alterando = false;
        colMat.setCellValueFactory(new PropertyValueFactory("cod"));
        colNome.setCellValueFactory(new PropertyValueFactory("aluno"));
        
        ColCod.setCellValueFactory(new PropertyValueFactory("av_cod"));
        ColAlu.setCellValueFactory(new PropertyValueFactory("alu_cpf"));
        ColHorario.setCellValueFactory(new PropertyValueFactory("hora"));
        ColData.setCellValueFactory(new PropertyValueFactory("dt_agendamento"));
        estadoOriginal();
    }

    @FXML
    private void clkBtnPesquisar(ActionEvent event) {
        carregaTabela("UPPER(alu_nome) like '%" + tbPesquisa.getText().toUpperCase() + "%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if (tbvDados.getSelectionModel().getSelectedIndex() >= 0) {
            BtnNovo.setDisable(false);
            //BtnAlterar.setDisable(false);
        }
    }

    private void carregaTabela(String filtro) {
        List<Matricula> M = Matricula.getMatriculas(filtro);
        ObservableList<Matricula> modelo;
        modelo = FXCollections.observableArrayList(M);
        tbvDados.setItems(modelo);
    }

    private void carregaComboBox() {
        List<String> dadosCBHorario = new ArrayList();
        dadosCBHorario.add("08:00");
        dadosCBHorario.add("09:00");
        dadosCBHorario.add("10:00");
        dadosCBHorario.add("11:00");
        dadosCBHorario.add("14:00");
        dadosCBHorario.add("15:00");
        dadosCBHorario.add("16:00");
        dadosCBHorario.add("17:00");
        List<String> HorasOcupadas;
        if(dtPicker.getValue() != null)
        {
            HorasOcupadas = Avaliacao.getHrOcupados(dtPicker.getValue());
            List<Avaliacao> A = Avaliacao.getAVS(dtPicker.getValue());
            ObservableList<Avaliacao> modelo;
            modelo = FXCollections.observableArrayList(A);
            tbvReservas.setItems(modelo);
            
            dadosCBHorario.removeAll(HorasOcupadas);
            if(dadosCBHorario.isEmpty())
            {
                //JOptionPane.showMessageDialog(null, "Não há mais horários disponíveis neste dia!");
                //usar um label- bem melhor
                cbHorarios.setDisable(true);
                //estadoOriginal();
            }
            else
                cbHorarios.setDisable(false);
        }
          
        ObservableList<String> obsListConteudoCB = FXCollections.observableList(dadosCBHorario);
        cbHorarios.setItems(obsListConteudoCB);
    }

    private void estadoEdicao() {
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
        BtnApagar.setDisable(true);
        cbHorarios.setDisable(true);
        dtPicker.setDisable(true);

        cbHorarios.getItems().clear();
        //limpar horas
        tbPesquisa.setText("");
        lbAviso.setText("");
        carregaTabela("");
        carregaComboBox();
    }

    @FXML
    private void clkBtnNovo(ActionEvent event) {
        if (tbvDados.getSelectionModel().getSelectedItem() != null) {
            estadoEdicao();
            BtnNovo.setDisable(true);
        }
    }

    @FXML
    private void clkBtnAlterar(ActionEvent event) {
        
        alterando = true;
        //Avaliacao AV = (Avaliacao)tbvReservas.getSelectionModel().getSelectedItem();
        //cbHorarios.setValue(AV.getHora());
        //dtPicker.setValue(AV.getDt_agendamento());
    }

    @FXML
    private void clkBtnConfirmar(ActionEvent event) {
        if (cbHorarios.getSelectionModel().getSelectedItem() != null && dtPicker.getValue() != null) {
            if(alterando == false)
            {
                Avaliacao AV = new Avaliacao(dtPicker.getValue(), cbHorarios.getSelectionModel().getSelectedItem(), tbvDados.getSelectionModel().getSelectedItem().getAluno().getCpf(), fun.getCpf());
                if (!AV.gravar()) 
                {
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
                } else 
                {
                
                estadoOriginal();
                }
            }
            else
            {
                Avaliacao AV = (Avaliacao) tbvReservas.getSelectionModel().getSelectedItem();
                AV.setDt_agendamento(dtPicker.getValue());
                AV.setHora(cbHorarios.getSelectionModel().getSelectedItem());
                AV.setFun_cpf(fun.getCpf());
                
                if (!AV.alterar()) 
                {
                    JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
                } else 
                {
                lbAviso.setText("Agendamento realizado!");
                    estadoOriginal();
                    alterando = false;
                }
            }
        }
        else
           lbAviso.setText("Preencha os campos de data e horário!");
    }

    @FXML
    private void clkBtnCancelar(ActionEvent event) {
        if (!tbvDados.isDisable()) {
            estadoOriginal();
        } else {
            Stage stage = (Stage) BtnConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
    }

    public void setFuncionario(Funcionario f) {
        fun = f;
    }

    public Funcionario getFun() {
        return fun;
    }

   
    @FXML
    private void actionDtPicker(ActionEvent event) {
        carregaComboBox();
        
    }

    @FXML
    private void clkBtnApagar(ActionEvent event) {
        if (tbvReservas.getSelectionModel().getSelectedItem() != null) 
        {
            
                Avaliacao AV = (Avaliacao)tbvReservas.getSelectionModel().getSelectedItem();
                System.out.println(AV.getAv_cod());
                if (!AV.apagar()) 
                {
                    JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
                } 
                else {
                    lbAviso.setText("Agendamento apagado!");
                    estadoOriginal();
                }
        }
    }

    @FXML
    private void clkTabelaAv(MouseEvent event) {
        if (tbvReservas.getSelectionModel().getSelectedIndex() >= 0) {
            BtnNovo.setDisable(true);
            BtnAlterar.setDisable(false);
            BtnApagar.setDisable(false);
        }
    }

}
