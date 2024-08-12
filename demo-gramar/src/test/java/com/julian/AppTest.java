package com.julian;

import org.junit.jupiter.api.Test;

import java.io.FileReader;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        try {
            Lexer lexer = new Lexer(new FileReader("src/main/java/com/julian/input.txt"));
            parser parser = new parser(lexer);
            parser.parse();

            System.out.println("Tokens:");
            for (Token token : lexer.tokens) {
                System.out.println(token);
            }

            if (!lexer.lexicalErrors.isEmpty()) {
                System.out.println("Lexical Errors:");
                for (String error : lexer.lexicalErrors) {
                    System.out.println(error);
                }
            }
/*
            System.out.println("Syntax Errors:");
            for (String error : parser.getErrors()) {
                System.out.println(error);
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}