import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int PORT = 8081;
    ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Start Server");
            while(true) {
                Socket s = server.accept();
                System.out.println("Client Connected");

                MusicRequest db = new MusicRequest(s);
                
                Thread t = new Thread(db);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            try {
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {
        new Server();
    }
}