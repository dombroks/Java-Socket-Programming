import model.Command;
import model.Triplet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server {
    private static final String PATH_TO_FILE = "/home/dom/IdeaProjects/CCP/comptes.ccp.txt";
    private static File file = new File(PATH_TO_FILE);
    private static Triplet triplet;
    private final ServerSocket serverSocket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(50000);
    }

    public static synchronized Triplet credit(int CCP, float Sum) {
        String ccp = String.valueOf(CCP);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        triplet = new Triplet("0", "insufficient balance", "0");
        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String name = sc.nextLine();
                String s = sc.nextLine();
                Float f = Float.parseFloat(s) + Sum;
                triplet.setFirstParam(ccp);
                triplet.setSecondParam(name);
                triplet.setThirdParam(f.toString());
                updateFile(s, f.toString());
                return triplet;
            }
        }
        return triplet;
    }

    public static synchronized Triplet debit(int CCP, float Sum) {
        String ccp = String.valueOf(CCP);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        triplet = new Triplet("0", "insufficient balance", "0");


        while (sc.hasNextLine()) {
            if (sc.nextLine().matches(ccp)) {
                String name = sc.nextLine();
                String s = sc.nextLine();
                if (Float.parseFloat(s) < Sum) {
                    return triplet;
                } else {
                    Float f = Float.parseFloat(s) - Sum;
                    triplet.setFirstParam(ccp);
                    triplet.setSecondParam(name);
                    triplet.setThirdParam(f.toString());
                    updateFile(s, f.toString());
                    return triplet;
                }
            }
        }
        return triplet;
    }

    public static synchronized Triplet check(int CCP) {
        triplet = new Triplet("0", "None", "0");
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


    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                ObjectOutputStream outputStream = new ObjectOutputStream(server.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(server.getInputStream());


                Command c = (Command) inputStream.readObject();
                String operation = c.getOperation();

                if (operation.contains("Consulter")) {
                    int ccp = c.getCCP();
                    Triplet t = check(ccp);
                    outputStream.writeObject(t);
                } else if (operation.contains("Debiter")) {
                    int ccp = c.getCCP();
                    Float sum = c.getSum();
                    Triplet t = debit(ccp, sum);
                    outputStream.writeObject(t);


                } else if (operation.contains("Crediter")) {
                    int ccp = c.getCCP();
                    float sum = c.getSum();
                    Triplet t = credit(ccp, sum);
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

    private static void updateFile(String oldLine, String newLine) {
        try {
            Scanner sc = new Scanner(file);
            StringBuffer buffer = new StringBuffer();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine() + "\n");
            }
            String fileContents = buffer.toString();
            sc.close();
            fileContents = fileContents.replaceAll(oldLine, newLine);

            FileWriter writer = new FileWriter(PATH_TO_FILE);
            writer.append(fileContents);
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 6066;
        try {
            Server server = new Server(port);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}