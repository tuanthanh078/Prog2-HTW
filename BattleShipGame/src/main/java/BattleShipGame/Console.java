package BattleShipGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class implements methods for reading integers or strings
 * from System.in (stdin) 
 */

public class Console {

    private transient BufferedReader reader = null;
    
    /**
     * Specific constructor for dependency injection.
     * 
     * @param reader    BufferReader for manipulations of inputs.
     */
    public Console(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Default constructor for class.
     */
    public Console() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message   Message for the user.
     * @return Valid integer.
     */
    public int readIntegerFromStdin(String message) {
        return readIntegerFromStdin(message, "Is not a valid number!");
    }
    
    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message   Message for the user.
     * @return Valid string.
     */
    public String readStringFromStdin(String message) {
        return readStringFromStdin(message, "Is not a valid string!");
    }
    
    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message   Message for the user.
     * @return Valid binary number.
     */
    public int readBinaryFromStdin(String message) {
        return readBinaryFromStdin(message, "Is not a valid number!");
    }
    
    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message       Message for the user.
     * @param errorMessage  Error message for the user.
     * @return Valid integer.
     */
    public int readIntegerFromStdin(String message, String errorMessage) {
        do {
            try {
                writeMessageToStdout(message);
                return Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                writeErrorToStdErr(e, errorMessage);
            }
        } while (true);
    }

    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message       Message for the user.
     * @param errorMessage  Error message for the user.
     * @return Valid string.
     */
    public String readStringFromStdin(String message, String errorMessage) {
        do {
            try {
                writeMessageToStdout(message);
                return reader.readLine().trim();
            } catch (Exception e) {
                writeErrorToStdErr(e, errorMessage);
            }
        } while (true);
    }
    
    /**
     * Returns a valid input from command line interface (CLI).
     * 
     * @param message       Message for the user.
     * @param errorMessage  Error message for the user.
     * @return Valid binary number.
     */
    public int readBinaryFromStdin(String message, String errorMessage) {
        do {
            try {
                writeMessageToStdout(message);
                int b = Integer.parseInt(reader.readLine().trim());
                if (b == 0 || b == 1) return b;
                else throw new Exception();
                
            } catch (Exception e) {
                writeErrorToStdErr(e, errorMessage);
            }
        } while (true);
    }
    
    /**
     * Prints out a message for the user
     * 
     * @param message   Message for the user.
     */
    private void writeMessageToStdout(String message) {
        System.out.print(message);
    }

    /**
     * Prints out an error message for the user
     * 
     * @param e             Exception
     * @param errorMessage  Error message for the user.
     */
    private void writeErrorToStdErr(Exception e, String errorMessage) {
        System.err.println(errorMessage == null ? e.getMessage() : errorMessage);
    } 

}