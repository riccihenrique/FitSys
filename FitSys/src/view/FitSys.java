package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import util.Banco;

public class FitSys extends Application {
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLGerTreino.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)
    {
        if(!Banco.conectar())
        {
            JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            if(JOptionPane.showConfirmDialog(null, "Deseja criar uma base de dados?") == JOptionPane.YES_OPTION)
            {
                if(!Banco.criarBD("fitsys"))
                {
                    JOptionPane.showMessageDialog(null, "Erro ao criar banco: " + Banco.getCon().getMensagemErro());
                    System.exit(-1);
                }
                else
                {
                    if(!Banco.conectar())
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco");                    
                        System.exit(-1);
                    }
                    else                        
                        if(!Banco.criarTabelas("script/script.sql", "fitsys"))
                        {
                            JOptionPane.showMessageDialog(null, "Erro ao criar tabelas: " + Banco.getCon().getMensagemErro());
                            System.exit(-1);
                        }
                }
            }    
            else
                System.exit(-1);
        }
        launch(args);
    }
}
