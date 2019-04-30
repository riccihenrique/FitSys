package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Aluno 
{
    private String cpf;
    private String nome;
    private String tel;
    private String rua;
    private String cidade;
    private String cep;
    private String sexo;
    private String email;
    private LocalDate dt_nasc;

    public Aluno() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public Aluno(String cpf, String nome, String tel, String rua, String cidade, String CEP, String sexo, String email, LocalDate dt_nasc) {
        this.cpf = cpf;
        this.nome = nome;
        this.tel = tel;
        this.rua = rua;
        this.cidade = cidade;
        this.cep = CEP;
        this.sexo = sexo;
        this.email = email;
        this.dt_nasc = dt_nasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCEP() {
        return cep;
    }

    public void setCEP(String CEP) {
        this.cep = CEP;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(LocalDate dt_nasc) {
        this.dt_nasc = dt_nasc;
    }
    
    @Override
    public String toString()
    {
        return this.nome;
    }
    
    public static List<Aluno> get(String filtro)
    {
        List<Aluno> L = new ArrayList();
        String sql = "select * from aluno";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
                L.add(new  Aluno(rs.getString("alu_cpf"), rs.getString("alu_nome"), rs.getString("alu_tel"), 
                        rs.getString("alu_rua"), rs.getString("alu_cidade"), rs.getString("alu_cep"),
                        rs.getString("alu_sexo"), rs.getString("alu_email"), rs.getDate("alu_dtnasc").toLocalDate()));
        }
        catch(SQLException e){}
        return L;
    }
    
    public boolean gravar()
    {
        String sql="insert into aluno values ('#1','#2','#3','#4','#5','#6','#7', '#8', '#9')";
        
        sql=sql.replaceAll("#1", cpf.replace("-", "").replace(".", ""));
        sql=sql.replaceAll("#2", nome);
        sql=sql.replaceAll("#3", tel);
        sql=sql.replaceAll("#4", rua);
        sql=sql.replaceAll("#5", cep.replace("-", ""));
        sql=sql.replaceAll("#6", cidade);
        sql=sql.replaceAll("#7", dt_nasc.toString());
        sql=sql.replaceAll("#8", email);
        sql=sql.replaceAll("#9", sexo);
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql="update aluno set alu_nome = '#2',alu_tel = '#3', alu_rua = '#4',"
                + "alu_cep = '#5', alu_cidade = '#6', alu_dtnasc = '#7', alu_email = '#8',"
                + "alu_sexo = '#9' where alu_cpf = '" + cpf.replace("-", "").replace(".", "") + "'";
        
        sql=sql.replaceAll("#2", nome);
        sql=sql.replaceAll("#3", tel);
        sql=sql.replaceAll("#4", rua);
        sql=sql.replaceAll("#5", cep.replace("-", ""));
        sql=sql.replaceAll("#6", cidade);
        sql=sql.replaceAll("#7", dt_nasc.toString());
        sql=sql.replaceAll("#8", email);
        sql=sql.replaceAll("#9", sexo);
        
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagar(String cpf)
    {
        return Banco.getCon().manipular("delete from aluno where alu_cpf = '" + cpf.replace("-", "").replace(".", "") + "'");
    }
    
    public boolean geti(String cpf)
    {
        ResultSet rs = Banco.getCon().consultar("select * from aluno where alu_cpf = '" + cpf.replace(".", "").replace("-", "") + "'");
        try
        {
            if(rs != null && rs.next())
            {
                this.cpf = rs.getString("alu_cpf");
                this.nome = rs.getString("alu_nome");
                this.tel = rs.getString("alu_tel");
                this.rua = rs.getString("alu_rua");
                this.cidade = rs.getString("alu_cidade");
                this.cep = rs.getString("alu_cep");
                this.sexo = rs.getString("alu_sexo");
                this.email = rs.getString("alu_email");
                this.dt_nasc = rs.getDate("alu_dtnasc").toLocalDate();
                return true;
            }
        }
        catch(SQLException e){return false;}
        return false;
    }
}
