package com.julian.Gui;

import com.julian.Lexer;
import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
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

    @FXML
    private TextArea textInputArea;

    @FXML
    private TextArea textOutputArea;

    @FXML
    protected void onClickButtonFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Agregar filtro de extensión para archivos .ac
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("AC files (*.ac)", "*.ac");
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

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            currentFile = file;
            System.out.println("Archivo seleccionado: " + file.getAbsolutePath());
            loadFileContent(currentFile);
        }
    };

    @FXML
    protected void onClickButtonCreateNewFile(){
        if (!textInputArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Save");
            alert.setHeaderText("Unsaved changes");
            alert.setContentText("You have unsaved changes. Do you want to save them before creating a new file?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (currentFile != null) {
                    onClickButtonSaveFile();
                } else {
                    Stage stage = (Stage) textInputArea.getScene().getWindow();
                    onClickButtonsaveAsFile();
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
            // Asegurarse de que el nombre del archivo tenga la extensión .ac
            if (!fileName.endsWith(".ac")) {
                fileName += ".ac";
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
                    System.out.println("Archivo creado: " + currentFile.getName());
                    showAlert(Alert.AlertType.INFORMATION, "File Created", "New file created: " + currentFile.getName());
                } else {
                    System.out.println("El archivo ya existe.");
                    showAlert(Alert.AlertType.WARNING, "File Exists", "File already exists.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error.");
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    };

    @FXML
    protected void onClickButtonSaveFile() {
        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                String content = textInputArea.getText(); // Retrieve content from textInputArea
                writer.write(content); // Write content to the file
                System.out.println("File saved: " + currentFile.getName());
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No file to save", "No file to save, Create file or Load file before to save.");
            System.out.println("No file to save.");
        }
    };

    @FXML
    protected void onClickButtonsaveAsFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Agregar filtro de extensión para archivos .ac
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("AC files (*.ac)", "*.ac");
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
                System.out.println("File saved: " + file.getName());
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    @FXML
    protected void onClickExecution(){
        String text = textInputArea.getText();
        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty File", "There is no content to analyze.");
            return;
        }

        try {
                lexer = new Lexer(new StringReader(text));
                p = new parser(lexer);
                var resultado = p.parse();

                var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
                var tabla = new tablaSimbolo();

                for (var a : ast.getInstrucciones()) {
                    if (a == null) continue;
                    var res = a.interpretar(ast, tabla);
                    System.out.println(res);
                }

                textOutputArea.setText(ast.getConsola());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while analyzing the content.");
            e.printStackTrace();
        }
    }

    public void openReportes(ActionEvent actionEvent) {
        try {
            
            var tokens = lexer.tokens;
            var erroresLexicos = lexer.errors;
            var erroresSintacticos = p.errors;
            var erroresSemantico =  semanticErrorManager.getErrors();

            String reporteToken = null;
            String reporteErroresLexicos = null;
            String reporteErroresSintacticos = null;
            String reporteErroresSemanticos = null;

            if (erroresLexicos.size() > 0 || tokens.size() > 0) {
                Reports reporte = new Reports(tokens, erroresLexicos, erroresSintacticos, erroresSemantico);
                reporteToken = reporte.getTokens();
                reporteErroresLexicos = reporte.erroresLexicos();
                reporteErroresSintacticos = reporte.erroresSintacticos();
                reporteErroresSemanticos = reporte.erroresSemanticos();
            }

            // Generar reporte de tokens, con el string reporte, con formato.
            createHtmlFile("Reporte_Tokens.html", reporteToken);

            // Generar reporte de errores, con el string reporteErrores, con formato.
            createHtmlFile("Reporte_Errores_Lexicos.html", reporteErroresLexicos);
            createHtmlFile("Reporte_Errores_Sintacticos.html", reporteErroresSintacticos);
            createHtmlFile("Reporte_Errores_Semanticos.html", reporteErroresSemanticos);

            // Ejecuta reporte de tokens y Errores, con el string reporte, con formato.
            openHtmlFile("Reporte_Tokens.html");
            openHtmlFile("Reporte_Errores_Lexicos.html");
            openHtmlFile("Reporte_Errores_Sintacticos.html");
            openHtmlFile("Reporte_Errores_Semanticos.html");
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

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs(); // Crear directorio si no existe
        }

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
}
