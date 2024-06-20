package dao.impl;
import dao.CategoriaDAO;
import model.Categoria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {
    private SessionFactory miFactory;

    public CategoriaDAOImpl(SessionFactory miFactory) {
        this.miFactory = miFactory;
    }

    @Override
    public Categoria obtenerCategoriaPorNombre(String nombreCategoria) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Categoria where nombre_categoria = :nombreCategoria", Categoria.class)
                    .setParameter("nombreCategoria", nombreCategoria)
                    .uniqueResult();
        } finally {
            miSession.close();
        }
    }

    @Override
    public Categoria findbyID(Integer integer) {
        return null;
    }

    @Override
    public List<Categoria> findAll() {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Categoria", Categoria.class).list();
        } finally {
            miSession.close();
        }
    }

    @Override
    public Categoria save() {
        return null;
    }

    @Override
    public void deleteByID(Categoria entity) {

    }
}

