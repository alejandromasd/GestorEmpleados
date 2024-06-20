package dao;

import dao.impl.SedeDAOImpl;
import model.Departamento;
import model.Sede;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public interface DepartamentoDAO extends Crud <Departamento, Integer> {
    public Departamento obtenerDepartamentoPorNombre(String nomDepto);

}
