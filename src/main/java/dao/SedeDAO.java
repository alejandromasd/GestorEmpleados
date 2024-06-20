package dao;

import dao.impl.SedeDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import model.Sede;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public interface SedeDAO extends Crud <Sede, Integer> {
    public Sede obtenerSedePorNombre(String nomSede);

}
