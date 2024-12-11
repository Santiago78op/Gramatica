package com.julian.reports;

import com.julian.exception.Errores;
import com.julian.token.Token;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import static j2html.TagCreator.*;

public class Reports {

    private LinkedList<Token> tokens;
    private LinkedList<Errores> erroresLexicos;
    private LinkedList<Errores> erroresSintacticos;
    private LinkedList<Errores> erroresSemanticos;

    public Reports(LinkedList<Token> tokens, LinkedList<Errores> erroresLexicos, LinkedList<Errores> erroresSintacticos) {
        this.tokens = tokens;
        this.erroresLexicos = erroresLexicos;
        this.erroresSintacticos = erroresSintacticos;
        this.erroresSemanticos = erroresSemanticos;
    }

    public String getTokens() {
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
                                                                                tbody( // Crea una fila por cada token
                                                                                        each(this.tokens, token -> tr(
                                                                                                th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                td(token.getValue()),
                                                                                                td(token.getType()),
                                                                                                td(Integer.toString(token.getLine())),
                                                                                                td(Integer.toString(token.getColumn())),
                                                                                                td(Integer.toString(token.getLength()))
                                                                                                )
                                                                                        )
                                                                                ) // Cambia el color de la tabla a un azul oscuro
                                                                        ).withClass("table table-hover table-striped")
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5")
                                ),
                                footer(
                                        div(
                                                small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                        ).withClass("text-center mt-5")
                                )// Colorea el fondo del cuerpo y el texto
                        ).withClass("bg-dark text-white")
                )
        );
    }

    public String erroresLexicos() {
        AtomicInteger cont = new AtomicInteger();
        return document(
                html(
                        head(
                                meta().withCharset("UTF-8"),
                                meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                                title("Errores Lexicos")
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
                                                                                        each(this.erroresLexicos, lexError -> {
                                                                                            if ("Lexico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Sintactico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Semantico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            }
                                                                                            return null; // Omitir errores que no sean de tipo "Error"
                                                                                        })
                                                                                ) // Cambia el color de la tabla a un azul oscuro
                                                                        ).withClass("table table-hover table-striped")
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5"),
                                        footer(
                                                div(
                                                        small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                                ).withClass("text-center mt-5")
                                        )
                                ) // Colorea el fondo del cuerpo y el texto
                        ).withClass("bg-dark text-white")
                )
        );
    }

    public String erroresSintacticos() {
        AtomicInteger cont = new AtomicInteger();
        return document(
                html(
                        head(
                                meta().withCharset("UTF-8"),
                                meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                                title("Errores Sintacticos")
                        ),
                        body(
                                header(
                                        div(
                                                div(
                                                        div(
                                                                h1("Tabla de Errores Sintacticos").withClass("display-4")
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
                                                                                        each(this.erroresSintacticos, lexError -> {
                                                                                            if ("Lexico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Sintactico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Semantico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            }
                                                                                            return null; // Omitir errores que no sean de tipo "Error"
                                                                                        })
                                                                                ) // Cambia el color de la tabla a un azul oscuro
                                                                        ).withClass("table table-hover table-striped")
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5"),
                                        footer(
                                                div(
                                                        small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                                ).withClass("text-center mt-5")
                                        )
                                ) // Colorea el fondo del cuerpo y el texto
                        ).withClass("bg-dark text-white")
                )
        );
    }

    public String erroresSemanticos() {
        AtomicInteger cont = new AtomicInteger();
        return document(
                html(
                        head(
                                meta().withCharset("UTF-8"),
                                meta().withName("viewport").withContent("width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"),
                                title("Errores Semanticos")
                        ),
                        body(
                                header(
                                        div(
                                                div(
                                                        div(
                                                                h1("Tabla de Errores Semanticos").withClass("display-4")
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
                                                                                        each(this.erroresSemanticos, lexError -> {
                                                                                            if ("Lexico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Sintactico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            } else if ("Semantico".equals(lexError.getTipo())) {
                                                                                                return tr(
                                                                                                        th(String.valueOf(cont.getAndIncrement())).withScope("row"),
                                                                                                        td(lexError.getTipo()),
                                                                                                        td(lexError.getDesc()),
                                                                                                        td(Integer.toString(lexError.getLinea())),
                                                                                                        td(Integer.toString(lexError.getColumna()))
                                                                                                );
                                                                                            }
                                                                                            return null; // Omitir errores que no sean de tipo "Error"
                                                                                        })
                                                                                ) // Cambia el color de la tabla a un azul oscuro
                                                                        ).withClass("table table-hover table-striped")
                                                                ).withClass("table-responsive")
                                                        ).withClass("col-lg-10 mx-auto bg-white rounded shadow")
                                                ).withClass("row")
                                        ).withClass("container py-5"),
                                        footer(
                                                div(
                                                        small("Copyright © 2024 Create by Santiago Barrera. All Rights Reserved.")
                                                ).withClass("text-center mt-5")
                                        )
                                ) // Colorea el fondo del cuerpo y el texto
                        ).withClass("bg-dark text-white")
                )
        );
    }
}
