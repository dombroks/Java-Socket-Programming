import model.Commande;
import model.Triplet;

import java.net.*;
import java.io.*;

public class Client {

    public static Commande consulter(int CCP) {
        return new Commande("Consulter", CCP);
    }

    public static Commande debiter(int CCP, float somme) {
        return new Commande("Debiter", CCP, somme);
    }

    public static Commande crediter(int CCP, float somme) {
        return new Commande("Crediter", CCP, somme);
    }


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
            outputStream.writeObject(debiter(120,300));

            //Receiving
            Triplet t = (Triplet) inputStream.readObject();
            System.out.println(t.toString());


            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}