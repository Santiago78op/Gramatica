// DO NOT EDIT
// Generated by JFlex 1.9.1 http://jflex.de/
// source: src/main/jflex/lex.flex


/*
 Siemprer se debe definir el paquete
 en el que se encuentra la clase Lexer,
 esto es importante para que el analizador
 sintactico pueda encontrar la clase Lexer.
*/
package com.julian;

/*
    Importamos las clases necesarias para el
    funcionamiento del analizador lexico.
*/

// Cup es la clase que contiene los simbolos que
// se usan en el analizador sintactico.
import java_cup.runtime.*;

// Estas listas son necesarias para guardar los
// tokens y errores lexicos.
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("fallthrough")
public class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1, 1
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\4\1\5\1\6\2\0\1\7\1\0\1\10\1\11"+
    "\1\0\1\12\1\13\1\14\1\15\1\0\12\16\1\17"+
    "\1\20\1\21\1\0\1\22\2\0\1\23\1\24\1\25"+
    "\1\24\1\26\4\24\1\27\1\24\1\30\1\24\1\31"+
    "\1\32\1\33\1\24\1\34\2\24\1\35\1\36\4\24"+
    "\1\0\1\37\1\0\1\40\2\0\4\24\1\41\10\24"+
    "\1\42\3\24\1\43\1\24\1\44\6\24\1\45\1\0"+
    "\1\46\1\47\u0181\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\1\4\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\2\3\1\25"+
    "\3\0\3\14\1\26\1\27\1\30\1\31\1\11\1\0"+
    "\1\11\1\0\3\14\1\0\1\32\2\14\1\0\1\14"+
    "\1\33\1\3\1\14\1\34";

  private static int [] zzUnpackAction() {
    int [] result = new int[57];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\50\0\120\0\120\0\170\0\240\0\120\0\120"+
    "\0\120\0\120\0\310\0\360\0\120\0\120\0\u0118\0\u0140"+
    "\0\u0168\0\u0190\0\u01b8\0\u0140\0\120\0\120\0\120\0\120"+
    "\0\u01e0\0\120\0\u0208\0\120\0\u0230\0\120\0\u0258\0\u0280"+
    "\0\u02a8\0\u02d0\0\u02f8\0\u0320\0\120\0\120\0\120\0\120"+
    "\0\u0348\0\u0370\0\u0370\0\u0398\0\u03c0\0\u03e8\0\u0410\0\u0438"+
    "\0\u0140\0\u0460\0\u0488\0\u04b0\0\u04d8\0\u0140\0\u02a8\0\u0500"+
    "\0\u0140";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[57];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\2\4\1\5\2\3\1\6\1\7\1\10\1\11"+
    "\1\3\1\12\1\13\1\3\1\14\1\15\1\16\1\17"+
    "\1\3\2\20\1\21\1\22\3\20\1\23\2\20\1\24"+
    "\1\20\1\3\1\25\4\20\1\26\1\27\1\30\2\31"+
    "\2\3\1\31\1\32\31\31\1\33\10\31\52\0\1\4"+
    "\45\0\2\6\1\34\1\35\44\6\22\0\1\36\42\0"+
    "\1\37\1\14\7\0\1\40\12\0\1\40\12\0\1\41"+
    "\61\0\1\20\4\0\14\20\2\0\4\20\21\0\1\20"+
    "\4\0\7\20\1\42\4\20\2\0\4\20\21\0\1\20"+
    "\4\0\13\20\1\43\2\0\4\20\21\0\1\20\4\0"+
    "\10\20\1\44\3\20\2\0\4\20\3\0\2\31\2\0"+
    "\1\31\1\0\31\31\1\0\10\31\5\0\1\45\34\0"+
    "\1\46\1\47\1\50\5\0\1\34\63\0\1\51\43\0"+
    "\1\52\1\0\1\52\1\0\1\53\31\0\4\41\1\54"+
    "\43\41\16\0\1\20\4\0\6\20\1\55\5\20\2\0"+
    "\4\20\21\0\1\20\4\0\1\56\13\20\2\0\4\20"+
    "\21\0\1\20\4\0\3\20\1\57\10\20\2\0\4\20"+
    "\21\0\1\51\7\0\1\40\12\0\1\40\24\0\1\53"+
    "\31\0\4\41\1\60\15\41\1\34\25\41\16\0\1\20"+
    "\4\0\4\20\1\61\7\20\2\0\4\20\21\0\1\20"+
    "\4\0\5\20\1\62\6\20\2\0\4\20\21\0\1\20"+
    "\4\0\11\20\1\63\2\20\2\0\4\20\3\0\4\41"+
    "\1\64\43\41\16\0\1\20\4\0\12\20\1\65\1\20"+
    "\2\0\4\20\21\0\1\20\4\0\1\66\13\20\2\0"+
    "\4\20\3\0\4\41\1\64\15\41\1\67\25\41\16\0"+
    "\1\20\4\0\1\70\13\20\2\0\4\20\21\0\1\20"+
    "\4\0\11\20\1\71\2\20\2\0\4\20\3\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[1320];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\2\11\2\1\4\11\2\1\2\11\6\1\4\11"+
    "\1\1\1\11\1\1\1\11\1\1\1\11\3\0\3\1"+
    "\4\11\1\1\1\0\1\1\1\0\3\1\1\0\3\1"+
    "\1\0\5\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[57];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[Math.min(ZZ_BUFFERSIZE, zzMaxBufferLen())];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */

    StringBuffer string = new StringBuffer();

    public List<Token> tokens = new ArrayList<>();
    public List<String> lexicalErrors = new ArrayList<>();

    /*
        Generamos un java_cup.Symbol para guardar el tipo de token
        encontrado.
     */
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    /*
        Generamos un Symbol para el tipo de token encontrado
        junto con su valor.
     */
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }

    private void addToken(String type, String value) {
        tokens.add(new Token(type, value, yyline, yycolumn, value.length()));
    }

    private void addLexicalError(String message) {
       lexicalErrors.add("Lexical error: " + message + " at line " + yyline + ", column " + yycolumn);
     }



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
      yycolumn = 1;
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate && zzCanGrow()) {
      /* if not, and it can grow: blow it up */
      char newBuffer[] = new char[Math.min(zzBuffer.length * 2, zzMaxBufferLen())];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      if (requested == 0) {
        throw new java.io.EOFException("Scan buffer limit reached ["+zzBuffer.length+"]");
      }
      else {
        throw new java.io.IOException(
            "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
      }
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    int initBufferSize = Math.min(ZZ_BUFFERSIZE, zzMaxBufferLen());
    if (zzBuffer.length > initBufferSize) {
      zzBuffer = new char[initBufferSize];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return new java_cup.runtime.Symbol(sym.EOF); }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { addLexicalError("Illegal character: " + yytext());
            }
          // fall through
          case 29: break;
          case 2:
            { /* ignore whitespace */
            }
          // fall through
          case 30: break;
          case 3:
            { /* ignora comentarios de una linea */
            }
          // fall through
          case 31: break;
          case 4:
            { addToken("&", yytext()); return symbol(sym.INTERSECCION);
            }
          // fall through
          case 32: break;
          case 5:
            { addToken("(", yytext()); return symbol(sym.LPAREN);
            }
          // fall through
          case 33: break;
          case 6:
            { addToken(")", yytext()); return symbol(sym.RPAREN);
            }
          // fall through
          case 34: break;
          case 7:
            { addToken(",", yytext()); return symbol(sym.COMMA);
            }
          // fall through
          case 35: break;
          case 8:
            { addToken("^", yytext()); return symbol(sym.DIFERENCIA);
            }
          // fall through
          case 36: break;
          case 9:
            { addToken("NUM", yytext()); return symbol(sym.NUM, Double.parseDouble(yytext()));
            }
          // fall through
          case 37: break;
          case 10:
            { addToken(":", yytext()); return symbol(sym.COLON);
            }
          // fall through
          case 38: break;
          case 11:
            { addToken(";", yytext()); return symbol(sym.SEMICOLON);
            }
          // fall through
          case 39: break;
          case 12:
            { addToken("ID", yytext()); return symbol(sym.ID, yytext());
            }
          // fall through
          case 40: break;
          case 13:
            { addToken("U", yytext()); return symbol(sym.UNION);
            }
          // fall through
          case 41: break;
          case 14:
            { addToken("!", yytext()); return symbol(sym.COMPLEMENTO);
            }
          // fall through
          case 42: break;
          case 15:
            { addToken("{", yytext()); return symbol(sym.LBRACE);
            }
          // fall through
          case 43: break;
          case 16:
            { addToken("}", yytext()); return symbol(sym.RBRACE);
            }
          // fall through
          case 44: break;
          case 17:
            { addToken("~", yytext()); return symbol(sym.VIRGULILLA);
            }
          // fall through
          case 45: break;
          case 18:
            { string.append( yytext() );
            }
          // fall through
          case 46: break;
          case 19:
            { yybegin(YYINITIAL);
                                   return symbol(sym.STRING_LITERAL,
                                   string.toString());
            }
          // fall through
          case 47: break;
          case 20:
            { string.append('\\');
            }
          // fall through
          case 48: break;
          case 21:
            { addToken("->", yytext()); return symbol(sym.ARROW);
            }
          // fall through
          case 49: break;
          case 22:
            { string.append('\"');
            }
          // fall through
          case 50: break;
          case 23:
            { string.append('\n');
            }
          // fall through
          case 51: break;
          case 24:
            { string.append('\r');
            }
          // fall through
          case 52: break;
          case 25:
            { string.append('\t');
            }
          // fall through
          case 53: break;
          case 26:
            { addToken("CONJ", yytext()); return symbol(sym.CONJ);
            }
          // fall through
          case 54: break;
          case 27:
            { addToken("OPERA", yytext()); return symbol(sym.OPERA);
            }
          // fall through
          case 55: break;
          case 28:
            { addToken("EVALUAR", yytext()); return symbol(sym.EVALUAR);
            }
          // fall through
          case 56: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
