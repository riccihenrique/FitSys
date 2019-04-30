package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Parametrizacao;
import org.json.JSONObject;
import util.Banco;
import util.BuscaCep;
import util.BuscaCnpj;
import util.MaskFieldUtil;

public class FXMLParametrizacaoController implements Initializable {

    @FXML
    private JFXButton btGravar;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXTextField tbRazao;
    @FXML
    private JFXTextField tbCep;
    @FXML
    private JFXTextField tbCnpj;
    @FXML
    private JFXTextField tbEndereco;
    @FXML
    private JFXTextField tbCidade;
    @FXML
    private JFXTextField tbUf;
    @FXML
    private JFXColorPicker cpkCorp;
    @FXML
    private JFXColorPicker cpkCors;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgBack;
    @FXML
    private AnchorPane pnDados;
    
    private Parametrizacao p;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.cepField(tbCep);
        MaskFieldUtil.cnpjField(tbCnpj);
        
        p = new Parametrizacao();
        if(p.busca())
        {
            tbCep.setText(p.getCep());
            tbCidade.setText(p.getCidade());
            tbCnpj.setText(p.getCnpj());
            cpkCorp.setValue(Color.valueOf(p.getCor_primaria()));
            cpkCors.setValue(Color.valueOf(p.getCor_secundaria()));
            tbEndereco.setText(p.getEndereco());
            tbRazao.setText(p.getRazao());
            tbUf.setText(p.getUf());
            imgLogo.setImage(p.getLogo());
            imgBack.setImage(p.getBack());
        }
        
        if(tbCnpj.getText().isEmpty()) // Verifica qual o estado da aplicação
        {
            btAlterar.setDisable(true);
            btCancelar.setDisable(true);
        }
        else
            btGravar.setDisable(true);
        
        tbCep.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
            {
                if(!newValue)
                {
                    String json = BuscaCep.consultaCep(tbCep.getText().replace("-", ""));
                    Platform.runLater(()-> {
                        JSONObject ob = new JSONObject(json);
                        tbCidade.setText(ob.getString("localidade"));
                        tbEndereco.setText(ob.getString("logradouro"));
                        tbUf.setText(ob.getString("uf"));
                    });
                }
            }
        });
        
        tbCnpj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
            {
                if(!newValue)
                {
                    String json = BuscaCnpj.consultaCnpj(tbCnpj.getText().replace("-", "").replace(".", "").replace("/", "")), jsonCep;
                    Platform.runLater(()-> {
                        JSONObject ob = new JSONObject(json);
                        tbRazao.setText(ob.getString("nome"));
                        tbCep.setText(ob.getString("cep"));
                        tbCidade.setText(ob.getString("municipio"));
                        tbEndereco.setText(ob.getString("logradouro"));
                        tbUf.setText(ob.getString("uf"));
                    });
                }
            }
        });
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        if(isOk())
        {
            p = new Parametrizacao(tbRazao.getText(), tbCnpj.getText(), tbEndereco.getText(), 
                tbCidade.getText(), tbCep.getText(), tbUf.getText(), cpkCorp.getValue().toString(), cpkCors.getValue().toString(), 
                    imgLogo.getImage(), imgBack.getImage());
            if(!p.gravar())
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            else
            {
                JOptionPane.showMessageDialog(null, "Configurações iniciais definidas com sucesso");
                fechar();
            } 
        }
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
        
        if(isOk())
        {            
            p = new Parametrizacao(tbRazao.getText(), tbCnpj.getText(), tbEndereco.getText(), 
            tbCidade.getText(), tbCep.getText(), tbUf.getText(), cpkCorp.getValue().toString(), cpkCors.getValue().toString(), 
                imgLogo.getImage(), imgBack.getImage());
            if(!p.alterar())
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            else
            {
                JOptionPane.showMessageDialog(null, "Parametrização alterada com sucesso");
                fechar();
            }
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        if(tbCnpj.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Não é possível cancelar esta operação");
        else
            fechar();
    }

    @FXML
    private void btnProcuraLogo(ActionEvent event) {
        FileChooser arquivo = new FileChooser();
        File arq = arquivo.showOpenDialog(null);
        imgLogo.setImage(new Image(arq.toURI().toString()));
    }

    @FXML
    private void btnLimpLogo(ActionEvent event) {
        imgLogo.setImage(null);
    }

    @FXML
    private void btnProcuraBack(ActionEvent event) {
        FileChooser arquivo = new FileChooser();
        File arq = arquivo.showOpenDialog(null);
        imgBack.setImage(new Image(arq.toURI().toString()));
    }

    @FXML
    private void btnLimpaBack(ActionEvent event) {
        imgBack.setImage(null);
    }
    
    private boolean isOk()
    {
        ObservableList<Node> componentes = pnDados.getChildren();
        for (Node n : componentes) {
            if (n instanceof TextInputControl && ((TextInputControl) n).getText().isEmpty())
            {
                n.setStyle("-fx-background-color:#e61919");
                return false;
            }
        }
        return true;
    }
    
    private void fechar()
    {
        Stage stage = (Stage) btGravar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
}
