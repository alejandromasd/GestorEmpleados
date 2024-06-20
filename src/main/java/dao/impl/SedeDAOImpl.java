package dao.impl;

import dao.SedeDAO;
import model.Sede;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class SedeDAOImpl implements SedeDAO {
    private SessionFactory miFactory;

    public SedeDAOImpl(SessionFactory miFactory) {
        this.miFactory = miFactory;
    }

    @Override
    public Sede obtenerSedePorNombre(String nomSede) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Sede where nomSede = :nomSede", Sede.class)
                    .setParameter("nomSede", nomSede)
                    .uniqueResult();
        } finally {
            miSession.close();
        }
    }

    @Override
    public Sede findbyID(Integer integer) {
        return null;
    }

    @Override
    public List<Sede> findAll() {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Sede", Sede.class).list();
        } finally {
            miSession.close();
        }
    }

    @Override
    @FXML
    public Sede save() {
        SedeDAOImpl sedeDAOImpl = new SedeDAOImpl(miFactory);
        Sede miSede = new Sede();

        boolean sedeCreada = false;

        while (!sedeCreada) {
            TextInputDialog nomSedeDialog = new TextInputDialog();
            nomSedeDialog.setTitle("Crear una nueva sede");
            nomSedeDialog.setHeaderText("Introduce el nombre de la nueva sede:");
            Optional<String> nomSede = nomSedeDialog.showAndWait();

            if (nomSede.isPresent()) {
                String sedeMayus = nomSede.get().toUpperCase();

                Sede sedeExistente = sedeDAOImpl.obtenerSedePorNombre(sedeMayus);

                if (sedeExistente != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("La sede ya existe en la base de datos. Por favor, introduce un nombre diferente.");
                    alert.showAndWait();
                } else {
                    Session miSession = miFactory.openSession();
                    try {

                        miSede.setNomSede(sedeMayus);

                        miSession.beginTransaction();
                        miSession.save(miSede);
                        miSession.getTransaction().commit();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Información");
                        alert.setHeaderText(null);
                        alert.setContentText("Nueva sede ingresada en BBDD");
                        alert.showAndWait();

                        sedeCreada = true;

                    } finally {
                        miSession.close();
                    }

                }

            } else {
                break;
            }
        }
        return miSede;
    }
    @Override
    public void deleteByID(Sede entity) {
    }
}

