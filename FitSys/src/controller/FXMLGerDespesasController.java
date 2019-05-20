package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Despesa;
import model.TipoDespesa;
import util.Banco;
import util.MaskFieldUtil;

public class FXMLGerDespesasController implements Initializable {

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
    private JFXComboBox<TipoDespesa> cbTpDespesa;
    @FXML
    private JFXTextField tbValor;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private JFXCheckBox chkQuitar;
    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXTextField tbValorQuitado;
    @FXML
    private TableColumn<Despesa, String> colCod;
    @FXML
    private TableColumn<Despesa, String> colTpDespesa;
    @FXML
    private TableColumn<Despesa, String> colVenc;
    @FXML
    private TableColumn<Despesa, String> colValor;
    @FXML
    private TableColumn<Despesa, String> colPaga;
    @FXML
    private TableColumn<Despesa, String> colPag;
    @FXML
    private RadioButton rdioTipo;
    @FXML
    private ToggleGroup busca;
    @FXML
    private RadioButton rdioVenc;
    @FXML
    private JFXTextField tbBusca;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private JFXTextField tbTotal;
    @FXML
    private AnchorPane pnDados;
    @FXML
    private TableView<Despesa> tbvDados;
    
    private Despesa despesa = new Despesa();
    @FXML
    private AnchorPane pnDadosQuitado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        colTpDespesa.setCellValueFactory(new PropertyValueFactory("tpDespesa"));
        colVenc.setCellValueFactory(new PropertyValueFactory("vencimento"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colPaga.setCellValueFactory(new PropertyValueFactory("paga"));
        colPag.setCellValueFactory(new PropertyValueFactory("pagamento"));        
        
        MaskFieldUtil.monetaryField(tbTotal);
        MaskFieldUtil.monetaryField(tbValor);
        MaskFieldUtil.monetaryField(tbValorQuitado);
        
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
            despesa = tbvDados.getSelectionModel().getSelectedItem();
            tbValor.setText("" + despesa.getValor());
            cbTpDespesa.getSelectionModel().select(0);
            cbTpDespesa.getSelectionModel().select(despesa.getTpDespesa());
            dtPagamento.setValue(despesa.getPagamento());
            dtVencimento.setValue(despesa.getVencimento());
            
            chkQuitar.setSelected(despesa.isPaga());
            tbValorQuitado.setDisable(despesa.isPaga());
            estadoEdicao();
        }
    }

    @FXML
    private void clkApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja realmente apagar?"); 
        if(a.showAndWait().get() == ButtonType.OK)
        {
            a = new Alert(Alert.AlertType.INFORMATION);
            if(Despesa.apagar(tbvDados.getSelectionModel().getSelectedItem().getCod()))
            {
                snackBar("Despesa deletada com sucesso");
                carregaTabela("");
                estadoOriginal();
            }
            else
            {
                a.setContentText("Erro ao deletar despesa: " + Banco.getCon().getMensagemErro());
                a.showAndWait();
            }
        }
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
        Alert al;
        if(isOk())
        {
            if(despesa.getCod() == 0) //Insere
            {
                despesa = new Despesa(Double.parseDouble(tbValor.getText().replace(".", "").replace(",", ".")), dtVencimento.getValue(),
                        cbTpDespesa.getValue(),chkQuitar.isSelected() ? dtPagamento.getValue() : null , chkQuitar.isSelected());
                
                if(despesa.gravar())
                {
                    snackBar("Despesa gravada com sucesso"); 
                    despesa = new Despesa();
                    estadoOriginal();
                }
                else
                {
                    al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Erro ao gravar despesa: " + Banco.getCon().getMensagemErro());
                    al.showAndWait();
                }
            }
            else // Altera
            {
                int cod = despesa.getCod();
                despesa = new Despesa(cod, Double.parseDouble(tbValor.getText().replace(".", "").replace(",", ".")), dtVencimento.getValue(),
                cbTpDespesa.getValue(),chkQuitar.isSelected() ? dtPagamento.getValue() : null , chkQuitar.isSelected());
                
                if(despesa.alterar())
                {
                    snackBar("Despesa alterado com sucesso");
                    despesa = new Despesa();
                    estadoOriginal();
                }
                else 
                {
                    al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Erro ao alterar Despesa: " + Banco.getCon().getMensagemErro());
                    al.showAndWait();
                }
            }
            estadoOriginal();
        } 
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if(!pnDados.isDisable())
            estadoOriginal();
        else
        {
            Stage stage = (Stage) btnConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            btnAlterar.setDisable(false);
            btnApagar.setDisable(false);
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if(rdioTipo.isSelected())
            carregaTabela("tpd_cod = " + tbBusca.getText());
        else
            carregaTabela("desp_dtvencimento <= '" + tbBusca.getText().replace("/", "") + "'");
    }
    
