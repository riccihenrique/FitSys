package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import model.ExercicioTreino;
import model.Funcionario;
import model.Matricula;
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
    
    public static boolean alterar(int cod, LocalDate dtTreino, LocalDate dtVencimento, int qnt, Object mat, Object funcionario)
    {
        boolean flag = false;
        Treino t = new Treino(cod, dtTreino, dtVencimento, (Matricula) mat, (Funcionario) funcionario);
        Banco.connect();
        flag = t.alterar();
        Banco.disconnect();        
        return flag;
    }
    
    public static boolean inserir(LocalDate dtTreino, LocalDate dtVencimento, int qnt, Object mat, Object funcionario, List<ObservableList<Object>> olList) throws SQLException
    {
        boolean flag = true;
        
        Matricula m = new Matricula();
        m.get((int) mat);
        
        Treino t = new Treino(dtTreino, dtVencimento, m, (Funcionario) funcionario);       
        Banco.connect();
        Banco.getCon().getConnection().setAutoCommit(false);
        int index_t = 65; // A, B, C ...
        for(ObservableList<Object> ol : olList)
        {
            for(Object ex : ol)
            {
                ((ExercicioTreino)ex).setTreino(t);
                ((ExercicioTreino)ex).setTipo((char)index_t);
                flag = flag && ((ExercicioTreino)ex).gravar();
            }
            index_t++;
        }
        if(flag)
            flag = t.gravar();
        
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        
        Banco.getCon().getConnection().setAutoCommit(true);       
        Banco.disconnect();
        
        return false;
    }
    
    public static Object buscar(String filtro)
    {                
        return Treino.get(filtro).toArray();
    }
}
