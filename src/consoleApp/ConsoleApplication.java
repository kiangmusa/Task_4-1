/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleApp;

import javaChat.ClientConnection;

/**
 *
 * @author AngkyMusa
 */
public class ConsoleApplication {

    private ClientConnection client;

    public void startChat() {
        try {
            client = new ClientConnection();
            System.out.println("Input server IP : ");
            String ip = client.inputString();
            client.connect(ip);
            ReadInput in = new ReadInput();
            WriteOutput out = new WriteOutput();
            in.start();
            out.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ReadInput extends Thread {

        @Override
        public void run() {
            try {
                String inputKeyboard;
                do {
                    System.out.print(">> ");
                    inputKeyboard = client.inputString();
                    client.writeStream(inputKeyboard);

                } while (!inputKeyboard.equals("quit"));
                client.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class WriteOutput extends Thread {

        @Override
        public void run() {
            try {
                String inputan;
                while ((inputan = client.readStream()) != null) {
                    System.out.println(inputan);
                    System.out.print(">> ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
