package view;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Aluno;
import model.Matricula;
import model.Mensalidade;
import util.MaskFieldUtil;

public class FXMLQuitarRecebimentosController implements Initializable 
{

    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtPreco;
    @FXML
    private JFXTextField txtValorPago;
    @FXML
    private JFXTextField txtTroco;
    @FXML
    private Label lbMensagem;
    @FXML
    private JFXTextField txtData;
    private Matricula mat = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        MaskFieldUtil.monetaryFieldAlternative(txtValorPago);
    }
    
    private void limparTela()
    {
        txtCPF.clear();
        txtNome.clear();
        txtData.clear();
        txtPreco.clear();
        txtTroco.clear();
        txtValorPago.clear();
    }

    @FXML
    private void btnBuscaAluno(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLBuscaMatricula.fxml"));
        Parent root = (Parent) loader.load();
        FXMLBuscaMatriculaController ba = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

        mat = ba.getMatricula();
        
        if(mat != null)
        {
            Mensalidade men = new Mensalidade();
            men.get(mat.getCod());
            txtCPF.setText(mat.getAluno().getCpf());
            txtNome.setText(mat.getAluno().getNome());
            txtData.setText(men.getMen_dtvenc().toString());
            txtPreco.setText(""+men.getMen_valor());
        }
    }

    @FXML
    private void btnQuitarDespesa(ActionEvent event)
    {
        if(!txtCPF.getText().isEmpty())
        {
            float valor;
            if(!txtValorPago.getText().isEmpty() && !txtPreco.getText().isEmpty())
                valor = Float.parseFloat(txtValorPago.getText().replace(",", ".")) - Float.parseFloat(txtPreco.getText());
            else valor = -1;
            
            if(valor >= 0)
            {
                Mensalidade men = new Mensalidade();
                men.get(mat.getCod());
                if(men.getMen_dtvenc().getMonthValue() <= LocalDate.now().getMonthValue())
                {
                    Mensalidade.geraMensalidade(mat, men.getMen_dtvenc().getDayOfMonth(), false);
                    limparTela();
                    
                    lbMensagem.setTextFill(Paint.valueOf("green"));
                    lbMensagem.setText("*Recebimento quitado com sucesso!");
                }  
                else
                {
                    lbMensagem.setTextFill(Paint.valueOf("red"));
                    lbMensagem.setText("*Recebimento já quitado!");
                }
                
            }
            else
            {
                lbMensagem.setTextFill(Paint.valueOf("red"));
                lbMensagem.setText("*Valor pago insuficiente!");
            }
        }
        else
        {
            lbMensagem.setTextFill(Paint.valueOf("red"));
            lbMensagem.setText("*Selecione uma matricula!");
        }
    }

    @FXML
    private void evtValorPago(KeyEvent event) {
        if(!txtValorPago.getText().isEmpty() && !txtPreco.getText().isEmpty())
        {
            float new_value = Float.parseFloat(txtValorPago.getText()) - Float.parseFloat(txtPreco.getText());
            if(new_value >= 0)
                txtTroco.setText(""+new_value);
            else
            txtTroco.setText("");    
        }
        else
            txtTroco.setText("");
    }
}
