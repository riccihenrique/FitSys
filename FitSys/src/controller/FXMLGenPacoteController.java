package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Modalidade;

public class FXMLGenPacoteController implements Initializable {

    @FXML
    private ListView<Modalidade> livModalidades;
    @FXML
    private ListView<Modalidade> livSelec;

    private List<Modalidade> selectedMod;
    @FXML
    private Label lbMensagem;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        selectedMod = new ArrayList<>();
        livModalidades.setItems(FXCollections.observableList(Modalidade.get("")));
        
    }    

    @FXML
    private void btnSelecionar(ActionEvent event) 
    {
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
    }

    @FXML
    private void btnRetirar(ActionEvent event) 
    {
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
    }
    
}
