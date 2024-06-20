package org.example.gestorEmpleados;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import java.io.IOException;

public class MenuPrincipal extends Application {

    private SessionFactory miFactory;

    public void iniciarFactory() {
        try {
            //Inicializamos factory
            miFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (IllegalStateException e) {
            System.err.println("Error al iniciar la SessionFactory.");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos. Comprueba la conexión");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        iniciarFactory();

        FXMLLoader fxmlLoader = new FXMLLoader(MenuPrincipal.class.getResource("diseño-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);

        //Inyectamos miFactory en HelloController
        MenuControlador controller = fxmlLoader.getController();
        controller.setMiFactory(miFactory);

        stage.setTitle("PROYECTO GESTOR EMPLEADOS - Alejandro Mas Diego");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
