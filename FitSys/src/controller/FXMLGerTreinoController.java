package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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

    private Matricula mat;
    private String[] treinos = {"A", "B", "C", "D", "E"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {
            Tab t = new Tab();
            t.setText("Treino " + treinos[0]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLTreino.fxml"));
            Parent root = null;
            root = (Parent) loader.load();
            t.setContent(root);
            tbPane.getTabs().add(t);
        }
        catch(Exception e){}
        
        tbPane.setDisable(true);
        btConfirmar.setDisable(true);
        
        dttTreino.setValue(LocalDate.now());
        dttVenciTreino.setValue(LocalDate.now());
        
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5);
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
                    tbPane.getTabs().remove(oldValue);
            }
        });
        
        List <Funcionario> funcionarios = Funcionario.get("");
        ObservableList <Funcionario> obsFuncionario = FXCollections.observableList(funcionarios);
        cbFuncionario.setItems(obsFuncionario);
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
            tbPane.setDisable(false);
            btConfirmar.setDisable(false);
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
        boolean ok = true;
        
        for(Tab t : tbPane.getTabs())
        {
            ObservableList<Node> componentes =  ((AnchorPane) t.getContent()).getChildren(); //”limpa” os componentes
            for (Node n : componentes) {
                if (n instanceof TableView) // textfield, textarea e htmleditor
                    if(((TableView) n).getItems().isEmpty())
                        ok = false;
            }
        }
        
        if(ok)
        {
            Treino t = new Treino(dttTreino.getValue(), dttVenciTreino.getValue(), mat, cbFuncionario.getValue());
            if(t.gravar())
            {
                t.setCod(Banco.getCon().getMaxPK("treino", "treino_cod"));
                
                for(Tab tab: tbPane.getTabs())
                {
                    ObservableList<Node> componentes = ((AnchorPane) tab.getContent()).getChildren(); //”limpa” os componentes
                    int i = 0;
                    for (Node n : componentes) {
                        if (n instanceof TableView) // textfield, textarea e htmleditor
                        {
                            ObservableList<ExercicioTreino> obExTrei  = ((TableView) n).getItems();
                            for(ExercicioTreino ex : obExTrei)
                            {
                                ex.setTreino(t);
                                ex.setTipo(treinos[i++].charAt(0));
                                if(!ex.gravar())
                                    JOptionPane.showMessageDialog(null, "Erro ao gravar exercicios: " + Banco.getCon().getMensagemErro());
                            }
                        }
                    }
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Erro ao gravar treino: " + Banco.getCon().getMensagemErro());
        }
        else
            JOptionPane.showMessageDialog(null, "Há treinos sem montar");
    }
}
