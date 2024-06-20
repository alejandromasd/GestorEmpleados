package org.example.gestorEmpleados;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dao.*;
import dao.impl.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.opencsv.CSVWriter;

public class MenuControlador {

    private SessionFactory miFactory;
    public void setMiFactory(SessionFactory miFactory) {
        this.miFactory = miFactory;
        if (this.miFactory == null) {
            deshabilitarBotones();
        }
    }
    @FXML
    private Button crearEmpleadoBoton;
    @FXML
    private Button modificarEmpleadoBoton;
    @FXML
    private Button eliminarEmpleadoBoton;
    @FXML
    private Button crearDepartamentoBoton;
    @FXML
    private Button crearSedeBoton;
    @FXML
    private Button exportarEmpleadosBoton;
    @FXML
    private Button importarEmpleadosBoton;
    @FXML
    private Button mostrarEmpleadoBoton;


    public void deshabilitarBotones() {
        crearEmpleadoBoton.setDisable(true);
        modificarEmpleadoBoton.setDisable(true);
        eliminarEmpleadoBoton.setDisable(true);
        crearDepartamentoBoton.setDisable(true);
        crearSedeBoton.setDisable(true);
        exportarEmpleadosBoton.setDisable(true);
        importarEmpleadosBoton.setDisable(true);
        mostrarEmpleadoBoton.setDisable(true);

    }

    @FXML
    protected void crearEmpleado() {

        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);


        TextInputDialog dniDialog = new TextInputDialog();
        dniDialog.setTitle("Crear un nuevo empleado");
        dniDialog.setHeaderText("Introduce el DNI del empleado:");
        Optional<String> dni = dniDialog.showAndWait();

        if (dni.isPresent()) {
            dni = Optional.of(dni.get().toUpperCase());
        }


