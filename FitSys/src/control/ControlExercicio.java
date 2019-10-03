/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.Exercicio;
import model.ExercicioTreino;
import model.Funcionario;
import model.GrupoMuscular;
import model.Matricula;
import model.Treino;
import util.Banco;

/**
 *
 * @author gabriel
 */
public class ControlExercicio {
    private ControlExercicio ce;
    private ControlExercicio()
    {
    }
    public ControlExercicio getInstance()
    {
        if(ce == null)
            ce = new ControlExercicio();
        return ce;
    }
   
    public boolean apagar(int cod) throws SQLException
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
    
    public boolean alterar(int cod) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        Treino t = (Treino) buscar(cod);
        flag = t.alterar();
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    
    public boolean inserir(int cod, String desc, GrupoMuscular gm) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        Exercicio e = new Exercicio(cod,desc,gm);
        flag = e.gravar();
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    
    public Object buscar(String filtro) throws SQLException
    {
        Banco.connect();
        Banco.getCon().getConnection().setAutoCommit(false);
        List<Exercicio> le = Exercicio.get(filtro);
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return le;
    }
    public Object buscar(int cod) throws SQLException
    {
        Banco.connect();
        Banco.getCon().getConnection().setAutoCommit(false);
        Exercicio e = new Exercicio();
        e.setCod(cod);
        boolean flag = e.geti(cod);
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        if(!flag)
            return null;
        return e;
    }
}
