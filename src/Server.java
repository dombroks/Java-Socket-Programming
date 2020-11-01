import sun.rmi.runtime.Log;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(50000);
    }

    public static String crediter(int CCP, int Somme) {
        String ccp = String.valueOf(CCP);
        File file = new File("/home/dom/Desktop/comptes.ccp.txt");

        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String info = "(0," + "Solde insuffisant" + ",0)";
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String nom = sc.nextLine();
                String s = sc.nextLine();
                Float f = Float.parseFloat(s) + Somme;
                return "(" + ccp + "," + nom + "," + f + ")";
            }
        }
        return info;


    }

    public static String debiter(int CCP, int Somme) {
        String ccp = String.valueOf(CCP);
        File file = new File("/home/dom/Desktop/comptes.ccp.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String info = "(0," + "Solde insuffisant" + ",0)";
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String nom = sc.nextLine();
                String s = sc.nextLine();
                if (Float.parseFloat(s) < Somme) {
                    return info;
                } else {
                    Float f = Float.parseFloat(s) - Somme;
                    return "(" + ccp + "," + nom + "," + f + ")";
                }
            }
        }
        return info;
    }

    public static String consulter(int CCP) {
        String ccp = String.valueOf(CCP);
        File file = new File("/home/dom/Desktop/comptes.ccp.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String info = "(0," + "Inexitant" + ",0)";
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                info = "(" + ccp + "," + sc.nextLine() + "," + sc.nextLine() + ")";
                break;
            }
        }
        return info;
    }


    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                String message = in.readUTF();
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                if (message.contains("Consulter")) {
                    message.replace("Consulter", "");
                    out.writeUTF( "Consulting" + consulter(120));
                }


                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
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
}