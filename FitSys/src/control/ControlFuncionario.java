package control;

import model.Funcionario;

public class ControlFuncionario 
{
    public static Object buscar(String filtro)
    {
        return Funcionario.get(filtro);
    }
}
