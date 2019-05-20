package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.ExercicioTreino;
import model.Funcionario;
import model.Matricula;
import model.Treino;
import util.Banco;

public class FXMLGerTreinoController implements Initializable {

    @FXML
    private JFXTextField tbMatricula;
    @FXML
    private JFXTextField tbAluno;
    @FXML
    private TabPane tbPane;
    @FXML
    private JFXDatePicker dttTreino;
    @FXML
    private JFXDatePicker dttVenciTreino;
    @FXML
    private JFXComboBox<Funcionario> cbFuncionario;
    @FXML
    private JFXButton btConfirmar;
    @FXML
    private Spinner<Integer> spQtdTreinos;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnApagar;
    @FXML
    private RadioButton rdioNome;
    @FXML
    private ToggleGroup busca;
    @FXML
    private RadioButton rdioCpf;
    @FXML
    private JFXTextField tbBusca;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private AnchorPane pnDados;
    @FXML
    private TableView<Treino> tbvDados;
    @FXML
    private TableColumn<Treino, String> colCod;
    @FXML
    private TableColumn<Treino, String> colData;
    @FXML
    private TableColumn<Treino, String> colVencimento;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private VBox pnDados2;
    @FXML
    private Tab tbDetalhes;
    @FXML
    private TableColumn<Treino, String> colFuncionario;
    
