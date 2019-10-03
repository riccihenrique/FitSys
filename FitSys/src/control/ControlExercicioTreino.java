/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.List;
import model.Exercicio;
import model.ExercicioTreino;
import model.GrupoMuscular;
import model.Treino;
import util.Banco;

/**
 *
 * @author gabriel
 */
public class ControlExercicioTreino {
    private ControlExercicioTreino cet;
    public ControlExercicioTreino getInstance()
    {
        if(cet == null)
            cet = new ControlExercicioTreino();
        return cet;
    }
    Exercicio exercicio = new Exercicio();
    Treino treino = new Treino();
    
    public boolean inserir(Exercicio e, Treino t, char tipo, int ordem, int rep, int serie, int carga) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        ExercicioTreino et = new ExercicioTreino(e,t,tipo,ordem,rep,serie,carga);
        flag = et.gravar();
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    public boolean apagar(int cod) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        flag = ExercicioTreino.apagar(cod); // CÓDIGO DO TREINO 
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    public boolean alterar(Exercicio e, Treino t, char tipo, int ordem, int rep, int serie, int carga) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        ExercicioTreino et = new ExercicioTreino(e,t,tipo,ordem,rep,serie,carga);
        flag = et.alterar();
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    public Object buscar(int cod) throws SQLException
    {
        Banco.connect();
        Banco.getCon().getConnection().setAutoCommit(false);
        List<ExercicioTreino> let = ExercicioTreino.getEx(cod);
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return let;
    }
}
