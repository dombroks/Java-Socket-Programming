import model.Command;
import model.Triplet;

import java.net.*;
import java.io.*;

public class Client {

    public static Command consulter(int CCP) {
        return new Command("Consulter", CCP);
    }

    public static Command debiter(int CCP, float somme) {
        return new Command("Debiter", CCP, somme);
    }

    public static Command crediter(int CCP, float somme) {
        return new Command("Crediter", CCP, somme);
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