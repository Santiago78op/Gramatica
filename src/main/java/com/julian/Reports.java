package com.julian;

import static j2html.TagCreator.*;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reports {

    public static String reportToken(List<Token> tokens){
        AtomicInteger cont = new AtomicInteger();
        return document(
            html(
                head(
                    meta().withCharset("UTF-8"),
                    meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                    link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                    link().withRel("stylesheet").withHref("./css/bootstrap.min.css"),
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
                                                   th("#").withScope("col").withClass("col-3"),
                                                   th("Lexema").withScope("col").withClass("col-3"),
                                                   th("Tipo").withScope("col").withClass("col-3"),
                                                   th("Linea").withScope("col").withClass("col-3"),
                                                   th("Columna").withScope("col").withClass("col-3"),
                                                   th("Largo").withScope("col").withClass("col-3")
                                               )
                                           ),
                                           tbody(
                                               each(tokens, token->
                                                   tr(
                                                       th(String.valueOf(cont.getAndIncrement())).withScope("row").withClass("col-3"),
                                                       td(token.getValue()).withClass("col-3"),
                                                       td(token.getType()).withClass("col-3"),
                                                       td(String.valueOf(token.getLine())).withClass("col-3"),
                                                       td(String.valueOf(token.getColumn())).withClass("col-3"),
                                                       td(String.valueOf(token.getLength())).withClass("col-3")
                                                   )
                                               )
                                           )
                                       ).withClass("table table-fixed")
                                   ).withClass("table-responsive")
                               ) .withClass("col-lg-7 mx-auto bg-white rounded shadow")
                            ).withClass("row")
                        ).withClass("container py-5")

                    ),
                    footer(
                        small(" Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                    )
                )
            )
        );
    };

    public static void saveAndOpenHtmlFile(String html){
        // Obtener la ruta del directorio base del proyecto
        String basePath = System.getProperty("user.dir");
        // Construir la ruta relativa al directorio deseado dentro del proyecto
        String filename = basePath + "/reports";

        try (FileWriter fw = new FileWriter(filename)){

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


