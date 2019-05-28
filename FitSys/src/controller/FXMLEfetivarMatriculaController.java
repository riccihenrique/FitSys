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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Aluno;
import model.Matricula;
import model.Mensalidade;
import model.Pacote;
import model.PacoteModalidade;

public class FXMLEfetivarMatriculaController implements Initializable 
{
    private boolean flag_alt = false;
    private Matricula selected_mat = new Matricula();
    
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
    private TableColumn<Matricula, String> colCPF;
    @FXML
    private TableColumn<Matricula, String> colNome;
    @FXML
    private JFXTextField txtPgto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        lbMensagem.setText("*");
        cbPacotes.setItems(FXCollections.observableList(Pacote.get("")));
        tbMatriculas.setItems(FXCollections.observableList(Matricula.getMatriculas("")));
        
        //tabela
        colCPF.setCellValueFactory(new PropertyValueFactory("aluno.getCPF()"));
        colNome.setCellValueFactory(new PropertyValueFactory("aluno"));
        
        carregaTabela("");
    }
    
    private void carregaTabela(String filtro) 
    {
        List<Matricula> res = Matricula.getMatriculas(filtro);
        ObservableList<Matricula> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbMatriculas.setItems(modelo);
    }
    
    private void limparTela()
    {
        txtCPF.clear();
        txtNome.clear();
        flag_alt = false;
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
        
        if(alu != null)
        {
            txtNome.setText(alu.getNome());
            txtCPF.setText(alu.getCpf());
        }
    }

    @FXML
    private void btnAlterar(ActionEvent event) 
    {
        selected_mat = tbMatriculas.getSelectionModel().getSelectedItem();
        if(selected_mat != null)
        {
            Mensalidade men = new Mensalidade();
            men.get(selected_mat.getCod());
            int dia = men.getMen_dtvenc().getDayOfMonth();
            
            txtCPF.setText(selected_mat.getAluno().getCpf());
            txtNome.setText(selected_mat.getAluno().getNome());
            cbPacotes.getSelectionModel().select(selected_mat.getPacote());
            txtPgto.setText(""+dia);
            
            flag_alt = true;
            
            lbMensagem.setTextFill(Paint.valueOf("green"));
            lbMensagem.setText("*");
        }
        else
        {
            lbMensagem.setTextFill(Paint.valueOf("red"));
            lbMensagem.setText("*Selecione um item!");
        }
    }

    @FXML
    private void btnExcluir(ActionEvent event) 
    {
        try
        {
            int mat_cod = tbMatriculas.selectionModelProperty().get().getSelectedItem().getCod();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Deseja realmente apagar?"); 
            if(a.showAndWait().get() == ButtonType.OK)
            {
                if(Matricula.deletar(mat_cod))
                {
                    lbMensagem.setTextFill(Paint.valueOf("green"));
                    lbMensagem.setText("*Matricula deletada com sucesso");
                    carregaTabela("");
                }
                else
                {
                    lbMensagem.setTextFill(Paint.valueOf("red"));
                    lbMensagem.setText("*Erro ao deletar a matricula!");
                }
            }
        }catch(Exception ex){
            lbMensagem.setTextFill(Paint.valueOf("red"));
            lbMensagem.setText("*Selecione um item!");
        }
    }

    @FXML
    private void btnConfirmar(ActionEvent event)
    {
        if(!txtNome.getText().isEmpty())
        {
            if(cbPacotes.getSelectionModel().getSelectedIndex() != -1)
            {
                if(!txtPgto.getText().isEmpty())
                {
                    if(flag_alt = false) //inserir
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
                    }else//alterar
                    {
                        Matricula new_mat = new Matricula(selected_mat.getCod(), LocalDate.now(), alu, cbPacotes.getValue());
                        
                        if(new_mat.alterar())
                        {
                            lbMensagem.setTextFill(Paint.valueOf("green"));
                            lbMensagem.setText("*Aluno Matriculado com Sucesso!");
                        }else
                        {
                            lbMensagem.setTextFill(Paint.valueOf("red"));
                            lbMensagem.setText("*Erro ao gerar a Matricula!");
                        }      
                    }
                }else
                {
                    lbMensagem.setTextFill(Paint.valueOf("red"));
                    lbMensagem.setText("*Selecione um dia de pagamento!");
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
        
        limparTela();
        carregaTabela("");
    }

    @FXML
    private void btnCancelar(ActionEvent event)
    {
        limparTela();
        lbMensagem.setTextFill(Paint.valueOf("red"));
        lbMensagem.setText("*");
    }
    
}
