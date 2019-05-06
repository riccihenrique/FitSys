package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Exercicio;
import model.ExercicioTreino;
import model.GrupoMuscular;
import util.MaskFieldUtil;

public class FXMLTreinoController implements Initializable {

    @FXML
    private TableView<ExercicioTreino> tbvDados;
    @FXML
    private TableColumn<ExercicioTreino, String> colOrdem;
    @FXML
    private TableColumn<ExercicioTreino, String> colExercicio;
    @FXML
    private TableColumn<ExercicioTreino, String> colSerie;
    @FXML
    private TableColumn<ExercicioTreino, String> colRepeticao;
    @FXML
    private TableColumn<ExercicioTreino, String> colCarga;
    @FXML
    private JFXComboBox<GrupoMuscular> cbGrupoMuscular;
    @FXML
    private JFXComboBox<Exercicio> cbExercicio;
    @FXML
    private JFXTextField tbOrdem;
    @FXML
    private JFXTextField tbSeries;
    @FXML
    private JFXTextField tbRepeticoes;
    @FXML
    private JFXTextField tbCarga;
    @FXML
    private JFXButton btIncluir;
    @FXML
    private JFXButton btExcluir;
    
    List<ExercicioTreino> lista;
    @FXML
    private AnchorPane pnDados;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lista = new ArrayList<>();
        
        // Configurar colunas das tabelas
        colCarga.setCellValueFactory(new PropertyValueFactory("carga"));
        colExercicio.setCellValueFactory(new PropertyValueFactory("exercicio"));
        colOrdem.setCellValueFactory(new PropertyValueFactory("ordem"));
        colRepeticao.setCellValueFactory(new PropertyValueFactory("repeticao"));
        colSerie.setCellValueFactory(new PropertyValueFactory("serie"));
        
        // Carrega combobox com grupos musculares
        List <GrupoMuscular> gruposMusculares = GrupoMuscular.get("");
        ObservableList <GrupoMuscular> obsGruposMusculares = FXCollections.observableList(gruposMusculares);
        cbGrupoMuscular.setItems(obsGruposMusculares);
        
        cbGrupoMuscular.valueProperty().addListener(new ChangeListener<GrupoMuscular>() {
            @Override
            public void changed(ObservableValue<? extends GrupoMuscular> observable, 
                    GrupoMuscular oldValue, GrupoMuscular newValue) {
                // Muda os exercicios dependendo dos grupos musculares
                List <Exercicio> dadosCategoria = Exercicio.get("gru_Cod = " + newValue.getCod());
                ObservableList <Exercicio> obsListCategoria = FXCollections.observableList(dadosCategoria);
                cbExercicio.setItems(obsListCategoria);
            }
        });
        
        // Define os campos como numericos
        MaskFieldUtil.numericField(tbOrdem);
        MaskFieldUtil.numericField(tbCarga);
        MaskFieldUtil.numericField(tbRepeticoes);
        MaskFieldUtil.numericField(tbSeries);
    } 

    @FXML
    private void clkExcluir(ActionEvent event) {
    }

    @FXML
    private void clkIncluir(ActionEvent event) {
        try
        {
            lista.add(new ExercicioTreino(cbExercicio.getValue(), 'a', Integer.parseInt(tbOrdem.getText()),  
                Integer.parseInt(tbRepeticoes.getText()), Integer.parseInt(tbSeries.getText()), 
                Integer.parseInt(tbCarga.getText())));
        }
        catch(Exception e)
        {
            
        }
                
        ObservableList<ExercicioTreino> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tbvDados.setItems(modelo);
        
        ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                ((TextInputControl) n).setText("");
            if (n instanceof ComboBox)
                ((ComboBox) n).getItems().clear();
        }
    }
}
