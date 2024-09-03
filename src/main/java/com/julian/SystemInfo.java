package com.julian;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class  SystemInfo {

    private File currentFile;

    private String reporteToken;

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
                Lexer lexer = new Lexer(new StringReader(text));
                parser parser = new parser(lexer);
                /*
                TreeNode root = (TreeNode)parser.parse().value;
                root.printTree(root, "");
                */

                // Parsear el archivo y obtener el árbol de análisis sintáctico
                TreeNode<String> root = (TreeNode<String>) parser.parse().value;
                //root.printTree("");

                // Recorrer el árbol de operaciones y evaluar cada nodo
                //root.obtenerConjuntosDefinidos();
                root.evaluateOperations();

/*
                reporteToken = Reports.reportToken(lexer.tokens);
                Reports.saveAndOpenHtmlFile(reporteToken, "/reports/Reporte_Tokens.html");

                reporteToken = Reports.reportLexErrors(lexer.lexErrors);
                Reports.saveAndOpenHtmlFile(reporteToken, "/reports/Reporte_Lex_Error.html");

                reporteToken = Reports.reportSyntaxErrors(parser.syntaxErrors);
                Reports.saveAndOpenHtmlFile(reporteToken, "/reports/Reporte_Syntax_Error.html");
*/
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while analyzing the content.");
            e.printStackTrace();
        }
    }
}
