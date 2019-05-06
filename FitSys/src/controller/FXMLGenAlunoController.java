package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Aluno;
import org.json.JSONObject;
import util.Banco;
import util.BuscaCep;
import util.MaskFieldUtil;
import util.ValidaCpf;

public class FXMLGenAlunoController implements Initializable {

    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private AnchorPane pnDados1;
    @FXML
    private JFXTextField tbCpf;
    @FXML
    private JFXTextField tbNome;
    @FXML
    private JFXTextField tbEmail;
    @FXML
    private JFXTextField tbCep;
    @FXML
    private JFXComboBox<String> cbSexo;
    @FXML
    private JFXDatePicker dttNascimemto;
    @FXML
    private JFXTextField tbCidade;
    @FXML
    private VBox pnDados2;
    @FXML
    private JFXTextField tbBusca;
    @FXML
    private TableView<Aluno> tbvDados;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private TableColumn<Aluno, String> colTelefone;
    @FXML
    private JFXTextField tbTelefone;
    @FXML
    private JFXTextField tbEndereco;
    @FXML
    private TableColumn<Aluno, String> colEmail;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private JFXButton btnApagar;

    boolean cpf = false;
    @FXML
    private RadioButton rdioNome;
    @FXML
    private RadioButton rdioCpf;
    @FXML
    private ToggleGroup busca;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        
        MaskFieldUtil.cepField(tbCep);
        MaskFieldUtil.cpfField(tbCpf);
        MaskFieldUtil.foneField(tbTelefone);
        
