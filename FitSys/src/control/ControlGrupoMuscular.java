/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import java.sql.SQLException;
import java.util.List;
import model.GrupoMuscular;
import model.Treino;
import util.Banco;
public class ControlGrupoMuscular {
    
    private ControlGrupoMuscular cgm;
    
    private ControlGrupoMuscular(){}
    
    public ControlGrupoMuscular getInstance()
    {
        if(cgm == null)
            cgm = new ControlGrupoMuscular();
        return cgm;
    }
    public boolean inserir(int cod, String desc) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        GrupoMuscular gm = new GrupoMuscular(cod,desc);
        flag = gm.gravar();
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    public boolean apagar(int cod)
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        flag = GrupoMuscular.delete(cod);
        if(flag)
            Banco.getCon().getConnection().commit();
        else
            Banco.getCon().getConnection().rollback();
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return flag;
    }
    public boolean alterar(int cod, String desc) throws SQLException
    {
        Banco.connect();
        boolean flag;
        Banco.getCon().getConnection().setAutoCommit(false);
        GrupoMuscular gm = (GrupoMuscular) get(cod);
        flag = gm.alterar();
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
        List<GrupoMuscular> lgm = GrupoMuscular.get(filtro);
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        return lgm;
    }
    public Object buscar(int cod) throws SQLException
    {
        Banco.connect();
        Banco.getCon().getConnection().setAutoCommit(false);
        GrupoMuscular gm = new GrupoMuscular(cod,"");
        boolean flag = gm.get(cod);
        Banco.getCon().getConnection().setAutoCommit(true); 
        Banco.disconnect();
        if(!flag)
            return null;
        return gm;
    }
}
