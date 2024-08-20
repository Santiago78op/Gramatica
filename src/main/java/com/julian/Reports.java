package com.julian;

import static j2html.TagCreator.*;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reports {

    public static String reportToken(List<Token> tokens) {
        AtomicInteger cont = new AtomicInteger();
        return document(
                html(
                        head(
                                meta().withCharset("UTF-8"),
                                meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                                title("Tokens")
                        ),
                        body(
                                header(
                                        div(
                                                div(
                                                        div(
                                                                h1("Tabla de Tokens").withClass("display-4")
                                                        ).withClass("col-lg-8 mx-auto")
                                                ).withClass("row pt-5")
                                        ).withClass("container text-center text-white")
                                ),
                                main(
                                        div(
                                                div(
                                                        div(
                                                                div(
                                                                        table(
                                                                                thead(
                                                                                        tr(
                                                                                                th("#").withScope("col"),
                                                                                                th("Lexema").withScope("col"),
                                                                                                th("Tipo").withScope("col"),
                                                                                                th("Linea").withScope("col"),
                                                                                                th("Columna").withScope("col"),
                                                                                                 th("Largo").withScope("col")
                                                                                        )
                                                                                ),
                                                                                tbody(
                                                                                        each(tokens, token ->
                                                                                                tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(token.getValue()),
                                                                                                        td(token.getType()),
                                                                                                        td(String.valueOf(token.getLine())),
                                                                                                        td(String.valueOf(token.getColumn())),
                                                                                                        td(String.valueOf(token.getLength()))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ).withClass("table table-hover table-striped") // Cambia el color de la tabla a un azul oscuro
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5")
                                ),
                                footer(
                                        div(
                                                small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                        ).withClass("text-center mt-5")
                                )
                        ).withClass("bg-dark text-white") // Colorea el fondo del cuerpo y el texto
                )
        );
    }

    public static String reportLexErrors(List<LexError> lexErrors) {
        AtomicInteger cont = new AtomicInteger();
        return document(
                html(
                        head(
                                meta().withCharset("UTF-8"),
                                meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                                title("Error Lexico")
                        ),
                        body(
                                header(
                                        div(
                                                div(
                                                        div(
                                                                h1("Tabla de Errores Lexicos").withClass("display-4")
                                                        ).withClass("col-lg-8 mx-auto")
                                                ).withClass("row pt-5")
                                        ).withClass("container text-center text-white")
                                ),
                                main(
                                        div(
                                                div(
                                                        div(
                                                                div(
                                                                        table(
                                                                                thead(
                                                                                        tr(
                                                                                                th("#").withScope("col"),
                                                                                                th("Tipo").withScope("col"),
                                                                                                th("Descripcion").withScope("col"),
                                                                                                th("Linea").withScope("col"),
                                                                                                th("Columna").withScope("col")
                                                                                        )
                                                                                ),
                                                                                tbody(
                                                                                        each(lexErrors, lexError ->
                                                                                                tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getMessage()),
                                                                                                        td(String.valueOf(lexError.getLine())),
                                                                                                        td(String.valueOf(lexError.getColumn()))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ).withClass("table table-hover table-striped") // Cambia el color de la tabla a un azul oscuro
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5")
                                ),
                                footer(
                                        div(
                                                small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                        ).withClass("text-center mt-5")
                                )
                        ).withClass("bg-dark text-white") // Colorea el fondo del cuerpo y el texto
                )
        );
    }

    public static void saveAndOpenHtmlFile(String html, String flm) {
        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String filename = basePath + flm;
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(html);
            System.out.println("HTML file written to: " + filename);
            // Abrir el archivo HTML en el navegador predeterminado
            File htmlFile = new File(filename);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("El archivo HTML se ha abierto en el navegador predeterminado.");
            } else {
                System.out.println("No se pudo abrir el navegador automáticamente.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


