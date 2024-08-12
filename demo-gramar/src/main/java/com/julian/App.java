package com.julian;

import java.io.FileReader;
import java.io.SequenceInputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Lexer lexer = new Lexer(new FileReader("src/main/java/com/julian/input.txt"));
            parser parser = new parser(lexer);
            parser.parse();

            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
