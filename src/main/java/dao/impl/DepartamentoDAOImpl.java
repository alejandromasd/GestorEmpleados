package dao.impl;
import dao.DepartamentoDAO;
import model.Departamento;
import model.Sede;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Scanner;

public class DepartamentoDAOImpl implements DepartamentoDAO {
    private SessionFactory miFactory;

    public DepartamentoDAOImpl(SessionFactory miFactory) {
        this.miFactory = miFactory;
    }


    @Override
    public Departamento obtenerDepartamentoPorNombre(String nomDepto) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Departamento where nomDepto = :nomDepto", Departamento.class)
                    .setParameter("nomDepto", nomDepto)
                    .uniqueResult();
        } finally {
            miSession.close();
        }
    }


    @Override
    public Departamento findbyID(Integer idDepto) {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Departamento where idDepto = :idDepto", Departamento.class)
                    .setParameter("idDepto", idDepto)
                    .uniqueResult();
        } finally {
            miSession.close();
        }
    }

    @Override
    public List<Departamento> findAll() {
        Session miSession = miFactory.openSession();
        try {
            return miSession.createQuery("from Departamento", Departamento.class).list();
        } finally {
            miSession.close();
        }
    }

    @Override
    public Departamento save() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el nombre del nuevo departamento:");
        String nomDepto = scanner.nextLine();

        nomDepto = nomDepto.toUpperCase();

        // Obtener todas las sedes y mostrarlas al usuario
        SedeDAOImpl sedeDAOImpl = new SedeDAOImpl(miFactory);
        List<Sede> sedes = sedeDAOImpl.findAll();
        for (int i = 0; i < sedes.size(); i++) {
            System.out.println((i + 1) + ". " + sedes.get(i).getNomSede());
        }

        int numSede;
        do {
            System.out.println("Elige una de las sedes disponibles:");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número correcto:");
                scanner.next();
            }
            numSede = scanner.nextInt();
        } while (numSede <= 0 || numSede > sedes.size());
        Sede sedeSeleccionada = sedes.get(numSede - 1);

        Session miSession = miFactory.openSession();
        try {
            Departamento miDepto = new Departamento();
            miDepto.setNomDepto(nomDepto);
            miDepto.setSedes(sedeSeleccionada);

            miSession.beginTransaction();
            miSession.save(miDepto);
            miSession.getTransaction().commit();

            System.out.println("Nuevo departamento ingresado en BBDD");
            return miDepto;
        } finally {
            miSession.close();
        }


    }

    @Override
    public void deleteByID(Departamento entity) {

    }
}

