import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket s;

    private Scanner in;
    private PrintWriter out;

    public Client(String host, int port) {
        try {
            s = new Socket(host, port);

            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String strOut) {
        System.out.println(strOut);
        out.write(strOut);
    }
}