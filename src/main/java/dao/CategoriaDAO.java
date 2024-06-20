package dao;

import model.Categoria;
import org.hibernate.Session;

import java.util.List;

public interface CategoriaDAO extends Crud<Categoria, Integer> {

    public Categoria obtenerCategoriaPorNombre(String nombreCategoria);
}
