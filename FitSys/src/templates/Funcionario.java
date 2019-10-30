package templates;

import model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Funcionario extends TempPessoa implements Observer
{
    private String senha;
    private String cargo;
    private String uf;
    private char nivel;
    private static Funcionario fun;

    public Funcionario() {
        super();
    }

    private Funcionario(String cpf, String nome, String cargo, char nivel, String senha) {
        super();
        this.setCpf(cpf);
        this.setNome(nome);
        this.cargo = cargo;
        this.nivel = nivel;
        this.senha = senha;
    }

    public Funcionario(String cpf, String nome, String tel, String rua, String cidade, String cep, String email, LocalDate dt_nasc, String senha, String cargo, char nivel, String uf) {
        super(cpf,nome,tel,rua,cidade,cep,"indefinido pelos programadores",email,dt_nasc);
        this.uf = uf;
        this.senha = senha;
        this.cargo = cargo;
        this.nivel = nivel;
    }
    
    public static Funcionario login(String cpf, String nome, String cargo, char nivel, String senha)
    {
        if(fun == null)
            fun = new Funcionario(cpf, nome, cargo, nivel, senha);
        
        return fun;
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
    /*
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
    
    public boolean getFuncionario(String cpf)
    {
        ResultSet rs = Banco.getCon().consultar("select * from funcionario where fun_cpf = '" + cpf.replace(".", "").replace("-", "") + "'");
        try
        {
            if(rs != null && rs.next())
            {
                this.cpf = rs.getString("fun_cpf");
                this.nome = rs.getString("fun_nome");
                this.tel = rs.getString("fun_tel");
                this.rua = rs.getString("fun_rua");
                this.cidade = rs.getString("fun_cidade");
                this.cep = rs.getString("fun_cep");
                this.email = rs.getString("fun_email");
                this.dt_nasc = rs.getDate("fun_dtnasc").toLocalDate();
                this.senha = rs.getString("fun_email");
                this.cargo = rs.getString("fun_cargo");
                this.uf = rs.getString("fun_uf");
                this.cargo = rs.getString("fun_cargo");
                return true;
            }
        }
        catch(SQLException e){return false;}
        return false;
    }    */

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
