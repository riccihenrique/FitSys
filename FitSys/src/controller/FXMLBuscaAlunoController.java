package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Aluno;
import model.Matricula;

public class FXMLBuscaAlunoController implements Initializable 
{

    @FXML
    private JFXRadioButton rbCpf;
    @FXML
    private ToggleGroup busca;
    @FXML
    private JFXRadioButton rbNome;
    @FXML
    private JFXTextField tbPesquisa;
    @FXML
    private TableView<Aluno> tbvAlunos;
    @FXML
    private TableColumn<Aluno, String> colMatricula;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private JFXButton btConfirmar;
    
    private Aluno alu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btConfirmar.setDisable(true);
        colNome.setCellValueFactory(new PropertyValueFactory("aluno"));
        colMatricula.setCellValueFactory(new PropertyValueFactory("cpf"));
        
        carregaTabela("");
    }    
    
    public Aluno getAluno()
    {
        return alu;
    }

    @FXML
    private void clkBuscar(ActionEvent event) {
        if(rbCpf.isSelected())
                carregaTabela("alu_Cpf = " + tbPesquisa.getText());
            else
                carregaTabela("UPPER(alu_nome) like '%" + tbPesquisa.getText().toUpperCase() + "%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tbvAlunos.getSelectionModel().getSelectedItem() != null)
        {
            btConfirmar.setDisable(false);
            alu = tbvAlunos.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
        if(tbvAlunos.getSelectionModel().getSelectedItem() != null)
        {
            Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
    }
    
     private void carregaTabela(String filtro) {
        
        List<Aluno> res = Aluno.get(filtro);
        ObservableList<Aluno> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbvAlunos.setItems(modelo);
    }
}