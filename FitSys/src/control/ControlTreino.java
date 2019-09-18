package control;

import java.sql.SQLException;
import model.ExercicioTreino;
import model.Treino;
import util.Banco;

public abstract class ControlTreino 
{
    public static boolean apagar(int cod) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        flag = ExercicioTreino.apagar(cod) && Treino.apagar(cod);
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    
    public static boolean alterar(int cod)
    {
        return false;
    }
    
    public static boolean inserir()
    {
        return false;
    }
    
    public static Object buscar()
    {
        return null;
    }
}
