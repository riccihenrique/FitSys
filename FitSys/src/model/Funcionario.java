package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Funcionario
{
    private String cpf;
    private String nome;
    private String tel;
    private String rua;
    private String cidade;
    private String cep;
    private String email;
    private LocalDate dt_nasc;
    private String senha;
    private String cargo;
    private String uf;
    private char nivel;

    public Funcionario() {
    }

    public Funcionario(String cpf, String nome, String cargo, char nivel, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.nivel = nivel;
        this.senha = senha;
    }

    public Funcionario(String cpf, String nome, String tel, String rua, String cidade, String cep, String email, LocalDate dt_nasc, String senha, String cargo, char nivel, String uf) {
        this.cpf = cpf;
        this.nome = nome;
        this.tel = tel;
        this.rua = rua;
        this.cidade = cidade;
        this.cep = cep;
        this.email = email;
        this.uf = uf;
        this.dt_nasc = dt_nasc;
        this.senha = senha;
        this.cargo = cargo;
        this.nivel = nivel;
    }

    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public char getNivel() {
        return nivel;
    }

    public void setNivel(char nivel) {
        this.nivel = nivel;
    }

    
    
    @Override
    public String toString()
    {
        return this.nome;
    }
    
    public boolean gravar()
    {
        String sql="insert into funcionario (fun_cpf, fun_nome, fun_tel, fun_rua, fun_dtnasc, fun_cidade, fun_cep, fun_email, fun_cargo, fun_senha, fun_nivel, fun_uf) values ('#1','#2','#3','#4','#5','#6','#7','#8','#9','#!','#@', '#%')";
        
        sql=sql.replaceAll("#1", cpf.replace(".", "").replace("-", ""));
        sql=sql.replaceAll("#2", nome);
        sql=sql.replaceAll("#3", tel.replace("-", "").replace(")", "").replace("(", ""));
        sql=sql.replaceAll("#4", rua);
        sql=sql.replaceAll("#5", dt_nasc.toString());
        sql=sql.replaceAll("#6", cidade);
        sql=sql.replaceAll("#7", cep.replace("-", ""));
        sql=sql.replaceAll("#8", email);
        sql=sql.replaceAll("#9", cargo);
        sql=sql.replaceAll("#!", senha);
        sql=sql.replaceAll("#@", ""+nivel);
        sql=sql.replaceAll("#%", uf);
       
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar()
    {
        return Banco.getCon().manipular("delete from funcionario where fun_cpf='"+cpf+"'");
    }
    
    public boolean alterar()
    {
        //aaaaaaa
        String sql="update funcionario set fun_nome='#2', fun_tel='#3', fun_rua='#4', fun_dtnasc='#5', fun_cidade='#6', fun_cep='#7', fun_email='#8', fun_cargo='#9', fun_senha='#!', fun_nivel='#@', fun_uf='#%' where fun_cpf='"+cpf.replace(".", "").replace("-", "")+"'";
        
        sql=sql.replaceAll("#2", nome);
        sql=sql.replaceAll("#3", tel.replace("-", "").replace("(", "").replace(")", ""));
        sql=sql.replaceAll("#4", rua);
        sql=sql.replaceAll("#5", dt_nasc.toString());
        sql=sql.replaceAll("#6", cidade);
        sql=sql.replaceAll("#7", cep.replace("-", ""));
        sql=sql.replaceAll("#8", email);
        sql=sql.replaceAll("#9", cargo);
        sql=sql.replaceAll("#!", senha);
        sql=sql.replaceAll("#@", ""+nivel);
        sql=sql.replaceAll("#%", uf);
       
        return Banco.getCon().manipular(sql);
    }
    
    public static List<Funcionario> get(String filtro)
    {
        List<Funcionario> L = new ArrayList();
        
        String sql = "select * from funcionario";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                    L.add(new Funcionario(rs.getString("fun_cpf"), rs.getString("fun_nome"), rs.getString("fun_tel"), 
                        rs.getString("fun_rua"), rs.getString("fun_cidade"), rs.getString("fun_cep"), rs.getString("fun_email"), 
                        rs.getDate("fun_dtnasc").toLocalDate(), rs.getString("fun_senha"), rs.getString("fun_cargo"), rs.getString("fun_nivel").charAt(0), rs.getString("fun_uf")));
            }
        }
        catch(SQLException e){ }
        return L;    
    } 
    
}
