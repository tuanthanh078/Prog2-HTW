package BattleShipGame;

import java.io.IOException;

import gameEngine.GameEngine;
import gameEngine.GameSender;
import gameEngine.StatusException;
import protocolBinding.StreamBindingReceiver;
import protocolBinding.StreamBindingSender;
import transmission.DataConnectorThread;

public class test {
    public static void main(String args[]) throws IOException, StatusException {
        if(args.length < 1 || args.length > 2) {
            test.printUsageMessage();
            System.exit(0);
        }
        Console console = new Console();
        DataConnectorThread connector = null;
        GameSender sender = null;

        if(args.length == 1) {
            // server
            connector = new DataConnectorThread(Integer.parseInt(args[0]));
   
        } else if(args.length == 2) {
            // client
            connector = new DataConnectorThread(args[0], Integer.parseInt(args[1]));
        }
        try {
            connector.start();
            connector.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sender = new StreamBindingSender(connector.getDataOutputStream());
        GameEngine engine = new GameEngine(sender);
        StreamBindingReceiver receiver = new StreamBindingReceiver(connector.getDataInputStream(), engine);
        receiver.start();
        engine.doDice();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("isActive " + engine.isActive());
        System.out.println("isPassive " + engine.isPassive());
        //while(true) {
            
            // read user input
            //String stringFromUserInput = userInput.readLine();

            // trim whitespaces on both sides
            //stringFromUserInput = stringFromUserInput.trim();

            // send
            //sender.send(stringFromUserInput);
        //}
    }

    private static void printUsageMessage() {
        System.err.println("wrong parameter set, Use");
        System.err.println("test <portnumber> -- to be set up as server");
        System.err.println("test <hostname> <portnumber> -- to be set up as client");
    }
}
