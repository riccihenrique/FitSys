package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXMLDataController implements Initializable {

    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnConfirmar;
    
    private LocalDate data;
    private LocalDate dataVen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setMinData(LocalDate min)
    {
        dataVen = min;
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        data = null;
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void clkConfirmar(ActionEvent event) {
        data = dtPagamento.getValue();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
    
    public LocalDate getData()
    {
        return data;
    }

    @FXML
    private void clkData(Event event) {
        if(dtPagamento.getValue().compareTo(dataVen) < 0)
        {
            JOptionPane.showMessageDialog(null, "Selecione uma data maior ou igual ao vencimento");
            data = null;
        }
    }
    
}
