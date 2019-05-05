package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import model.Funcionario;
import model.Matricula;
import util.MaskFieldUtil;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] treinos = {"A", "B", "C", "D", "E"};
        
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
        catch(Exception e)
        {
            
        }
        
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
                        Parent root = null;
                        root = (Parent) loader.load();
                        t.setContent(root);
                        tbPane.getTabs().add(t);
                    } 
                    catch (IOException ex) 
                    {
                        
                    }
                }
                else
                {
                    tbPane.getTabs().remove(oldValue - 1);
                }
            }
        });
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
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        Stage stage = (Stage) btConfirmar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
    }
}