    private Matricula mat;
    private String[] treinos = {"A", "B", "C", "D", "E"};
    Treino treino;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicia as colunas da tabela
        colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        colData.setCellValueFactory(new PropertyValueFactory("dataTreino"));
        colVencimento.setCellValueFactory(new PropertyValueFactory("dataProximo"));
        colFuncionario.setCellValueFactory(new PropertyValueFactory("funcinario"));
        // Adiciona os numeros dos treinos
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);
        spQtdTreinos.setValueFactory(values);
        spQtdTreinos.valueProperty().addListener(new ChangeListener<Integer>() { 
            @Override
            public void changed(ObservableValue<? extends Integer> observable,//
                    Integer oldValue, Integer newValue) {
                if(oldValue < newValue)
                {
                    try
                    {
                        Tab t = new Tab();
                        t.setText("Treino " + treinos[newValue - 1]);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLTreino.fxml"));
                        Parent root = (Parent) loader.load();
                        t.setContent(root);
                        tbPane.getTabs().add(newValue, t);
                    } 
                    catch (IOException ex) { }
                }
                else
                    tbPane.getTabs().remove(tbPane.getTabs().size() - 1);
            }
        });
        
        List <Funcionario> funcionarios = Funcionario.get("");
        ObservableList <Funcionario> obsFuncionario = FXCollections.observableList(funcionarios);
        cbFuncionario.setItems(obsFuncionario);
        
        treino = new Treino();
        estadoOriginal();
    }    

    @FXML
    private void clkBuscarAluno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLBuscaAluno.fxml"));
        Parent root = (Parent) loader.load();
        FXMLBuscaAlunoController ba = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

        mat = ba.getMatricula();
        if(mat != null)
        {
            tbMatricula.setText("" + mat.getCod());
            tbAluno.setText(mat.getAluno().getNome());
            pnDados.setDisable(true);
            estadoOriginal();
            carregaTabela("mat_cod = " + mat.getCod());
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if(pnDados.isDisable())
        {
            Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
        else
            estadoOriginal();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        boolean ok = true;
        int i, c;
        Banco.getCon().getConnection().setAutoCommit(false);

        if(cbFuncionario.getValue() == null)
        {
           cbFuncionario.setStyle("-fx-background-color:#e61919");
           ok = false; 
        }
        else
            cbFuncionario.setStyle("-fx-background-color:#fffff");
        
        if(dttVenciTreino.getValue().isBefore(dttTreino.getValue()))
        {
            ok = false;
            dttTreino.setStyle("-fx-background-color:#e61919");
            dttVenciTreino.setStyle("-fx-background-color:#e61919");
        }
        else
        {
            dttTreino.setStyle("-fx-background-color:#fffff");
            dttVenciTreino.setStyle("-fx-background-color:#fffff");
        }
        
        if(tbPane.getTabs().size() == 1)
        {
            ok = false;
            JOptionPane.showMessageDialog(null, "Deve haver pelo menos um treino");
        }
        
        c = 0;
        for(Tab t : tbPane.getTabs())
        {
            if(c != 0)
            {
                ObservableList<Node> componentes = ((AnchorPane) t.getContent()).getChildren(); //”limpa” os componentes
                for (Node n : componentes)
                    if (n instanceof TableView) // textfield, textarea e htmleditor
                        if(((TableView) n).getItems().isEmpty())
                            ok = false;
            }
            c++;
        }
        
        if(ok)
        {
            if(treino.getCod() == 0) // Novo Treino
            {
                ok = true;
                treino = new Treino(dttTreino.getValue(), dttVenciTreino.getValue(), mat, cbFuncionario.getValue());

                if(treino.gravar())
                {
                    treino.setCod(Banco.getCon().getMaxPK("treino", "treino_cod"));
                    i = 0;
                    c = 0;
                    for(Tab tab: tbPane.getTabs())
                    {
                        if(c != 0)
                        {                            
                            ObservableList<Node> componentes = ((AnchorPane) tab.getContent()).getChildren(); //”limpa” os componentes
                            for (Node n : componentes) 
                            {
                                if (n instanceof TableView) // textfield, textarea e htmleditor
                                {
                                    ObservableList<ExercicioTreino> obExTrei  = ((TableView) n).getItems();
                                    for(ExercicioTreino ex : obExTrei)
                                    {
                                        ex.setTreino(treino);
                                        ex.setTipo(treinos[i].charAt(0));
                                        if(!ex.gravar())
                                        {
                                            JOptionPane.showMessageDialog(null, "Erro ao gravar exercicios: " + Banco.getCon().getMensagemErro());
                                            ok = false;
                                        }
                                    }
                                }
                            }
                            i++;
                        }
                        c++;                                
                    }

                    if(ok)
                    {
                        JOptionPane.showMessageDialog(null, "Treino inserido com sucesso:");
                        Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
                        stage.close(); //Fechando o Stage
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao gravar exercicos do trieno: " + Banco.getCon().getMensagemErro());
                        ok = false;
                    }
                    
                    tbAluno.clear();
                    tbMatricula.clear();;
                    mat = null;
                    estadoOriginal();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Erro ao gravar treino: " + Banco.getCon().getMensagemErro());
                    ok = false;
                }
            }
            else
            {
                if(treino.alterar())
                {
                    ok = ok && ExercicioTreino.apagar(treino.getCod());
                    
                    i = 0;
                    c = 0;
                    for(Tab tab: tbPane.getTabs())
                    {
                        if(c != 0)
                        {                            
                            ObservableList<Node> componentes = ((AnchorPane) tab.getContent()).getChildren(); //”limpa” os componentes
                            for (Node n : componentes) 
                            {
                                if (n instanceof TableView) // textfield, textarea e htmleditor
                                {
                                    ObservableList<ExercicioTreino> obExTrei  = ((TableView) n).getItems();
                                    for(ExercicioTreino ex : obExTrei)
                                    {
                                        ex.setTreino(treino);
                                        ex.setTipo(treinos[i].charAt(0));
                                        if(!ex.gravar())
                                        {
                                            JOptionPane.showMessageDialog(null, "Erro ao gravar exercicios: " + Banco.getCon().getMensagemErro());
                                            ok = false;
                                        }
                                    }
                                }
                            }
                            i++;
                        }
                        c++;                                
                    }

                    if(ok)
                    {
                        JOptionPane.showMessageDialog(null, "Treino alterado com sucesso:");
                        Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
                        stage.close(); //Fechando o Stage
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar exercicos do trieno: " + Banco.getCon().getMensagemErro());
                        ok = false;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Erro ao alterar treino: " + Banco.getCon().getMensagemErro());
                    ok = false;
                }
            }
            
            if(ok)
                Banco.getCon().getConnection().commit();
            else
                Banco.getCon().getConnection().rollback();
            
            Banco.getCon().getConnection().setAutoCommit(true);   
            treino = new Treino();
        }
        else
            JOptionPane.showMessageDialog(null, "Há treinos sem montar");
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
       if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            for(Tab t : tbPane.getTabs())
                if(t.getText().contains("Treino"))
                    tbPane.getTabs().remove(t);
            SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);
            spQtdTreinos.setValueFactory(values);
            
            treino = (Treino)tbvDados.getSelectionModel().getSelectedItem();
            dttTreino.setValue(treino.getDataTreino());
            dttVenciTreino.setValue(treino.getDataProximo());
            cbFuncionario.getSelectionModel().select(0);
            cbFuncionario.getSelectionModel().select(treino.getFuncinario());
            
            List<ExercicioTreino> l = ExercicioTreino.getEx(treino.getCod());
            int[] vet = new int[5];
            
            for(ExercicioTreino et : l)
                vet[et.getTipo() - 65]++;
            
            int c = 0, j = 0;
            while(c < 5 && vet[c] != 0)
            {
                spQtdTreinos.increment();
                
                Tab t = tbPane.getTabs().get(c + 1);
                
                for(int i = 0; i < vet[c]; i++)
                {
                    ObservableList<Node> componentes = ((AnchorPane) t.getContent()).getChildren();
                    for (Node n : componentes)
                        if (n instanceof TableView)
                            ((TableView) n).getItems().add(l.get(j++));
                }
                
                c++;
            }
            estadoEdicao();
        }
    }

    @FXML
    private void clkApagar(ActionEvent event) throws SQLException {
         Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja realmente apagar?"); 
        if(a.showAndWait().get() == ButtonType.OK)
        {
            Banco.getCon().getConnection().setAutoCommit(false);  
            a = new Alert(Alert.AlertType.INFORMATION);
            if(Treino.apagar(tbvDados.getSelectionModel().getSelectedItem().getCod()))
            {
                Banco.getCon().getConnection().commit();
                snackBar("Treino deletado com sucesso");
                carregaTabela("");
                estadoOriginal();
            }
            else
            {
                Banco.getCon().getConnection().rollback();
                a.setContentText("Erro ao deletar treino: " + Banco.getCon().getMensagemErro());
                a.showAndWait();
            }
            Banco.getCon().getConnection().setAutoCommit(true);      
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if(rdioNome.isSelected())
            carregaTabela("treino_data = " + btnPesquisar.getText());
        else
            carregaTabela("treino_dataProx = " + btnPesquisar.getText());
    }
    
    private void estadoEdicao()
    {   
        btnNovo.setDisable(true);  
        tbBusca.setDisable(true);
        pnDados.setDisable(false);
        btConfirmar.setDisable(false);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        dttTreino.setFocusTraversable(true);
        tbPane.setDisable(false);
    }
    
    private void estadoOriginal() {
        
        if(tbMatricula.getText().isEmpty())
        {
            btnApagar.setDisable(true);
            btnAlterar.setDisable(true);
            btnNovo.setDisable(true);
            btnPesquisar.setDisable(true);
            btConfirmar.setDisable(true);
        }
        else
        {
            btnApagar.setDisable(true);
            btnAlterar.setDisable(true);
            btnNovo.setDisable(false);
            btnPesquisar.setDisable(false);
            btConfirmar.setDisable(true);
        }
        pnDados.setDisable(true);
        btCancelar.setDisable(false);
         
        dttTreino.setValue(LocalDate.now());
        dttVenciTreino.setValue(LocalDate.now());
        
        for(Tab t : tbPane.getTabs())
            if(t.getText().contains("Treino"))
                tbPane.getTabs().remove(t);
        
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);
        spQtdTreinos.setValueFactory(values);
    }
    
    private void carregaTabela(String filtro) {
        
        List<Treino> res = Treino.get(filtro);
        ObservableList<Treino> modelo;
        modelo = FXCollections.observableArrayList(res);
        tbvDados.setItems(modelo);
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            btnAlterar.setDisable(false);
            btnApagar.setDisable(false);
        }
    }
    
     private void snackBar(String texto)
    {
        JFXSnackbar snacbar = new JFXSnackbar(pnDados);
        JFXSnackbarLayout layout = new JFXSnackbarLayout(texto);
        layout.setStyle("-fx-backgroundcolor:#FFFFF");
        snacbar.fireEvent(new JFXSnackbar.SnackbarEvent(layout));
    }
}
