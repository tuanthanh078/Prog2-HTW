package BattleShipGame;

import java.io.IOException;

import gameEngine.GameEngine;
import gameEngine.GameSender;
import gameEngine.StatusException;
import protocolBinding.StreamBindingReceiver;
import protocolBinding.StreamBindingSender;
import transmission.DataConnectorThread;

public class GameGUI {
    static Console console = new Console();
    
    public static void main(String args[]) throws IOException, StatusException {
        if(args.length < 1 || args.length > 2) {
            System.out.println("Error: Unvalid arguments");
            System.exit(0);
        }
        console = new Console();
        DataConnectorThread connector = null;
        GameSender sender = null;

    //////////////////////////////////////////////////////////////////////////////////////////
    ////                                   Connection                                     ////
    //////////////////////////////////////////////////////////////////////////////////////////
        
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
            System.err.print(e.getLocalizedMessage());
        }
        
        
    //////////////////////////////////////////////////////////////////////////////////////////
    ////                                   Place ships                                    ////
    //////////////////////////////////////////////////////////////////////////////////////////
        
        placeShips(engine);

        
    //////////////////////////////////////////////////////////////////////////////////////////
    ////                                   Playing                                        ////
    //////////////////////////////////////////////////////////////////////////////////////////
        while(true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
            if (engine.isActive()) {
                System.out.println();
                engine.displayShipBoard();
                System.out.println();
                engine.displayRecordBoard();
                while (true) {
                    try {
                        System.out.println();
                        int x = console.readIntegerFromStdin("Enter coordinate x (vertical) for shooting: ", "Error: Only integers") - 1;
                        int y = console.readIntegerFromStdin("Enter coordinate y (horizontal) for shooting: ", "Error: Only integers") - 1;
                        engine.sendShoot(x, y);
                        break;
                    } catch (Exception e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                System.out.println();
                engine.displayShipBoard();
                System.out.println();
                engine.displayRecordBoard();
                if (engine.isGameOver()) {
                    receiver.interrupt();
                    System.exit(0);
                    break;
                }
                System.out.println();
                System.out.println("Waiting for your enemy... ");
            }
            if (engine.isPassive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                if (engine.isGameOver()) {
                    receiver.interrupt();
                    System.exit(0);
                    break;
                }
            }
        }
    }
    
    private static void placeShips(GameEngine engine) {
        int[] ships = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2}; // ships' length
        System.out.println();
        System.out.println("Place your ships: ");
        System.out.println();
        engine.displayShipBoard();
        for (int i = 0; i < ships.length; i++) {
            int length = ships[i];
            while (true) {
                try {
                    System.out.println();
                    int isHorizontal = console.readBinaryFromStdin("Enter direction for " + (i+1) +". ship with length " + length +" (1 for horizontal, 0 for vertical): ", "Error: Only 0 or 1");
                    int x = console.readIntegerFromStdin("Enter coordinate x (vertical) for " + (i+1) +". ship with length " + length +": ", "Error: Only integers") - 1;
                    int y = console.readIntegerFromStdin("Enter coordinate y (horizontal) for " + (i+1) +". ship with length " + length +": ", "Error: Only integers") - 1;
                    engine.placeShip(x, y, length, isHorizontal);
                    break;
                } catch (Exception e) {
                    System.err.print(e.getLocalizedMessage()+"\n");
                }
            }
            System.out.println();
            engine.displayShipBoard();
        }
    }
     
}
