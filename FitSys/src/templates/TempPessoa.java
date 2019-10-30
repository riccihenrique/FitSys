/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates;

import java.time.LocalDate;
import util.Banco;

/**
 *
 * @author Aluno
 */
public abstract class TempPessoa {
    protected String cpf;
    protected String nome;
    protected String tel;
    protected String rua;
    protected String cidade;
    protected String cep;
    protected String sexo;
    protected String email;
    protected LocalDate dt_nasc;

    public TempPessoa() {
    }

    public TempPessoa(String cpf, String nome, String tel, String rua, String cidade, String cep, String sexo, String email, LocalDate dt_nasc) {
        this.cpf = cpf;
        this.nome = nome;
        this.tel = tel;
        this.rua = rua;
        this.cidade = cidade;
        this.cep = cep;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
    
    public final boolean gravar()
    {
        String sql = "";
        Banco.connect();
        if(this instanceof Aluno)
        {
            sql="insert into aluno values ('#1','#2','#3','#4','#5','#6','#7', '#8', '#9')";
            sql=sql.replaceAll("#9", sexo);
        }
        else if(this instanceof Funcionario)
        {
            Funcionario f = (Funcionario)this;
            sql="insert into funcionario (fun_cpf, fun_nome, fun_tel, fun_rua, fun_dtnasc, fun_cidade, fun_cep, fun_email, fun_cargo, fun_senha, fun_nivel, fun_uf) "
                    + "values ('#1','#2','#3','#4','#5','#6','#7','#8','#9','#!','#@', '#%')";
            sql=sql.replaceAll("#9", f.getCargo());
            sql=sql.replaceAll("#!", f.getSenha());
            sql=sql.replaceAll("#@", ""+f.getNivel());
            sql=sql.replaceAll("#%", f.getUf());
        }
        sql=sql.replaceAll("#1", cpf.replace("-", "").replace(".", ""));
        sql=sql.replaceAll("#2", nome);
        sql=sql.replaceAll("#3", tel);
        sql=sql.replaceAll("#4", rua);
        sql=sql.replaceAll("#5", cep.replace("-", ""));
        sql=sql.replaceAll("#6", cidade);
        sql=sql.replaceAll("#7", dt_nasc.toString());
        sql=sql.replaceAll("#8", email);
        
        boolean res = Banco.getCon().manipular(sql);
        Banco.disconnect();
        return res;
    }
    public final boolean delete()
    {
        String sql = "";
        return Banco.getCon().manipular(sql);
    }
}