        while (dni.isPresent() && (!empleadoDAO.validarDNI(dni.get()) || empleadoDAO.findbyID(dni.get()) != null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            if (!empleadoDAO.validarDNI(dni.get())) {
                alert.setHeaderText("DNI inválido");
                alert.setContentText("Por favor, introduce un DNI válido.");
            } else {
                alert.setHeaderText("Empleado ya existe");
                alert.setContentText("Ya existe un empleado con el DNI introducido. Por favor, introduce un DNI diferente.");
            }
            alert.showAndWait();
            dni = dniDialog.showAndWait();


            if (dni.isPresent()) {
                dni = Optional.of(dni.get().toUpperCase());
            }
        }

        if(dni.isPresent()) {

            TextInputDialog nombreDialog = new TextInputDialog();
            nombreDialog.setTitle("Crear un nuevo empleado");
            nombreDialog.setHeaderText("Introduce el nombre del empleado:");
            Optional<String> nombre = nombreDialog.showAndWait();

            if(nombre.isPresent()) {

                DepartamentoDAOImpl departamentoDAOImpl = new DepartamentoDAOImpl(miFactory);
                List<Departamento> departamentos = departamentoDAOImpl.findAll();
                //Creamos lista de departamentos para mostrarlos
                List<String> opciones = new ArrayList<>();
                for (Departamento departamento : departamentos) {
                    opciones.add(departamento.getNomDepto());
                }

                ChoiceDialog<String> dialogoDepto = new ChoiceDialog<>(opciones.get(0), opciones);
                dialogoDepto.setTitle("Crear un nuevo empleado");
                dialogoDepto.setHeaderText("Elige el departamento del empleado:");

                Optional<String> resultadoDepartamento = dialogoDepto.showAndWait();
                int idDeptoElegido = 0;
                if (resultadoDepartamento.isPresent()) {
                    String nombreDeptoElegido = resultadoDepartamento.get();
                    Departamento deptoElegido = departamentos.stream()
                            .filter(depto -> depto.getNomDepto().equals(nombreDeptoElegido))
                            .findFirst()
                            .orElse(null);
                    if (deptoElegido != null) {
                        idDeptoElegido = deptoElegido.getIdDepto();
                    }
                }
                if(resultadoDepartamento.isPresent()) {

                    CategoriaDAO categoriaDAO = new CategoriaDAOImpl(miFactory);
                    List<Categoria> categorias = categoriaDAO.findAll();
                    //Creamos una lista de nombres de categorías para mostrar en el diálogo
                    List<String> opcionCategoria = new ArrayList<>();
                    for (Categoria categoria : categorias) {
                        opcionCategoria.add(categoria.getNombreCategoria());
                    }
                    ChoiceDialog<String> dialogoCategoria = new ChoiceDialog<>(opcionCategoria.get(0), opcionCategoria);
                    dialogoCategoria.setTitle("Crear un nuevo empleado");
                    dialogoCategoria.setHeaderText("Elige la categoría del empleado:");

                    Optional<String> resultadoCategoria = dialogoCategoria.showAndWait();
                    Categoria categoriaElegida = null;
                    if (resultadoCategoria.isPresent()) {
                        String nombreCategoriaElegida = resultadoCategoria.get();

                        categoriaElegida = categorias.stream()
                                .filter(categoria -> categoria.getNombreCategoria().equals(nombreCategoriaElegida))
                                .findFirst()
                                .orElse(null);
                    }
                    if(resultadoCategoria.isPresent()) {

                        TextInputDialog sueldoDialog = new TextInputDialog();
                        sueldoDialog.setTitle("Crear un nuevo empleado");
                        sueldoDialog.setHeaderText("Introduce el sueldo bruto anual del empleado:");
                        Optional<String> sueldo = sueldoDialog.showAndWait();


                        while (sueldo.isPresent() && (!empleadoDAO.validarSueldo(sueldo.get()))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Sueldo bruto anual inválido");
                            alert.setContentText("Por favor, introduce un sueldo entre 10.000 y 100.000.");
                            alert.showAndWait();
                            sueldo = sueldoDialog.showAndWait();
                        }

                        double sueldoBrutoAnual = 0;
                        if (sueldo.isPresent()) {
                            sueldoBrutoAnual = Double.parseDouble(sueldo.get());
                        }

                        if(sueldo.isPresent()) {

                            Session miSession = miFactory.openSession();
                            try {
                                Empleado miEmpleado = new Empleado();
                                miEmpleado.setDni(dni.get());
                                miEmpleado.setNomEmp(nombre.get());
                                miEmpleado.setIdDepto(idDeptoElegido);

                                EmpleadoDatosProf datosProf = new EmpleadoDatosProf();
                                datosProf.setDni(dni.get());
                                datosProf.setCategoria(categoriaElegida);
                                datosProf.setSueldoBrutoAnual(sueldoBrutoAnual);

                                miSession.beginTransaction();
                                miSession.save(miEmpleado);
                                miSession.save(datosProf);
                                miSession.getTransaction().commit();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Información");
                                alert.setHeaderText(null);
                                alert.setContentText("Registro ingresado en BBDD");
                                alert.showAndWait();
                            } finally {
                                miSession.close();
                            }
                        }

                    }

                }

            }

        }

    }

    @FXML
    protected void modificarEmpleado() {

        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);


        List<Empleado> empleados = empleadoDAO.findAll();


        List<String> opciones = new ArrayList<>();
        for (Empleado empleado : empleados) {
            opciones.add(empleado.getDni() + " - " + empleado.getNomEmp());
        }


        ChoiceDialog<String> dialogoEmpleado = new ChoiceDialog<>(opciones.get(0), opciones);
        dialogoEmpleado.setTitle("Modificar un empleado existente");
        dialogoEmpleado.setHeaderText("Elige el empleado que quieres modificar:");

