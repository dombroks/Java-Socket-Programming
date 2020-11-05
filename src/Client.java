import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {

        String serverName = "localhost";
        int port = 6066;
        try {
            System.out.println("Connecting to " + "localhost" + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());


            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            Commande c = new Commande("Consulter",121) ;
            outputStream.writeObject(c);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}