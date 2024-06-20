package dao;

import model.Empleado;
import model.EmpleadoDatosProf;
import org.hibernate.Session;

import java.util.List;

public interface EmpleadoDAO extends Crud <Empleado, String> {
    public boolean validarDNI(String dni);

    public boolean validarSueldo(String sueldo);
    public EmpleadoDatosProf obtenerDatosProfesionales(String dni);

}