    private void estadoOriginal() {
        btnPesquisar.setDisable(false);
        btnNovo.setDisable(true);
        pnDados.setDisable(true);
        btnConfirmar.setDisable(true);
        btnCancelar.setDisable(false);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        btnNovo.setDisable(false);
        tbBusca.setDisable(false);
        
        dtPagamento.setValue(LocalDate.now());
        dtVencimento.setValue(LocalDate.now());
        
        ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                ((TextInputControl) n).setText("");
            if (n instanceof ComboBox)
                ((ComboBox) n).getItems().clear();
        }  
        
        componentes = pnDadosQuitado.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                ((TextInputControl) n).setText("");
            if (n instanceof ComboBox)
                ((ComboBox) n).getItems().clear();
        }
       
        carregaTabela("");
        carregaComboBox();
    }
    
    private void carregaTabela(String filtro) {
        
        List<Despesa> res = Despesa.get(filtro);
        ObservableList<Despesa> modelo = FXCollections.observableArrayList(res);
        tbvDados.setItems(modelo);
        calculaValor();
    }

    private void estadoEdicao() {   
        btnNovo.setDisable(true);  
        tbBusca.setDisable(true);
        pnDados.setDisable(false);
        btnConfirmar.setDisable(false);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        
        cbTpDespesa.requestFocus();
    }
    
    private boolean isOk() {
        boolean res = true;
        
        ObservableList<Node> componentes = pnDados.getChildren();
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
        
        if(chkQuitar.isSelected())
        {
            componentes = pnDadosQuitado.getChildren();
            for (Node n : componentes) {
                if (n instanceof TextInputControl)
                    if(((TextInputControl) n).getText().isEmpty())
                    {
                        n.setStyle("-fx-background-color:#e61919");
                        res = false;
                    }
                    else
                        n.setStyle("-fx-background-color:#ffff");
                if (n instanceof ComboBox)
                    if(((ComboBox) n).getSelectionModel().getSelectedItem()== null)
                    {
                        n.setStyle("-fx-background-color:#e61919");
                        res = false;
                    }
                    else
                        n.setStyle("-fx-background-color:#ffff");
            }
            
            if(dtVencimento.getValue() != null && dtPagamento.getValue().isBefore(dtVencimento.getValue()))
                JOptionPane.showMessageDialog(null, "A data de pagamento não deve ser menor que a data de vencimento");  
        }
        
        return res;
    }
    
    private void snackBar(String texto) {
        JFXSnackbar snacbar = new JFXSnackbar(pnDados);
        JFXSnackbarLayout layout = new JFXSnackbarLayout(texto);
        layout.setStyle("-fx-backgroundcolor:#FFFFF");
        snacbar.fireEvent(new JFXSnackbar.SnackbarEvent(layout));
    }

    @FXML
    private void clkRdio(ActionEvent event) {
        if(rdioTipo.isSelected())
            tbBusca.setPromptText("Digite um Tipo de Despesa:");
        else
            tbBusca.setPromptText("Digite uma Data de Vencimento:");
    }
    
    private void carregaComboBox() { 
        List <TipoDespesa> l = TipoDespesa.get("");
        ObservableList<TipoDespesa> tpdesp = FXCollections.observableList(l);
        cbTpDespesa.setItems(tpdesp);
    }

    @FXML
    private void clkQuitar(ActionEvent event) {
        if(chkQuitar.isSelected())
            pnDadosQuitado.setDisable(false);
        else
        {
            tbValorQuitado.setStyle("-fx-background-color:#ffff");
            dtPagamento.setStyle("-fx-background-color:#ffff");
            pnDadosQuitado.setDisable(true);
        } 
    }
    
    private void calculaValor()
    {
        double val = 0;
        for(Despesa d : tbvDados.getItems())
            if(!d.isPaga())
                val += d.getValor();
        
        tbTotal.setText("" + val);
    }
    
}