        Optional<String> resultado = dialogoEmpleado.showAndWait();
        if (resultado.isPresent()) {

            String dniEmpleadoElegido = resultado.get().split(" - ")[0];

            Empleado empleadoElegido = empleados.stream()
                    .filter(empleado -> empleado.getDni().equals(dniEmpleadoElegido))
                    .findFirst()
                    .orElse(null);

            if (empleadoElegido != null) {

                    TextInputDialog nombreDialog = new TextInputDialog();
                    nombreDialog.setTitle("Modificar un empleado existente");
                    nombreDialog.setHeaderText("Introduce el nuevo nombre del empleado:");
                    Optional<String> nuevoNombre = nombreDialog.showAndWait();

                    if(nuevoNombre.isPresent()) {

                        DepartamentoDAO departamentoDAOImpl = new DepartamentoDAOImpl(miFactory);
                        List<Departamento> departamentos = departamentoDAOImpl.findAll();


                        List<String> opcionDepartamento = new ArrayList<>();
                        for (Departamento departamento : departamentos) {
                            opcionDepartamento.add(departamento.getNomDepto());
                        }

                        ChoiceDialog<String> dialogoDepto = new ChoiceDialog<>(opcionDepartamento.get(0), opcionDepartamento);
                        dialogoDepto.setTitle("Modificar un empleado existente");
                        dialogoDepto.setHeaderText("Elige el nuevo departamento del empleado:");

                        Optional<String> resultadoDepartamento = dialogoDepto.showAndWait();
                        int idDeptoElegido = 0;
                        if (resultadoDepartamento.isPresent()) {

                            String nombreDeptoElegido = resultadoDepartamento.get();
                            Departamento deptoElegido = departamentos.stream()
                                    .filter(depto -> depto.getNomDepto().equals(nombreDeptoElegido))
                                    .findFirst()
                                    .orElse(null);

                            if (deptoElegido != null) {
                                idDeptoElegido = deptoElegido.getIdDepto();
                            }

                    }

                        if(resultadoDepartamento.isPresent()) {

                            CategoriaDAO categoriaDAO = new CategoriaDAOImpl(miFactory);
                            List<Categoria> categorias = categoriaDAO.findAll();

                            //Lista de categorías
                            List<String> opcionCategoria = new ArrayList<>();
                            for (Categoria categoria : categorias) {
                                opcionCategoria.add(categoria.getNombreCategoria());
                            }

                            ChoiceDialog<String> dialogoCategoria = new ChoiceDialog<>(opcionCategoria.get(0), opcionCategoria);
                            dialogoCategoria.setTitle("Modificar un empleado existente");
                            dialogoCategoria.setHeaderText("Elige la nueva categoría del empleado:");

                            Optional<String> resultadoCategoria = dialogoCategoria.showAndWait();
                            Categoria categoriaElegida = null;
                            if (resultadoCategoria.isPresent()) {
                                String nombreCategoriaElegida = resultadoCategoria.get();
                                categoriaElegida = categorias.stream()
                                        .filter(categoria -> categoria.getNombreCategoria().equals(nombreCategoriaElegida))
                                        .findFirst()
                                        .orElse(null);
                            }

                            if (resultadoCategoria.isPresent()) {

                                TextInputDialog sueldoDialog = new TextInputDialog();
                                sueldoDialog.setTitle("Modificar un empleado existente");
                                sueldoDialog.setHeaderText("Introduce el nuevo sueldo bruto anual del empleado:");
                                Optional<String> nuevoSueldo = sueldoDialog.showAndWait();


                                while (nuevoSueldo.isPresent() && (!empleadoDAO.validarSueldo(nuevoSueldo.get()))) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Sueldo bruto anual inválido");
                                    alert.setContentText("Por favor, introduce un sueldo entre 10.000 y 100.000.");
                                    alert.showAndWait();
                                    nuevoSueldo = sueldoDialog.showAndWait();
                                }

                                double sueldoBrutoAnual = 0;
                                if (nuevoSueldo.isPresent()) {
                                    sueldoBrutoAnual = Double.parseDouble(nuevoSueldo.get());
                                }

                                //Actualizamos los datos del empleado en la bbdd

                                Session miSession = miFactory.openSession();
                                try {
                                    empleadoElegido.setNomEmp(nuevoNombre.get());
                                    empleadoElegido.setIdDepto(idDeptoElegido);

                                    //Obtenemos los datos profesionales antiguos del empleado
                                    EmpleadoDatosProf datosProfAntiguos = empleadoDAO.obtenerDatosProfesionales(empleadoElegido.getDni());

                                    if (datosProfAntiguos != null) {
                                        datosProfAntiguos.setCategoria(categoriaElegida);
                                        datosProfAntiguos.setSueldoBrutoAnual(sueldoBrutoAnual);
                                    }
                                    miSession.beginTransaction();
                                    miSession.update(empleadoElegido);
                                    if (datosProfAntiguos != null) {
                                        miSession.update(datosProfAntiguos);
                                    }
                                    miSession.getTransaction().commit();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Registro actualizado en BBDD");
                                    alert.showAndWait();
                                } finally {
                                    miSession.close();
                                }

                            }

                }
                        }

            }

        }

    }
    @FXML
    protected void eliminarEmpleado() {
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);

        List<Empleado> empleados = empleadoDAO.findAll();

        //Mostramos empleados en un ListView
        ObservableList<String> opciones = FXCollections.observableArrayList();
        for (Empleado empleado : empleados) {
            opciones.add(empleado.getDni() + " - " + empleado.getNomEmp());
        }

        ListView<String> listView = new ListView<>(opciones);
        listView.setPrefSize(500, 300);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar un empleado existente");
        alert.setHeaderText("Elige el empleado que quieres eliminar:");
        alert.getDialogPane().setContent(listView);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            String dniEmpleadoElegido = listView.getSelectionModel().getSelectedItem().split(" - ")[0];

            //Buscamos el empleado elegido
            Empleado empleadoElegido = empleados.stream()
                    .filter(empleado -> empleado.getDni().equals(dniEmpleadoElegido))
                    .findFirst()
                    .orElse(null);

            if (empleadoElegido != null) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmación de eliminación");
                confirmacion.setHeaderText("Estás a punto de eliminar a " + empleadoElegido.getNomEmp());
                confirmacion.setContentText("¿Estás seguro de que quieres continuar?");

                Optional<ButtonType> resultadoConfirmacion = confirmacion.showAndWait();
                if (resultadoConfirmacion.isPresent() && resultadoConfirmacion.get() == ButtonType.OK) {
                    //Eliminamos el empleado
                    empleadoDAO.deleteByID(empleadoElegido);
                }

            }
        }
    }
    @FXML
    protected void mostrarEmpleados() {
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);
        DepartamentoDAO departamentoImpl = new DepartamentoDAOImpl(miFactory);

        List<Empleado> empleados = empleadoDAO.findAll();

        TableView<Empleado> tabla = new TableView<>();
        TableColumn<Empleado, String> dniCol = new TableColumn<>("DNI");
        TableColumn<Empleado, String> nombreCol = new TableColumn<>("Nombre");
        TableColumn<Empleado, String> deptoCol = new TableColumn<>("Departamento");
        TableColumn<Empleado, String> categoriaCol = new TableColumn<>("Categoría");
        TableColumn<Empleado, String> sueldoCol = new TableColumn<>("Sueldo Bruto Anual");

        dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nomEmp"));

        deptoCol.setCellValueFactory(empleado -> {
            Departamento departamento = departamentoImpl.findbyID(empleado.getValue().getIdDepto());
            return new SimpleStringProperty(departamento.getNomDepto());
        });
        categoriaCol.setCellValueFactory(empleado -> new SimpleStringProperty(empleadoDAO.obtenerDatosProfesionales(empleado.getValue().getDni()).getCategoria().getNombreCategoria()));
        sueldoCol.setCellValueFactory(empleado -> new SimpleStringProperty(String.valueOf(empleadoDAO.obtenerDatosProfesionales(empleado.getValue().getDni()).getSueldoBrutoAnual())));

        tabla.getColumns().add(dniCol);
        tabla.getColumns().add(nombreCol);
        tabla.getColumns().add(deptoCol);
        tabla.getColumns().add(categoriaCol);
        tabla.getColumns().add(sueldoCol);

        tabla.getItems().addAll(empleados);

        Stage stage = new Stage();
        stage.setTitle("Empleados");
        VBox vbox = new VBox(tabla);
        Scene scene = new Scene(vbox, 650, 400);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void crearDepartamento() {
        SedeDAOImpl sedeDAOImpl = new SedeDAOImpl(miFactory);
        DepartamentoDAOImpl departamentoDAOImpl = new DepartamentoDAOImpl(miFactory);

        TextInputDialog nomDeptoDialog = new TextInputDialog();
        nomDeptoDialog.setTitle("Crear un nuevo departamento");
        nomDeptoDialog.setHeaderText("Introduce el nombre del nuevo departamento:");
        Optional<String> nomDepto = nomDeptoDialog.showAndWait();

        if (nomDepto.isPresent()) {
            List<Sede> sedes = sedeDAOImpl.findAll();
            //Lista de sedes
            List<String> opciones = new ArrayList<>();
            for (Sede sede : sedes) {
                opciones.add(sede.getNomSede());
            }

            ChoiceDialog<String> dialogoSede = new ChoiceDialog<>(opciones.get(0), opciones);
            dialogoSede.setTitle("Crear un nuevo departamento");
            dialogoSede.setHeaderText("Elige una de las sedes disponibles:");

            Optional<String> resultado = dialogoSede.showAndWait();
            Sede sedeSeleccionada = null;

            if (resultado.isPresent()) {
                String nombreSedeElegida = resultado.get();
                sedeSeleccionada = sedes.stream()
                        .filter(sede -> sede.getNomSede().equals(nombreSedeElegida))
                        .findFirst()
                        .orElse(null);

                //insertamos el nuevo Departamento y sede
                Session miSession = miFactory.openSession();
                try {
                    Departamento miDepto = new Departamento();
                    miDepto.setNomDepto(nomDepto.get().toUpperCase());
                    miDepto.setSedes(sedeSeleccionada);
                    miSession.beginTransaction();
                    miSession.save(miDepto);
                    miSession.getTransaction().commit();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Nuevo departamento ingresado en BBDD");
                    alert.showAndWait();
                } finally {
                    miSession.close();
                }
            }

        }
    }
    @FXML
    protected void crearSede() {
        SedeDAO nuevaSede = new SedeDAOImpl(miFactory);
        nuevaSede.save();
    }

    @FXML
    protected void exportarEmpleados() {
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);
        DepartamentoDAO departamentoImpl = new DepartamentoDAOImpl(miFactory);


        List<Empleado> empleados = empleadoDAO.findAll();


        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {

                String[] cabecera = {"DNI", "Nombre", "Departamento", "Categoría", "Sueldo Bruto Anual"};
                writer.writeNext(cabecera);

                for (Empleado empleado : empleados) {
                    EmpleadoDatosProf datosProf = empleadoDAO.obtenerDatosProfesionales(empleado.getDni());
                    Departamento departamento = departamentoImpl.findbyID(empleado.getIdDepto());
                    String[] datosEmpleado = {
                            empleado.getDni(),
                            empleado.getNomEmp(),
                            departamento.getNomDepto(),
                            datosProf.getCategoria().getNombreCategoria(),
                            String.valueOf(datosProf.getSueldoBrutoAnual())
                    };
                    writer.writeNext(datosEmpleado);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportación completada");
                alert.setHeaderText(null);
                alert.setContentText("Los datos de los empleados se han exportado correctamente a " + file.getName() + ".");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void importarEmpleados() {
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(miFactory);
        DepartamentoDAO departamentoImpl = new DepartamentoDAOImpl(miFactory);
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl(miFactory);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                String[] nextLine;
                reader.readNext();

                Session miSession = miFactory.openSession();
                miSession.beginTransaction();

                while ((nextLine = reader.readNext()) != null) {
                    //Comprobamos si la linea del CSV tiene el formato correcto
                    if (nextLine.length != 5) {
                        System.err.println("Línea Incorrecta: " + Arrays.toString(nextLine));
                        continue;
                    }

                    String dni = nextLine[0];
                    Empleado empleado = empleadoDAO.findbyID(dni);

                    //Comprobamos si el empleado ya existe en la bbdd
                    if (empleado == null) {
                        empleado = new Empleado();
                        empleado.setDni(dni);
                        empleado.setNomEmp(nextLine[1]);
                        empleado.setIdDepto(departamentoImpl.obtenerDepartamentoPorNombre(nextLine[2]).getIdDepto());

                        EmpleadoDatosProf datosProf = new EmpleadoDatosProf();
                        datosProf.setDni(dni);
                        Categoria categoria = categoriaDAO.obtenerCategoriaPorNombre(nextLine[3]);
                        if (categoria != null) {
                            datosProf.setCategoria(categoria);
                        }

                        try {
                            datosProf.setSueldoBrutoAnual(Double.parseDouble(nextLine[4]));
                        } catch (NumberFormatException e) {
                            System.err.println("Sueldo bruto anual inválido: " + nextLine[4]);
                            continue;
                        }

                        miSession.save(empleado);
                        miSession.save(datosProf);
                    }
                }
                miSession.getTransaction().commit();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Importación completada");
                alert.setHeaderText(null);
                alert.setContentText("Los datos de los empleados se han importado correctamente desde " + file.getName() + ".");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void salir(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
