package model;

import java.sql.Date;

public class Funcionario
{
    private String cpf;
    private String nome;
    private String tel;
    private String rua;
    private String cidade;
    private String cep;
    private char sexo;
    private String email;
    private Date dt_nasc;
    private String senha;
    private char nivel;

    public Funcionario() {
    }

    public Funcionario(String cpf, String nome, String tel, String rua, String cidade, String cep, char sexo, String email, Date dt_nasc, String senha, char nivel) {
        this.cpf = cpf;
        this.nome = nome;
        this.tel = tel;
        this.rua = rua;
        this.cidade = cidade;
        this.cep = cep;
        this.sexo = sexo;
        this.email = email;
        this.dt_nasc = dt_nasc;
        this.senha = senha;
        this.nivel = nivel;
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(Date dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}
