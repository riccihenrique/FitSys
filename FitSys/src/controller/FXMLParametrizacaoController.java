package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Parametrizacao;
import util.Banco;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.cepField(tbCep);
        MaskFieldUtil.cnpjField(tbCnpj);
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        if(isOk())
        {
            Parametrizacao p = new Parametrizacao(tbRazao.getText(), tbCnpj.getText(), tbEndereco.getText(), 
                tbCidade.getText(), tbCep.getText(), tbUf.getText(), cpkCorp.getValue().toString(), cpkCors.getValue().toString());
            if(!p.gravar(imgLogo.getImage(), imgBack.getImage()))
                JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            else
                JOptionPane.showMessageDialog(null, "Configurações iniciais definidas com sucesso");
        }
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
        Parametrizacao p = new Parametrizacao(tbRazao.getText(), tbCnpj.getText(), tbEndereco.getText(), 
                tbCidade.getText(), tbCep.getText(), tbUf.getText(), cpkCorp.getValue().toString(), cpkCors.getValue().toString());
        if(!p.alterar(imgLogo.getImage(), imgBack.getImage()))
            JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
        else
            JOptionPane.showMessageDialog(null, "Configurações iniciais alteradas com sucesso");
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        if(tbCnpj.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Não é possível cancelar esta operação");
        else
        {
            Stage stage = (Stage) btGravar.getScene().getWindow(); //Obtendo a janela atual
            stage.close(); //Fechando o Stage
        }
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
            if (n instanceof TextInputControl &&  ((TextInputControl) n).getText().isEmpty())
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("O campo " + n.getId().replace("tb", "") + " não foi preenchido");
                if(!n.getId().equals("tbCodigo"))
                {
                    a.show();
                    return false;
                }
            }
            if (n instanceof ComboBox && ((ComboBox) n).getSelectionModel().getSelectedItem()== null)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("O campo " + n.getId().replace("cb", "") + " não foi selecionado");
                a.show();
                return false;
            }
        }
        return true;
    }
}
