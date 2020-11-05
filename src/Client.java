import model.Commande;
import model.Triplet;

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws ClassNotFoundException {

        String serverName = "localhost";
        int port = 6066;

        try {
            System.out.println("Connecting to " + "localhost" + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());


            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());

            //Sending
            Commande c = new Commande("Debiter", 120, (float) 100);
            outputStream.writeObject(c);

            //Receiving
            Triplet t = (Triplet) inputStream.readObject();
            System.out.println(t.toString());


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}