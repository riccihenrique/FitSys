package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Avaliacao {

    private int av_cod;
    private LocalDate dt_agendamento;
    private String hora;
    private String fun_cpf;
    private String alu_cpf;

    public Avaliacao(LocalDate dt_agendamento, String hora, String alu_cpf, String fun_cpf) {
        this.dt_agendamento = dt_agendamento;
        this.fun_cpf = fun_cpf;
        this.alu_cpf = alu_cpf;
        this.hora = hora;
    }
    
    public Avaliacao(int Cod, LocalDate dt_agendamento, String hora, String alu_cpf, String fun_cpf) {
        this.dt_agendamento = dt_agendamento;
        this.fun_cpf = fun_cpf;
        this.alu_cpf = alu_cpf;
        this.hora = hora;
        this.av_cod = Cod;
    }

    public Avaliacao() {
    }

     public String getFun_cpf() {
        return fun_cpf;
    }

    public void setFun_cpf(String fun_cpf) {
        this.fun_cpf = fun_cpf;
    }
    public int getAv_cod() {
        return av_cod;
    }

    public LocalDate getDt_agendamento() {
        return dt_agendamento;
    }

    public void setDt_agendamento(LocalDate dt_agendamento) {
        this.dt_agendamento = dt_agendamento;
    }

    public String getAlu_cpf() {
        return alu_cpf;
    }

    public void setAlu_cpf(String alu_cpf) {
        this.alu_cpf = alu_cpf;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }
    
    public static List<Avaliacao> get(String filtro) {
        List<Avaliacao> L = new ArrayList();
        String sql = "select * from avaliacao";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                L.add(new Avaliacao(rs.getInt("av_cod"), rs.getDate("av_dtagendamento").toLocalDate(), rs.getString("av_hora"), rs.getString("alu_cpf"), rs.getString("fun_cpf")));
            }
        } catch (SQLException e) {
        }
        return L;
    }
    
    public static List<String> getHrOcupados(LocalDate dt)
    {
        List<String> L = new ArrayList();
        String sql = "select av_hora from avaliacao where av_dtagendamento = '#1' and av_peso is null";
        sql = sql.replaceAll("#1", dt.toString());
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
                L.add(rs.getString("av_hora"));
        }
        catch(SQLException e){}
        
        return L;
    }
    
    public static List<Avaliacao> getAVS(LocalDate dt)
    {
        List<Avaliacao> L = new ArrayList();
        String sql = "select av_cod, av_dtagendamento, av_hora, alu_cpf, fun_cpf from avaliacao where av_dtagendamento = '#1'";
        sql = sql.replaceAll("#1", dt.toString());
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                L.add(new Avaliacao(rs.getInt("av_cod"), rs.getDate("av_dtagendamento").toLocalDate(), rs.getString("av_hora"), rs.getString("alu_cpf"), rs.getString("fun_cpf")));
                //System.out.println(rs.getString("av_hora"));
            }
                
        }
        catch(SQLException e){}
        
        return L;
    }
    
     public static List<Avaliacao> getAVS(int cod)
    {
        List<Avaliacao> L = new ArrayList();
        String sql = "select av_cod, av_dtagendamento, av_hora, alu_cpf, fun_cpf from avaliacao where av_cod = '#1'";
        sql = sql.replaceAll("#1", cod+"");
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                L.add(new Avaliacao(rs.getInt("av_cod"), rs.getDate("av_dtagendamento").toLocalDate(), rs.getString("av_hora"), rs.getString("alu_cpf"), rs.getString("fun_cpf")));
                //System.out.println(rs.getString("av_hora"));
            }
                
        }
        catch(SQLException e){}
        
        return L;
    }
    

    public boolean gravar() {
        String sql = "insert into avaliacao(alu_cpf,av_dtagendamento,av_hora,fun_cpf) values('#1', '#2', '#3','#4')";
        sql = sql.replaceAll("#1", alu_cpf);
        sql = sql.replaceAll("#2", dt_agendamento.toString());
        sql = sql.replaceAll("#3", hora);
        sql = sql.replaceAll("#4", fun_cpf);
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar()
    {
        String sql = "delete from avaliacao where avaliacao.av_cod="+av_cod;
        return Banco.getCon().manipular(sql);    
    }
    
     public boolean alterar()
    {
        String sql = "update avaliacao set av_dtagendamento='#2',av_hora='#3',fun_cpf='#4' where avaliacao.av_cod="+av_cod;
       
        sql = sql.replaceAll("#2", dt_agendamento.toString());
        sql = sql.replaceAll("#3", hora);
        sql = sql.replaceAll("#4", fun_cpf);
        
        return Banco.getCon().manipular(sql);
    }

}
