package protocolBinding;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import gameEngine.GameException;
import gameEngine.GameReceiver;
import gameEngine.StatusException;

public class StreamBindingReceiver extends Thread {
    private final DataInputStream dis;
    private final GameReceiver receiver;

    public StreamBindingReceiver(DataInputStream dis, GameReceiver receiver) {
        this.dis = dis;
        this.receiver = receiver;
    }

    public void readDice() throws IOException, StatusException {
        int random = this.dis.readInt();
        this.receiver.receiveDice(random);
    }

    public void readShoot() throws IOException, StatusException {
        int x = this.dis.readInt();
        int y = this.dis.readInt();
        try {
            this.receiver.receiveShoot(x, y);
        } catch (GameException e) {
            System.err.println("cannot execute shoot - don't inform sender - error not part of protocol: "
                    + e.getLocalizedMessage());
        }
    }
    
    public void readResult() throws IOException, StatusException {
        int[] result = new int[5];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.dis.readInt();
        }
        try {
            this.receiver.receiveShootResult(result);
        } catch (Exception e) {
            System.err.println("cannot execute result - don't inform sender - error not part of protocol: "
                    + e.getLocalizedMessage());
        }
    }

    public void run() {
        boolean again = true;
        while(again) {
            try {
                int cmd = this.dis.readInt();

                switch (cmd) {
                    case StreamBinding.DICE : this.readDice(); break;
                    case StreamBinding.SHOOT : this.readShoot(); break;
                    case StreamBinding.RESULT : this.readResult(); break;
                    default: again = false; System.err.println("unknown command code: " + cmd);
                }

            } catch (IOException e) {
                System.err.println("IOException: " + e.getLocalizedMessage());
                again = false;
            } catch (StatusException e) {
                System.err.println("Status Exception: " + e.getLocalizedMessage());
                again = false;
            }
        }
    }
}