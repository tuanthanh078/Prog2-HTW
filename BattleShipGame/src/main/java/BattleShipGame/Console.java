package BattleShipGame;



import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Console {

    private transient BufferedReader reader = null;

    public Console(BufferedReader reader) {
        this.reader = reader;
    }

    public Console() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int readIntegerFromStdin(String message) {
        return readIntegerFromStdin(message, "Is not a valid number!");
    }
    
    public String readStringFromStdin(String message) {
        return readStringFromStdin(message, "Is not a valid string!");
    }
    
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

    private void writeMessageToStdout(String message) {
        System.out.print(message);
    }

    private void writeErrorToStdErr(Exception e, String errorMessage) {
        System.err.println(errorMessage == null ? e.getMessage() : errorMessage);
    }
}

