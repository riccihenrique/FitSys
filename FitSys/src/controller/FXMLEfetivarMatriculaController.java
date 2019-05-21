package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    private TableView<Matricula> tbPacotes;
    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private Label lbMensagem;
    
    private Aluno alu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        lbMensagem.setText("*");
        cbPacotes.setItems(FXCollections.observableList(Pacote.get("")));
        tbPacotes.setItems(FXCollections.observableList(Matricula.getMatriculas("")));
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
        
        System.out.println(alu.getNome());
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
            lbMensagem.setTextFill(Paint.valueOf("green"));
            lbMensagem.setText("*Aluno Matriculado com Sucesso!");
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
