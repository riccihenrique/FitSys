package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import util.Banco;

public class ControlLogin 
{ 
    public static boolean checkExistLogin()
    {
        try{
            return Banco.getCon().consultar("select * from funcionario").next();
        }
        catch(SQLException e){}
        return false;
    }
    public static ResultSet consultaLogin(String nome)
    {
        String sql = "select * from funcionario where fun_cpf = '#1'";
        sql = sql.replaceAll("#1", nome.replace(".", "").replace("-", ""));
        return Banco.getCon().consultar(sql);
    }
}
