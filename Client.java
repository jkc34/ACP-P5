/*****************************************************************
  Program source: https://www.admfactory.com/socket-example-in-java/
 *****************************************************************/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    String host = "127.0.0.1";
    int port = 8081;

    Socket s = null;

    PrintWriter out = null;
    BufferedReader in = null;

    public Client(String host, int port) {
        try {
            String serverHostname = new String("127.0.0.1");

            System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
            System.out.println("Enter message, q to quit");

            try {
                s = new Socket(serverHostname, 8081);
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void end() {
        try {
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String str) {
        while (true) {
            out.println(str);
            //System.out.println("server: " + in.readLine());
        }
    }
}