        tbCpf.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
            {
                if(!newValue)
                {
                    if(ValidaCpf.validaCpf(tbCpf.getText()))
                    {
                        cpf = true;
                        tbCpf.setStyle("-fx-background-color:#fffff");
                    }
                    else
                    {
                        cpf = false;
                        tbCpf.setStyle("-fx-background-color:#e61919");
                    }
                }
             }
        });
        
        tbCep.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
            {
                if(!newValue)
                {
                    String json = BuscaCep.consultaCep(tbCep.getText().replace("-", ""));
                    Platform.runLater(()-> {
                        JSONObject ob = new JSONObject(json);
                        tbCidade.setText(ob.getString("localidade"));
                        tbEndereco.setText(ob.getString("logradouro"));
                    });
                }
            }
        });

        estadoOriginal();
    } 

    @FXML
    private void clkNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            Aluno a = (Aluno)tbvDados.getSelectionModel().getSelectedItem();
            tbCpf.setText(a.getCpf());
            tbNome.setText(a.getNome());
            tbCep.setText(a.getCEP());
            tbCidade.setText(a.getCidade());
            tbEndereco.setText(a.getRua());
            tbTelefone.setText(a.getTel());
            tbEmail.setText(a.getEmail());
            dttNascimemto.setValue(a.getDt_nasc());
            cbSexo.setValue(a.getSexo());
            
            estadoEdicao();    
            tbCpf.setDisable(true);
        }
    }

    @FXML
    private void clkConfirmar(ActionEvent event)
    {
        if(isOk())
        {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            Aluno a = new Aluno();
            
            a.geti(tbCpf.getText());
            if(a.getCpf() == null)
            {
                a = new Aluno(tbCpf.getText(), tbNome.getText(), tbTelefone.getText(), tbEndereco.getText(), 
                        tbCidade.getText(), tbCep.getText(), cbSexo.getValue(), tbEmail.getText(),
                        dttNascimemto.getValue());
                
                if(a.gravar())
                {
                    snackBar("Aluno gravado com sucesso"); 
                    estadoOriginal();
                }
                else
                {
                    al.setContentText("Erro ao gravar o aluno: " + Banco.getCon().getMensagemErro());
                    al.showAndWait();
                }
            }
            else
            {
                a = new Aluno(tbCpf.getText(), tbNome.getText(), tbTelefone.getText(), tbEndereco.getText(), 
                        tbCidade.getText(), tbCep.getText(), cbSexo.getValue(), tbEmail.getText(),
                        dttNascimemto.getValue());
                if(a.alterar())
                {
                    snackBar("Aluno alterado com sucesso");
                    estadoOriginal();
                }
                else 
                {
                    al.setContentText("Erro ao alterar o aluno: " + Banco.getCon().getMensagemErro());
                    al.showAndWait();
                }
            }
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
         if(!pnDados1.isDisable())
            estadoOriginal();
        else
        {
            Stage stage = (Stage) btnConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if(rdioNome.isSelected())
            carregaTabela("UPPER(alu_nome) like '%" + tbBusca.getText().toUpperCase() + "%'");
        else
            carregaTabela("alu_cpf like '%" + tbBusca.getText().replace(".", "").replace("-", "") + "%'");
    }     

    @FXML
    private void clkApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja realmente apagar?"); 
        if(a.showAndWait().get() == ButtonType.OK)
        {
            a = new Alert(Alert.AlertType.INFORMATION);
            if(Aluno.apagar(tbvDados.getSelectionModel().getSelectedItem().getCpf()))
            {
                snackBar("Aluno deletado com sucesso");
                carregaTabela("");
                estadoOriginal();
            }
            else
            {
                a.setContentText("Erro ao deletar aluno: " + Banco.getCon().getMensagemErro());
                a.showAndWait();
            }
        }
    }
    
    private void estadoOriginal() {
        btnPesquisar.setDisable(false);
        btnNovo.setDisable(true);
        pnDados1.setDisable(true);
        btnConfirmar.setDisable(true);
        btnCancelar.setDisable(false);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        btnNovo.setDisable(false);
        tbBusca.setDisable(false);
        dttNascimemto.setValue(LocalDate.now());
        ObservableList<Node> componentes = pnDados1.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                ((TextInputControl) n).setText("");
            if (n instanceof ComboBox)
                ((ComboBox) n).getItems().clear();
        }
        
        List<String> l = new ArrayList<>();
        l.add("Feminino");
        l.add("Masculino");
        ObservableList o = FXCollections.observableList(l);
        cbSexo.setItems(o);
        
        tbCpf.setStyle("-fx-background-color:#fffff");
        carregaTabela("");
    }
    
    private void carregaTabela(String filtro) {
        
        List<Aluno> res = Aluno.get(filtro);
        ObservableList<Aluno> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbvDados.setItems(modelo);
    }

    private void estadoEdicao()
    {   
        btnNovo.setDisable(true);  
        tbBusca.setDisable(true);
        pnDados1.setDisable(false);
        btnConfirmar.setDisable(false);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        tbNome.setDisable(false);
        tbCpf.requestFocus(); 
        
        tbCpf.setFocusTraversable(false);
        
        tbCpf.setDisable(false);
    }
    
    private boolean isOk()
    {
        boolean res = true;
        if(!cpf)
            return false;
        
        ObservableList<Node> componentes = pnDados1.getChildren();
        for (Node n : componentes) {
            if (n instanceof TextInputControl &&  ((TextInputControl) n).getText().isEmpty())
            {
                n.setStyle("-fx-background-color:#e61919");
                res = false;
            }
            if (n instanceof ComboBox && ((ComboBox) n).getSelectionModel().getSelectedItem()== null)
            {
                n.setStyle("-fx-background-color:#e61919");
                res = false;
            }
        }
        return res;
    }
    
    private void snackBar(String texto)
    {
        JFXSnackbar snacbar = new JFXSnackbar(pnDados1);
        JFXSnackbarLayout layout = new JFXSnackbarLayout(texto);
        layout.setStyle("-fx-backgroundcolor:#FFFFF");
        snacbar.fireEvent(new JFXSnackbar.SnackbarEvent(layout));
    }

    @FXML
    private void clkTbvDados(MouseEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            btnAlterar.setDisable(false);
            btnApagar.setDisable(false);
        }
    }

    @FXML
    private void clkRdio(ActionEvent event) {
        tbBusca.setText("");
        if(rdioCpf.isSelected())
        {
            MaskFieldUtil.cpfField(tbBusca);
            tbBusca.setPromptText("Digite um CPF");
        }
        else
        {
            MaskFieldUtil.onlyAlfaNumericValue(tbBusca);
            tbBusca.setPromptText("Digite um nome");
        }
    }
}
