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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.Exercicio;
import model.ExercicioTreino;
import model.GrupoMuscular;
import util.MaskFieldUtil;

public class FXMLTreinoController implements Initializable {

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
    @FXML
    private AnchorPane pnDados;
    @FXML
    private TableView<ExercicioTreino> tbvDados1;
    @FXML
    private JFXButton btExcluir1;
    
    List<ExercicioTreino> lista;
    int index;
    boolean flag;
    
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
        carregaCombobox();
        
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
        
        tbvDados1.setRowFactory( tv -> {
            TableRow<ExercicioTreino> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    flag = true;
                    index = tbvDados1.getSelectionModel().getSelectedIndex();
                    cbGrupoMuscular.getSelectionModel().select(0);
                    cbGrupoMuscular.getSelectionModel().select(tbvDados1.getSelectionModel().getSelectedItem().getExercicio().getGrupoMuscular());
                    cbExercicio.getSelectionModel().select(0);
                    cbExercicio.getSelectionModel().select(tbvDados1.getSelectionModel().getSelectedItem().getExercicio());
                    tbCarga.setText("" + tbvDados1.getSelectionModel().getSelectedItem().getCarga());
                    tbOrdem.setText("" + tbvDados1.getSelectionModel().getSelectedItem().getOrdem());
                    tbRepeticoes.setText("" + tbvDados1.getSelectionModel().getSelectedItem().getRepeticao());
                    tbSeries.setText("" + tbvDados1.getSelectionModel().getSelectedItem().getSerie());
                }
            });
            return row ;
        });
        
        // Define os campos como numericos
        MaskFieldUtil.numericField(tbOrdem);
        MaskFieldUtil.numericField(tbCarga);
        MaskFieldUtil.numericField(tbRepeticoes);
        MaskFieldUtil.numericField(tbSeries);
    } 

    @FXML
    private void clkExcluir(ActionEvent event) {
        if(tbvDados1.getSelectionModel().getSelectedItem() != null)
        {
            lista.clear();
            for(ExercicioTreino et: tbvDados1.getItems())
                lista.add(et);
            
            lista.remove(tbvDados1.getSelectionModel().getSelectedItem());
            carregaTabela();
        }
    }

    @FXML
    private void clkIncluir(ActionEvent event) {
        boolean ok = true;
        
        lista.clear();
        for(ExercicioTreino et: tbvDados1.getItems())
            lista.add(et);
        
        if(isOk())
        {
            if(flag)
                lista.remove(index);
            
            for(ExercicioTreino ex : lista)
                if(ex.getExercicio() == cbExercicio.getValue())
                {
                    ok = false;
                    JOptionPane.showMessageDialog(null, "Exercício já inserido");
                }
                
            if(flag)    
                lista.add(index, new ExercicioTreino(cbExercicio.getValue(), 'Q', Integer.parseInt(tbOrdem.getText()),  
                                Integer.parseInt(tbRepeticoes.getText()), Integer.parseInt(tbSeries.getText()), 
                                Integer.parseInt(tbCarga.getText())));
            else
                lista.add(new ExercicioTreino(cbExercicio.getValue(), 'Q', Integer.parseInt(tbOrdem.getText()),  
                                Integer.parseInt(tbRepeticoes.getText()), Integer.parseInt(tbSeries.getText()), 
                                Integer.parseInt(tbCarga.getText())));
                
            carregaTabela();

            ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
            for (Node n : componentes) {
                if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                    ((TextInputControl) n).setText("");
                if (n instanceof ComboBox)
                    ((ComboBox) n).getItems().clear();
            }

            carregaCombobox();
            flag = false;
        }
    }
    
    private void carregaTabela()
    {        
        ObservableList<ExercicioTreino> modelo;
        modelo = FXCollections.observableArrayList(lista);
        tbvDados1.setItems(modelo);
    }

    private void carregaCombobox() {
        List <GrupoMuscular> gruposMusculares = GrupoMuscular.get("");
        ObservableList <GrupoMuscular> obsGruposMusculares = FXCollections.observableList(gruposMusculares);
        cbGrupoMuscular.setItems(obsGruposMusculares);
    }
    
    private boolean isOk()
    {
        boolean ok = true;
        ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
        for (Node n : componentes) 
        {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                if(((TextInputControl) n).getText().isEmpty())
                {
                    n.setStyle("-fx-background-color:#e61919");
                    ok = false;
                }
                else
                    n.setStyle("-fx-background-color:#fffff");
            
            if (n instanceof ComboBox)
                if(((ComboBox) n).getSelectionModel().getSelectedItem() == null)
                {
                    n.setStyle("-fx-background-color:#e61919");
                    ok = false;
                }
                else
                    n.setStyle("-fx-background-color:#fffff");
        }
        return ok;
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        carregaTabela();

        ObservableList<Node> componentes = pnDados.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                ((TextInputControl) n).setText("");
            if (n instanceof ComboBox)
                ((ComboBox) n).getItems().clear();
        }

        carregaCombobox();
        flag = false;
    }    
}
