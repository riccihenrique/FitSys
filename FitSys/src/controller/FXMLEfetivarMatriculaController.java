package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Aluno;
import model.Matricula;
import model.Pacote;

public class FXMLEfetivarMatriculaController implements Initializable 
{
    @FXML
    private JFXComboBox<Pacote> cbPacotes;
    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private Label lbMensagem;
    
    private Aluno alu;
    @FXML
    private TableView<Matricula> tbMatriculas;
    @FXML
    private TableColumn<Matricula, String> colNome;
    @FXML
    private TableColumn<Matricula, String> colPacote;
    @FXML
    private TableColumn<Matricula, String> colMat;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        lbMensagem.setText("*");
        cbPacotes.setItems(FXCollections.observableList(Pacote.get("")));
        tbMatriculas.setItems(FXCollections.observableList(Matricula.getMatriculas("")));
        
        //tabela
        colMat.setCellValueFactory(new PropertyValueFactory("cod"));
        colNome.setCellValueFactory(new PropertyValueFactory("aluno"));
        colPacote.setCellValueFactory(new PropertyValueFactory("pacote"));
        
        carregaTabela("");

    }
    
    private void carregaTabela(String filtro) 
    {
        List<Matricula> res = Matricula.getMatriculas(filtro);
        ObservableList<Matricula> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbMatriculas.setItems(modelo);
    }

    @FXML
    private void btnBuscaAluno(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLBuscaAluno.fxml"));
        Parent root = (Parent) loader.load();
        FXMLBuscaAlunoController ba = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

        alu = ba.getAluno();
        
        txtNome.setText(alu.getNome());
        txtCPF.setText(alu.getCpf());
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
    }

    @FXML
    private void btnConfirmar(ActionEvent event)
    {
        if(!txtNome.getText().isEmpty())
        {
            if(cbPacotes.getSelectionModel().getSelectedIndex() != -1)
            {
                Matricula new_mat = new Matricula(LocalDate.now(), alu, cbPacotes.getValue());
                if(new_mat.gravar())
                {
                    lbMensagem.setTextFill(Paint.valueOf("green"));
                    lbMensagem.setText("*Aluno Matriculado com Sucesso!");
                }else
                {
                    lbMensagem.setTextFill(Paint.valueOf("red"));
                    lbMensagem.setText("*Erro ao gerar a Matricula!");
                }   
            }else
            {
                lbMensagem.setTextFill(Paint.valueOf("red"));
                lbMensagem.setText("*Selecione um Pacote!");
            }
        }else
        {
            lbMensagem.setTextFill(Paint.valueOf("red"));
            lbMensagem.setText("*Selecione um Aluno!");
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event)
    {
        
    }
    
}
