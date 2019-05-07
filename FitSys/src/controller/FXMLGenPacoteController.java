package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import model.Aluno;
import model.Modalidade;
import model.Pacote;
import model.PacoteModalidade;
import util.Banco;
import util.Conexao;

public class FXMLGenPacoteController implements Initializable {

    @FXML
    private ListView<Modalidade> livModalidades;
    @FXML
    private ListView<Modalidade> livSelec;

    private List<Modalidade> selectedMod;
    @FXML
    private Label lbMensagem;
    @FXML
    private JFXTextField txtDesconto;
    @FXML
    private Label txtTotal;
    @FXML
    private JFXButton btnCancela;
    @FXML
    private JFXTextField txtCod;
    @FXML
    private JFXTextField txtDesc;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private TableView<Pacote> tabPacotes;
    @FXML
    private TableColumn<Pacote, Integer> tabCod;
    @FXML
    private TableColumn<Pacote, String> tabDesc;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnDeletar;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        selectedMod = new ArrayList<>();
        livModalidades.setItems(FXCollections.observableList(Modalidade.get("")));
        
        tabCod.setCellValueFactory(new PropertyValueFactory("cod"));
        tabDesc.setCellValueFactory(new PropertyValueFactory("descricao"));
        
        carregaTabela("");
    }
    
    private void carregaTabela(String filtro) {
        
        List<Pacote> res = Pacote.get(filtro);
        ObservableList<Pacote> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabPacotes.setItems(modelo);
    }

    @FXML
    private void btnSelecionar(ActionEvent event) 
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        if(livModalidades.getSelectionModel().getSelectedItem() != null)
        {
            if(!selectedMod.contains(livModalidades.getSelectionModel().getSelectedItem()))
            {
                selectedMod.add(livModalidades.getSelectionModel().getSelectedItem());
                livSelec.setItems(FXCollections.observableList(selectedMod));
                livSelec.refresh();
            }else
            {
                lbMensagem.setText("*Modalidade já inserida!");
            }
        }else
        {
            lbMensagem.setText("*Selecione uma modalidade!");
        }
        
        calcTotal(null);
    }

    @FXML
    private void btnRetirar(ActionEvent event) 
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        if(livSelec.getSelectionModel().getSelectedItem() != null)
        {
            if(selectedMod.contains(livModalidades.getSelectionModel().getSelectedItem()))
            {
                selectedMod.remove(livSelec.getSelectionModel().getSelectedItem());
                livSelec.setItems(FXCollections.observableList(selectedMod));
                livSelec.refresh();
            }else
            {
                lbMensagem.setText("*Modalidade invalida!");
            }
        }else
        {
            lbMensagem.setText("*Selecione uma modalidade!");
        }
        
        calcTotal(null);
    }
    
    @FXML
    private void calcTotal(KeyEvent event) {
        Double total = .00;
        
        for(Modalidade x : livSelec.getItems())
            total += x.getPreco();
        
        if(!txtDesconto.getText().equals(""))
            total -= total * Double.parseDouble(txtDesconto.getText()) / 100;
        
        txtTotal.setText(total.toString());
    }
    
    @FXML
    private void limpaCampos(ActionEvent event) 
    {
        txtCod.clear();
        txtDesc.clear();
        txtTotal.setText("00.00");
        selectedMod.clear();
        livSelec.refresh();
        txtDesconto.clear();
    }

    @FXML
    private void btnConfirmar(ActionEvent event) 
    {
        lbMensagem.setTextFill(Paint.valueOf("red"));
        PacoteModalidade pct_mod;
        if(!txtDesc.getText().isEmpty())
        {
            if(!livSelec.getItems().isEmpty())
            {
                if(txtCod.getText().isEmpty()) //inserção
                {
                    try
                    {
                        int desconto;
                        
                        if(!txtDesconto.getText().isEmpty())
                            desconto = Integer.parseInt(txtDesconto.getText());
                        else
                            desconto = 0;
                        
                        double total = Double.parseDouble(txtTotal.getText());
                        
                        Pacote p = new Pacote(txtDesc.getText(), desconto, selectedMod, total);
                        p.gravar();

                        for(Modalidade x : livSelec.getItems())
                        {
                            pct_mod = new PacoteModalidade(Banco.getCon().getMaxPK("pacote", "pct_cod"), x.getCod());
                            if(pct_mod.gravar())
                            {
                                lbMensagem.setTextFill(Paint.valueOf("green"));
                                lbMensagem.setText("Gravado com sucesso!");
                                carregaTabela("");
                                limpaCampos(null);
                            }else lbMensagem.setText("Erro ao gravar");
                        }
                    }catch(Exception ex){System.out.println("Erro: " + ex.getMessage());}
                }
                else //alteração
                {
                    try
                    {
                        PacoteModalidade.apagaTodos(Integer.parseInt(txtCod.getText()));
                        for(Modalidade x : livSelec.getItems())
                        {
                            pct_mod = new PacoteModalidade(Banco.getCon().getMaxPK("pacote", "pct_cod"), x.getCod());
                            pct_mod.gravar();
                        }
                        
                        int desconto;
                        
                        if(!txtDesconto.getText().isEmpty())
                            desconto = Integer.parseInt(txtDesconto.getText());
                        else
                            desconto = 0;
                        
                        double total = Double.parseDouble(txtTotal.getText());
                        
                        Pacote p = new Pacote(Integer.parseInt(txtCod.getText()), txtDesc.getText(), desconto, selectedMod, total);
                        if(p.alterar())
                            {
                                lbMensagem.setTextFill(Paint.valueOf("green"));
                                lbMensagem.setText("Alterado com sucesso!");
                                carregaTabela("");
                                limpaCampos(null);
                            }else lbMensagem.setText("*Erro ao alterar!");
                    }catch(Exception ex){System.out.println("Erro: " + ex.getMessage());}
                }
            }else{lbMensagem.setText("*Selecione pelo menos uma modalidade!");}
        }else{lbMensagem.setText("*Campo Descrição é obrigatório!");}
        
    }

    @FXML
    private void btnAlterar(ActionEvent event) 
    {
        limpaCampos(event);
        int pct_cod = tabPacotes.selectionModelProperty().get().getSelectedItem().getCod();
        int pct_porcdesconto = tabPacotes.selectionModelProperty().get().getSelectedItem().getDesconto();
        String desc = tabPacotes.selectionModelProperty().get().getSelectedItem().getDescricao();
        double tot = tabPacotes.selectionModelProperty().get().getSelectedItem().getTotal();
        
        txtCod.setText(""+pct_cod);
        txtDesc.setText(desc);
        txtDesconto.setText(""+pct_porcdesconto);
        txtTotal.setText(""+tot);
        
        List<Modalidade> lst_aux = tabPacotes.selectionModelProperty().get().getSelectedItem().getModalidades();

        
        for(Modalidade x : lst_aux)
            selectedMod.add(x);
        
        livSelec.refresh();
    }

    @FXML
    private void btnDeletar(ActionEvent event) 
    {
        try
        {
            int pct_cod = tabPacotes.selectionModelProperty().get().getSelectedItem().getCod();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Deseja realmente apagar?"); 
            if(a.showAndWait().get() == ButtonType.OK)
            {
                PacoteModalidade.apagaTodos(pct_cod);
                
                if(Pacote.deletar(pct_cod))
                {
                    lbMensagem.setTextFill(Paint.valueOf("green"));
                    lbMensagem.setText("*Pacote deletado com sucesso");
                    carregaTabela("");
                    limpaCampos(null);
                }
                else
                {
                    lbMensagem.setTextFill(Paint.valueOf("red"));
                    lbMensagem.setText("*Erro ao deletar o pacote!");
                }
            }
        }catch(Exception ex){
            lbMensagem.setTextFill(Paint.valueOf("red"));
            lbMensagem.setText("*Selecione um item!");
        }
        
    }
    
}
