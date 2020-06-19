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
    ////                                   Place ships                                     ////
    //////////////////////////////////////////////////////////////////////////////////////////
        
//        int[] ships = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2}; // ships' length
//        System.out.println("Place ships");
//        engine.displayShipBoard();
//        for (int i = 0; i < ships.length; i++) {
//            int length = ships[i];
//            while (true) {
//                try {
//                    int isHorizontal = console.readBinaryFromStdin("Enter direction for " + (i+1) +". ship with length " + length +" (1 for horizontal, 0 for vertical): ", "Error: Only 0 or 1");
//                    int x = console.readIntegerFromStdin("Enter coordinate x (vertical) for " + (i+1) +". ship with length " + length +": ", "Error: Only integers") - 1;
//                    int y = console.readIntegerFromStdin("Enter coordinate y (horizontal) for " + (i+1) +". ship with length " + length +": ", "Error: Only integers") - 1;
//                    engine.placeShip(x, y, length, isHorizontal);
//                    System.out.println("finished111");
//                    break;
//                } catch (Exception e) {
//                    System.err.print(e.getLocalizedMessage()+"\n");
//                }
//            }
//            engine.displayShipBoard();
//        }
        // for testing
        try {
            engine.placeShip(0, 0, 5, 1);
            engine.placeShip(1, 1, 4, 1);
            engine.placeShip(2, 2, 4, 1);
            engine.placeShip(3, 3, 3, 1);
            engine.placeShip(4, 4, 3, 1);
            engine.placeShip(5, 5, 3, 1);
            engine.placeShip(6, 6, 2, 1);
            engine.placeShip(7, 7, 2, 1);
            engine.placeShip(8, 8, 2, 1);
            engine.placeShip(9, 8, 2, 1);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        
        while(true) {
            if (engine.isActive()) {
                engine.displayShipBoard();
                engine.displayRecordBoard();
                while (true) {
                    try {
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
                engine.displayShipBoard();
                engine.displayRecordBoard();
            }
            if (engine.isPassive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
            if (engine.isGameOver()) {
                receiver.interrupt();;
                break;
            }
        }
    }

    private static void printUsageMessage() {
        System.err.println("wrong parameter set, Use");
        System.err.println("test <portnumber> -- to be set up as server");
        System.err.println("test <hostname> <portnumber> -- to be set up as client");
    }
}
