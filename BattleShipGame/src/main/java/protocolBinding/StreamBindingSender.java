package protocolBinding;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import gameEngine.GameException;
import gameEngine.GameSender;
import gameEngine.StatusException;

/**
 * Encodes methods and parameter and sends via OutputStream
 */
public class StreamBindingSender implements GameSender {
    private final DataOutputStream dos;

    public StreamBindingSender(DataOutputStream dos) {
        this.dos = dos;
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        this.dos.writeInt(StreamBinding.DICE);
        this.dos.writeInt(random);
    }

    @Override
    public void sendShoot(int x, int y) throws IOException, StatusException, GameException {
        this.dos.writeInt(StreamBinding.SHOOT);
        this.dos.writeInt(x);
        this.dos.writeInt(y);
        
    }

    @Override
    public void sendShootResult(int[] result) throws IOException, StatusException {
        this.dos.write(StreamBinding.RESULT);
        for (int i = 0; i < result.length; i++) {
            this.dos.write(result[i]);
        }       
    }
}