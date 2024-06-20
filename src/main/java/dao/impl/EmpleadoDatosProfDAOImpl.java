package dao.impl;
import dao.EmpleadoDatosProfDAO;
import model.EmpleadoDatosProf;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class EmpleadoDatosProfDAOImpl implements EmpleadoDatosProfDAO {
    private SessionFactory miFactory;


    public EmpleadoDatosProfDAOImpl(SessionFactory miFactory) {
        this.miFactory = miFactory;
    }


    @Override
    public EmpleadoDatosProf findbyID(String dni) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from EmpleadoDatosProf where dni = :dni", EmpleadoDatosProf.class)
                    .setParameter("dni", dni)
                    .uniqueResult();
        } finally {
            miSession.close();
        }
    }

    @Override
    public List<EmpleadoDatosProf> findAll() {
        return null;
    }

    @Override
    public EmpleadoDatosProf save() {
        return null;
    }

    @Override
    public void deleteByID(EmpleadoDatosProf entity) {

    }

}
