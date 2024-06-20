package dao.impl;
import dao.EmpleadoDAO;
import model.Empleado;
import model.EmpleadoDatosProf;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private SessionFactory miFactory;

    public EmpleadoDAOImpl(SessionFactory miFactory) {
        this.miFactory = miFactory;
    }

    @Override
    public boolean validarDNI(String dni) {
        return dni.matches("\\d{8}[A-HJ-NP-TV-Z]");
    }


    @Override
    public boolean validarSueldo(String sueldo) {
        try {
            double sueldoBrutoAnual = Double.parseDouble(sueldo);
            return sueldoBrutoAnual >= 10000 && sueldoBrutoAnual <= 100000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public EmpleadoDatosProf obtenerDatosProfesionales(String dni) {
        Session miSession = miFactory.openSession();
        EmpleadoDatosProf datosProf = null;
        try {
            miSession.beginTransaction();
            datosProf = miSession.get(EmpleadoDatosProf.class, dni);
            miSession.getTransaction().commit();
        } finally {
            miSession.close();
        }
        return datosProf;
    }


    @Override
    public Empleado findbyID(String id) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.get(Empleado.class, id);
        } finally {
            miSession.close();
        }
    }

    @Override
    public List<Empleado> findAll() {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Empleado", Empleado.class).list();
        } finally {
            miSession.close();
        }
    }

    @Override
    public Empleado save() {
        return null;
    }

    @Override
    public void deleteByID(Empleado empleado) {
        Session miSession = miFactory.openSession();
        try {
            EmpleadoDatosProf datosProf = obtenerDatosProfesionales(empleado.getDni());
            miSession.beginTransaction();
            if (datosProf != null) {
                miSession.delete(datosProf);
            }
            miSession.delete(empleado);
            miSession.getTransaction().commit();
        } finally {
            miSession.close();
        }

    }
}
