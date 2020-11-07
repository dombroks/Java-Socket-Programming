import model.Command;
import model.Triplet;

import java.net.*;
import java.io.*;

public class Client extends Thread {

    public static Command check(int CCP) {
        return new Command("Consulter", CCP);
    }

    public static Command debit(int CCP, float sum) {
        return new Command("Debiter", CCP, sum);
    }

    public static Command credit(int CCP, float sum) {
        return new Command("Crediter", CCP, sum);
    }


    public void run() {
        while (true) {
            String serverName = "localhost";
            int port = 6066;

            try {
                System.out.println("Connecting to " + "localhost" + " on port " + port);
                Socket client = new Socket(serverName, port);
                System.out.println("Just connected to " + client.getRemoteSocketAddress());


                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());

                //Sending
                outputStream.writeObject(debit(120, 300));

                //Receiving
                Triplet t = (Triplet) inputStream.readObject();
                System.out.println(t.toString());

                client.close();
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        // Running 3 clients
        for (int i = 0; i < 3; i++) {
            Thread client = new Client();
            client.start();
        }

    }
}