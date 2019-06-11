package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import util.Banco;
import util.MaskFieldUtil;

public class FXMLEfetivarMatriculaController implements Initializable 
{
    private boolean flag_alt;
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
    private TableColumn<Matricula, String> colNome;
    @FXML
    private JFXTextField txtPgto;
    @FXML
    private TableColumn<Matricula, Integer> colMat;
    
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
        
        flag_alt = false;
        MaskFieldUtil.numericField(txtPgto);
        carregaTabela("");
    }
    
    private void carregaTabela(String filtro) 
    {
        List<Matricula> res = Matricula.getMatriculas(filtro);
        ObservableList<Matricula> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbMatriculas.setItems(modelo);
        tbMatriculas.refresh();
    }
    
    private void limparTela()
    {
        txtCPF.clear();
        txtNome.clear();
        txtPgto.clear();
        carregaTabela("");
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
            alu = selected_mat.getAluno();
            txtCPF.setText(selected_mat.getAluno().getCpf());
            txtNome.setText(selected_mat.getAluno().getNome());

            cbPacotes.getItems().forEach((p) -> {
                if(p.getCod() == selected_mat.getPacote().getCod())
                    cbPacotes.getSelectionModel().select(p);
            });
                
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
            a.setContentText("Deseja realmente apagar a matricula e suas mensalidades?"); 
            if(a.showAndWait().get() == ButtonType.OK)
            {
                try
                {
                    Mensalidade.deletarTodos(mat_cod);
                    Matricula.deletar(mat_cod);
                    
                    lbMensagem.setTextFill(Paint.valueOf("green"));
                    lbMensagem.setText("*Matricula deletada com sucesso");
                    limparTela();
                }
                catch(Exception ex)
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
                    if(Integer.parseInt(txtPgto.getText()) <= 30 && Integer.parseInt(txtPgto.getText()) >= 0)
                    {
                        if(flag_alt == false) //inserir
                        {
                            Matricula new_mat = new Matricula(LocalDate.now(), alu, cbPacotes.getValue());
                            if(new_mat.gravar())
                            {
                                new_mat.setCod(Banco.getCon().getMaxPK("matricula", "mat_cod"));
                                Mensalidade.geraMensalidade(new_mat, Integer.parseInt(txtPgto.getText()), true);
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
                            
                            Mensalidade men = new Mensalidade();
                            men.get(selected_mat.getCod());
                            
                            try 
                            {
                                new_mat.alterar();
                                men.setMen_dtvenc(LocalDate.of(men.getMen_dtvenc().getYear(), men.getMen_dtvenc().getMonth(), Integer.parseInt(txtPgto.getText())));
                                men.alterar();
                                lbMensagem.setTextFill(Paint.valueOf("green"));
                                lbMensagem.setText("*Aluno Matriculado com Sucesso!");
                            }
                            catch (Exception e) 
                            {
                                lbMensagem.setTextFill(Paint.valueOf("red"));
                                lbMensagem.setText("*Erro ao alterar a Matricula!");
                            }
                        }    
                        
                        flag_alt = false;
                        limparTela();
                    }
                    else
                    {
                        lbMensagem.setTextFill(Paint.valueOf("red"));
                        lbMensagem.setText("*Dia de pagamento inválido!");
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
