package com.julian.Gui;

import com.julian.Lexer;
import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.parser;
import com.julian.reports.Reports;
import com.julian.symbol.Arbol;
import com.julian.symbol.tablaSimbolo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Optional;

public class Gui {

    private File   currentFile;
    private Lexer  lexer;
    private parser p;
    private tablaSimbolo tabla;

    @FXML
    private TextArea textInputArea;

    @FXML
    private TextArea textOutputArea;

    /**
     * Funcion -> Abrir archivos .cs
     * @param actionEvent Evento de acción.
     */
    public void onClickButtonOpenFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Agregar filtro de extensión para archivos .cs
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CS files (*.cs)", "*.cs");
        fileChooser.getExtensionFilters().add(extFilter);

        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String dirPath = basePath + "/data";

        // Verificar si el directorio existe, si no, crearlo
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                showAlert(Alert.AlertType.INFORMATION, "Folder Created", "The specified folder did not exist and was created.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Folder Creation Failed", "The specified folder could not be created.");
                return;
            }
        } else {
            fileChooser.setInitialDirectory(dir); // Establecer la carpeta inicial
        }

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            currentFile = file;
            System.out.println("Archivo seleccionado: " + file.getAbsolutePath());
            loadFileContent(currentFile);
        }
    }

    /**
     * Funcion -> Compilar el archivo .cs
     * @param actionEvent Evento de acción.
     */
    public void onClickButtonCompilar(ActionEvent actionEvent) {
        String text = textInputArea.getText();
        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty File", "There is no content to analyze.");
            return;
        }

        try {
            // Limpiar la consola, en este caso la textOutputArea
            textOutputArea.clear();

            lexer = new Lexer(new StringReader(text));
            p = new parser(lexer);
            var resultado = p.parse();

            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
            tabla = new tablaSimbolo();

            for (var a : ast.getInstrucciones()) {
                if (a == null) continue;
                var res = a.interpretar(ast, tabla);
                System.out.println(res);
            }

            if (lexer.errors.size() > 0 || p.errors.size() > 0 || semanticErrorManager.getErrors().size() > 0) {
                String dato = ast.getConsola();
                textOutputArea.setText("\n" + dato + "\n");

                textOutputArea.appendText("\n Salida de Error: \n" + "Generando Salida de Errores...\n");
                var erroresLexicos = lexer.errors;
                var erroresSintacticos = p.errors;
                var erroresSemantico =  semanticErrorManager.getErrors();
                textOutputArea.appendText("\n Salida de Errores Lexicos: \n" + "Generando Salida de Errores...");
                for (var error : erroresLexicos) {
                    textOutputArea.appendText(error.toString() + "\n");
                }

                textOutputArea.appendText("\n Salida de Errores Sintacticos: \n" + "Generando Salida de Errores...");
                for (var error : erroresSintacticos) {
                    textOutputArea.appendText(error.toString() + "\n");
                }

                textOutputArea.appendText("\n Salida de Errores Semanticos: \n" + "Generando Salida de Errores...");
                for (var error : erroresSemantico) {
                    textOutputArea.appendText(error.toString() + "\n");
                }

                showAlert(Alert.AlertType.ERROR, "Compilation Error", "There are errors in the code.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Compilation Successful", "The code was compiled successfully.");
                textOutputArea.setText(ast.getConsola());
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while analyzing the content.");
            e.printStackTrace();
        }
    }

    /**
     * Funcion -> Generar reportes de tokens y errores.
     * @param actionEvent Evento de acción.
     */
    public void onClickButtonReportes(ActionEvent actionEvent) {
        try {

            // Verificar si el directorio existe, si no, crearlo y limpiar los archivos existentes
            clearReportesFolder();

            var tokens = lexer.tokens;
            var erroresLexicos = lexer.errors;
            var erroresSintacticos = p.errors;
            var erroresSemantico = semanticErrorManager.getErrors();
            var tablaSimbolos = tabla.getTablaActual();

            String reporteToken = null;
            String reporteTablaSimbolos = null;
            String reporteErroresLexicos = null;
            String reporteErroresSintacticos = null;
            String reporteErroresSemanticos = null;

            if (erroresLexicos.size() > 0 || tokens.size() > 0 || tablaSimbolos.size() > 0) {
                Reports reporte = new Reports(tokens, erroresLexicos, erroresSintacticos, erroresSemantico, tablaSimbolos);
                reporteToken = reporte.getTokens();
                reporteErroresLexicos = reporte.erroresLexicos();
                reporteErroresSintacticos = reporte.erroresSintacticos();
                reporteErroresSemanticos = reporte.erroresSemanticos();
                reporteTablaSimbolos = reporte.tablaSimbolos();
            }

            // Generar reporte de tokens, con el string reporte, con formato.
            createHtmlFile("Reporte_Tokens.html", reporteToken);

            // Generar reporte de tabla de simbolos, con el string reporteTablaSimbolos, con formato.
            createHtmlFile("Reporte_Tabla_Simbolos.html", reporteTablaSimbolos);

            // Generar reporte de errores, con el string reporteErrores, con formato.
            createHtmlFile("Reporte_Errores_Lexicos.html", reporteErroresLexicos);
            createHtmlFile("Reporte_Errores_Sintacticos.html", reporteErroresSintacticos);
            createHtmlFile("Reporte_Errores_Semanticos.html", reporteErroresSemanticos);

            // Ejecuta reporte de tokens y Errores, con el string reporte, con formato.
            openHtmlFile("Reporte_Tokens.html");
            openHtmlFile("Reporte_Tabla_Simbolos.html");
            openHtmlFile("Reporte_Errores_Lexicos.html");
            openHtmlFile("Reporte_Errores_Sintacticos.html");
            openHtmlFile("Reporte_Errores_Semanticos.html");

            // limpiar errores
            lexer.errors.clear();
            p.errors.clear();
            semanticErrorManager.clearErrors();
        }catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred when generating the reports or \n No code was entered in the console to report.");
            e.printStackTrace();
        }
    }

    // Crea el archivo HTML
    public static void createHtmlFile(String fileName, String htmlContent) {
        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String dirPath = basePath + "/reportes";
        String filePath = dirPath + "/" + fileName;

        // Crear el archivo HTML
        File htmlFile = new File(filePath);
        try (FileWriter writer = new FileWriter(htmlFile)) {
            writer.write(htmlContent); // Escribir contenido HTML en el archivo
            System.out.println("HTML file created: " + htmlFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred while creating the HTML file.");
            e.printStackTrace();
        }
    }

    // Abre el archivo HTML en el navegador.
    public static void openHtmlFile(String fileName) {
        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String dirPath = basePath + "/reportes";
        String filePath = dirPath + "/" + fileName;

        try {
            File htmlFile = new File(filePath);
            if (htmlFile.exists()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("HTML file opened in browser: " + htmlFile.getAbsolutePath());
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while opening the HTML file.");
            e.printStackTrace();
        }
    }

    /**
     * Funcion -> Crear un nuevo archivo .sc
     *         -> Guardar el archivo anterior si este existe.
     *         -> Guardar el nuevo contenido en un archivo nuevo.
     * @param actionEvent
     */
    public void onClickButtonNewFile(ActionEvent actionEvent) {
        if (!textInputArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Save");
            alert.setHeaderText("Unsaved changes");
            alert.setContentText("You have unsaved changes. Do you want to save them before creating a new file?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (currentFile != null) {
                    onClickButtonSaveFile(actionEvent);
                } else {
                    Stage stage = (Stage) textInputArea.getScene().getWindow();
                    onClickButtonSaveAsFile(actionEvent);
                }
            }
        }

        // Mostrar un cuadro de diálogo para ingresar el nombre del archivo
        TextInputDialog dialog = new TextInputDialog("newFile");
        dialog.setTitle("New File");
        dialog.setHeaderText("Create a New File");
        dialog.setContentText("Please enter file name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String fileName = result.get();
            // Asegurarse de que el nombre del archivo tenga la extensión .cs
            if (!fileName.endsWith(".cs")) {
                fileName += ".cs";
            }
            // Obtener la ruta del directorio base del proyecto
            String basePath = System.getProperty("user.dir");
            // Construir la ruta relativa al directorio deseado dentro del proyecto
            String dirPath = basePath + "/data";
            String filePath = dirPath + "/" + fileName;

            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs(); // Crear directorio si no existe
            }

            currentFile = new File(filePath);
            try {
                if (currentFile.createNewFile()) {
                    // Limpiar la consola, en este caso la textInputArea
                    textInputArea.clear();
                    showAlert(Alert.AlertType.INFORMATION, "File Created", "New file created: " + currentFile.getName());
                } else {
                    showAlert(Alert.AlertType.WARNING, "File Exists", "File already exists.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Funcion -> Guardar el archivo actual con un nuevo nombre.
     * @param actionEvent
     */
    public void onClickButtonSaveAsFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Agregar filtro de extensión para archivos .cs
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CS files (*.cs)", "*.cs");
        fileChooser.getExtensionFilters().add(extFilter);

        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String dirPath = basePath + "/data";

        File dir = new File(dirPath);
        if (dir.exists()) {
            fileChooser.setInitialDirectory(dir); // Establecer la carpeta inicial
        } else {
            showAlert(Alert.AlertType.WARNING, "Folder Not Found", "The specified folder does not exist.");
            return;
        }

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                String content = textInputArea.getText(); // Retrieve content from textInputArea
                writer.write(content); // Write content to the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Funcion -> Guardar el archivo actual.
     * @param actionEvent
     */
    public void onClickButtonSaveFile(ActionEvent actionEvent) {
        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                String content = textInputArea.getText(); // Retrieve content from textInputArea
                writer.write(content); // Write content to the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No file to save", "No file to save, Create file or Load file before to save.");
        }
    }

    // Despliega un campo de alerta para mostrar mensajes.
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Carga el archivo seleccionado en el área de texto.
    private void loadFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            textInputArea.setText(content.toString());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void clearReportesFolder() {
            // Obtener la ruta del directorio base del proyecto
            String basePath = System.getProperty("user.dir");
            // Construir la ruta relativa al directorio deseado dentro del proyecto
            String dirPath = basePath + "/reportes";

            // Verificar si el directorio existe, si no, crearlo
            File dir = new File(dirPath);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Folder Created: " + dirPath);
                } else {
                    System.out.println("Folder Creation Failed: " + dirPath);
                }
            } else {
                // Limpiar los archivos existentes
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (!file.isDirectory()) {
                            file.delete();
                        }
                    }
                }
            }
    }
}
