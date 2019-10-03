
package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import control.ControlFuncionario;
import control.ControlTreino;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private JFXComboBox<Object> cbFuncionario;
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
    private TableView<Object> tbvDados;
    @FXML
    private TableColumn<String, String> colCod;
    @FXML
    private TableColumn<String, String> colData;
    @FXML
    private TableColumn<String, String> colVencimento;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private VBox pnDados2;
    @FXML
    private Tab tbDetalhes;
    @FXML
    private TableColumn<String, String> colFuncionario;
    
    String treinos[] = {"A", "B", "C", "D", "E"};
    int codTreino = 0;
    Object matricula;
    
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
                        tbPane.getTabs().add(t);
                    } 
                    catch (IOException ex) { }
                }
                else
                    tbPane.getTabs().remove(tbPane.getTabs().size() - 1);
            }
        });
        
        Object funcionarios = ControlFuncionario.buscar("");
        ObservableList <Object> obsFuncionario = FXCollections.observableList((List<Object>)funcionarios);
        cbFuncionario.setItems(obsFuncionario);
        estadoOriginal();
    }    

    @FXML
    private void clkBuscarAluno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLBuscaMatricula.fxml"));
        Parent root = (Parent) loader.load();
        FXMLBuscaMatriculaController buscaMatricula = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

        matricula = buscaMatricula.getMatricula();
        if(matricula != null)
        {
            tbMatricula.setText("" + ((List<Object>)matricula).get(0));
            tbAluno.setText(((List<Object>)matricula).get(1) + "");
            pnDados.setDisable(true);
            estadoOriginal();
            carregaTabela("mat_cod = " + ((List<Object>)matricula).get(0));
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        if(pnDados.isDisable())
        {
            Stage stage = (Stage) btConfirmar.getScene().getWindow();
            stage.close(); //Fechando o Stage 
        }
        else
            estadoOriginal();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) throws SQLException {
        boolean ok = true;
        int idx_tab;
        Banco.getCon().getConnection().setAutoCommit(false);

        if(cbFuncionario.getValue() == null)
        {
           cbFuncionario.setStyle("-fx-background-color:#e61919");
           ok = false; 
        }
        else
            cbFuncionario.setStyle("-fx-background-color:#fffff");
        
        if(tbPane.getTabs().size() == 1)
        {
            ok = false;
            JOptionPane.showMessageDialog(null, "Deve haver pelo menos um treino");
        }
        
        idx_tab = 0;
        for(Tab t : tbPane.getTabs())
        {
            if(idx_tab != 0)
            {
                ObservableList<Node> componentes = ((AnchorPane) t.getContent()).getChildren(); //”limpa” os componentes
                for (Node n : componentes)
                    if (n instanceof TableView) // textfield, textarea e htmleditor
                        if(((TableView) n).getItems().isEmpty())
                            ok = false;
            }
            idx_tab++;
        }
        
        if(verificaData())
            if(ok)
            {
                List<ObservableList<Object>> obListExTreino = new ArrayList<>();
                idx_tab = 0;
                for(Tab tab: tbPane.getTabs())
                {
                    if(idx_tab != 0)
                    {                            
                        ObservableList<Node> componentes = ((AnchorPane) tab.getContent()).getChildren(); //”limpa” os componentes
                        for (Node n : componentes) 
                            if (n instanceof TableView) // textfield, textarea e htmleditor
                                obListExTreino.add(((TableView) n).getItems());
                    }
                    idx_tab++;                                
                }
                
                if(codTreino == 0) // Novo Treino
                {
                    if(ControlTreino.inserir(dttTreino.getValue(), dttVenciTreino.getValue(), spQtdTreinos.getValue(),((List<Object>)matricula).get(0) ,cbFuncionario.getValue(), obListExTreino))
                        System.out.println("Gravado com sucesso");
                    else
                        System.out.println("Num gravo não");
                }
                else
                    if(ControlTreino.alterar(codTreino ,dttTreino.getValue(), dttVenciTreino.getValue(), spQtdTreinos.getValue(),((List<Object>)matricula).get(0) ,cbFuncionario.getValue(), obListExTreino))
                       System.out.println("Alterado");
                    else
                        System.out.println("Deu errado");
            }
        else
                System.out.println("há treinos sem montar");
    }

    @FXML
    private void clkNovo(ActionEvent event) {
        boolean ok = true;
        for(Treino tre: tbvDados.getItems())
            if(tre.getDataTreino().isBefore(LocalDate.now()) && tre.getDataProximo().isAfter(LocalDate.now()) || tre.getDataProximo().isEqual(LocalDate.now()) || tre.getDataTreino().isEqual(LocalDate.now()))
                ok = false;
        if(!ok)
            JOptionPane.showMessageDialog(null, "Já existe treino em vigor");
        else
            estadoEdicao();
        treino = new Treino();
    }

    @FXML
    private void clkAlterar(ActionEvent event) {
       if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            treino = (Treino)tbvDados.getSelectionModel().getSelectedItem();
            
            if(treino.getDataProximo().isBefore(LocalDate.now()))
                JOptionPane.showMessageDialog(null, "Impossível alterar treinos vencidos");
            else
            {
                estadoOriginal();
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
    }

    @FXML
    private void clkApagar(ActionEvent event) throws SQLException {
        if(tbvDados.getSelectionModel().getSelectedItem() != null)
        {
            treino = tbvDados.getSelectionModel().getSelectedItem(); 
                    
            if(treino.getDataProximo().isBefore(LocalDate.now()))
                JOptionPane.showMessageDialog(null, "Impossível excluir treinos vencidos");
            else
            {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Deseja realmente apagar?"); 
                if(a.showAndWait().get() == ButtonType.OK)
                {
                    if(ControlTreino.apagar((int)tbvDados.getSelectionModel().getSelectedItem().getCod()))
                    {
                        snackBar("Treino deletado com sucesso");
                        carregaTabela("");
                        estadoOriginal();   
                    }
                    else
                        snackBar("Erro ao deletar Treino");
                }
            }
            treino = new Treino();
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if(tbBusca.getText().isEmpty())
            carregaTabela("");
        else            
            if(rdioNome.isSelected())
                carregaTabela("treino_data = " + btnPesquisar.getText());
            else
                carregaTabela("treino_dataProx = " + btnPesquisar.getText());
    }
    
    private void estadoEdicao() {   
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
            tbBusca.setDisable(true);
        }
        else
        {
            btnApagar.setDisable(true);
            btnAlterar.setDisable(true);
            btnNovo.setDisable(false);
            btnPesquisar.setDisable(false);
            btConfirmar.setDisable(true);
            tbBusca.setDisable(false);
        }
        pnDados.setDisable(true);
        btCancelar.setDisable(false);
         
        dttTreino.setValue(LocalDate.now());
        dttVenciTreino.setValue(LocalDate.now());
        
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);
        spQtdTreinos.setValueFactory(values);
        
        for(int i = tbPane.getTabs().size() - 1; i > 0; i--)
                tbPane.getTabs().remove(i);
    }
    
    private void carregaTabela(String filtro) {
        
        List<Treino> res = Treino.get(filtro);
        ObservableList<Treino> modelo = FXCollections.observableArrayList(res);
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
    
    private void snackBar(String texto)  {
        JFXSnackbar snacbar = new JFXSnackbar(pnDados);
        JFXSnackbarLayout layout = new JFXSnackbarLayout(texto);
        layout.setStyle("-fx-backgroundcolor:#FFFFF");
        snacbar.fireEvent(new JFXSnackbar.SnackbarEvent(layout));
    }
    
    private boolean verificaData() {
        boolean a = true;
        
        for(Treino t: tbvDados.getItems())
            if(treino.getCod() != t.getCod())
                if(dttTreino.getValue().isBefore(t.getDataProximo()) || dttVenciTreino.getValue().isBefore(t.getDataProximo()))
                {
                    JOptionPane.showMessageDialog(null, "As datas estão contidas dentro de intervalos com treinos já existentes");
                    a = false;
                    break;
                }
        if(a)
            if(dttVenciTreino.getValue().isBefore(dttTreino.getValue()))
            {
                a = false;
                dttTreino.setStyle("-fx-background-color:#e61919");
                dttVenciTreino.setStyle("-fx-background-color:#e61919");
                JOptionPane.showMessageDialog(null, "Datas invertidas");
            }
            else
            {
                dttTreino.setStyle("-fx-background-color:#fffff");
                dttVenciTreino.setStyle("-fx-background-color:#fffff");
            }
        return a;
            
    }

}
