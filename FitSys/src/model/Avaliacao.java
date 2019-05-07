
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
    private String fun_cpf;
    private String alu_cpf;

    public Avaliacao(LocalDate dt_agendamento, String fun_cpf, String alu_cpf) {
        this.dt_agendamento = dt_agendamento;
        this.fun_cpf = fun_cpf;
        this.alu_cpf = alu_cpf;
    }

    public Avaliacao(int av_cod, LocalDate dt_agendamento, String fun_cpf, String alu_cpf) {
        this.av_cod = av_cod;
        this.dt_agendamento = dt_agendamento;
        this.fun_cpf = fun_cpf;
        this.alu_cpf = alu_cpf;
    }

    public Avaliacao() {
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

    public String getFun_cpf() {
        return fun_cpf;
    }

    public void setFun_cpf(String fun_cpf) {
        this.fun_cpf = fun_cpf;
    }

    public String getAlu_cpf() {
        return alu_cpf;
    }

    public void setAlu_cpf(String alu_cpf) {
        this.alu_cpf = alu_cpf;
    }
    
    public static List<Avaliacao> get(String filtro)
    {
        List<Avaliacao> L = new ArrayList();
        String sql = "select * from avaliacao";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
                L.add(new  Avaliacao(rs.getInt("av_cod"),rs.getDate("av_dtagendamento").toLocalDate(), rs.getString("fun_cpf"), rs.getString("alu_cpf")));
        }
        catch(SQLException e){}
        return L;
    }
    
    public boolean gravar()
    {
        String sql="insert into avaliacao(alu_cpf,fun_cpf,av_dtagendamento) values('#1', '#2', '#3')";
        sql = sql.replaceAll("#1", alu_cpf);
        sql = sql.replaceAll("#2", fun_cpf);
        sql = sql.replaceAll("#3", dt_agendamento.toString());
        
        return Banco.getCon().manipular(sql);
    }
    
}
