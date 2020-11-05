import model.Commande;
import model.Triplet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server extends Thread {
    private static final String PATH_TO_FILE = "/home/dom/IdeaProjects/CCP/comptes.ccp.txt";
    private static final File file = new File(PATH_TO_FILE);
    private static Triplet triplet ;
    private final ServerSocket serverSocket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(50000);
    }

    public static Triplet crediter(int CCP, float Somme) {
        String ccp = String.valueOf(CCP);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        triplet = new Triplet("0", "solde insuffisant", "0");
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String nom = sc.nextLine();
                String s = sc.nextLine();
                Float f = Float.parseFloat(s) + Somme;
                triplet.setFirstParam(ccp);
                triplet.setSecondParam(nom);
                triplet.setThirdParam(f.toString());
                return triplet;
            }
        }
        return triplet;


    }

    public static Triplet debiter(int CCP, float Somme) {
        String ccp = String.valueOf(CCP);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        triplet = new Triplet("0", "Solde insuffisant", "0");
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String nom = sc.nextLine();
                String s = sc.nextLine();
                if (Float.parseFloat(s) < Somme) {
                    return triplet;
                } else {
                    Float f = Float.parseFloat(s) - Somme;
                    triplet.setFirstParam(ccp);
                    triplet.setSecondParam(nom);
                    triplet.setThirdParam(f.toString());
                    return triplet;
                }
            }
        }
        return triplet;
    }

    public static Triplet consulter(int CCP) {
        triplet = new Triplet("0", "Inexistant", "0");
        String ccp = String.valueOf(CCP);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                triplet = new Triplet(ccp, sc.nextLine(), sc.nextLine());
                break;
            }
        }
        return triplet;
    }

    public static void main(String[] args) {
        int port = 6066;
        try {
            Thread t = new Server(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());


                ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());


                Commande c = (Commande) inputStream.readObject();
                String operation = c.getOperation();

                if (operation.contains("Consulter")) {
                    int ccp = c.getCCP();
                    Triplet t = consulter(ccp);
                    outputStream.writeObject(t);
                } else if (operation.contains("Debiter")) {
                    int ccp = c.getCCP();
                    Float somme = c.getSomme();
                    Triplet t = debiter(ccp, somme);
                    outputStream.writeObject(t);


                } else if (operation.contains("Crediter")) {
                    int ccp = c.getCCP();
                    float somme = c.getSomme();
                    Triplet t = crediter(ccp, somme);
                    outputStream.writeObject(t);
                } else System.out.println("unknown operation");


                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}