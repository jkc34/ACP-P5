import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicRequest extends Thread{
    Socket s;
    Scanner in;
    PrintWriter out;
    
    public MusicRequest (Socket socket){
        s = socket;
    }

    public void run() {
        try {
            try{
                in = new Scanner(s.getInputStream());
                out = new PrintWriter(s.getOutputStream());
                getRequest();
            }
            finally {
                System.out.println("Closing time");
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRequest() {
        System.out.println("Begin getRequest");
        ArrayList<String> parameters = new ArrayList<String>();
        String st = in.nextLine();
        System.out.println("Request = " + st + "\nthe parameters follow");
        String[] params = st.split(" ");

        System.out.print(params.length);

        for (int i=0; i<params.length; i++) {
            parameters.add(params[i]);
            System.out.println(parameters.get(i));
        }
        executeRequest(parameters);
    }

    public void executeRequest(ArrayList<String> params) {
        try { 
            System.out.println("on server in execReq");
            SimpleDataSource.init("database.properties");

            Connection conn = SimpleDataSource.getConnection();
            Statement stat = conn.createStatement();

            String queryString = buildQueryString(params);
            System.out.println("** query = [" + queryString + "]");
            ResultSet result = stat.executeQuery(queryString);
                System.out.print("2");
            String response = assembleResultString(result);
            System.out.println("before sending [" + response +"]");
            out.println(response);
            out.flush();
            conn.close();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buildQueryString(ArrayList<String> params) {
        String query = "SELECT instName, descrip, cost, quantity, address FROM Instruments,Locations,Inventory WHERE instNumber = iNumber AND lNumber = locNumber ";
        
        if(!params.get(0).equals("all")) {
            query += "and instName = '" + params.get(0) + "'";
        }
        if(!params.get(1).equals("null") && (params.get(1).compareTo("all") != 0)) {
            System.out.println("in here with params.get(1) = " + params.get(1));
            query += "and descrip = '" + params.get(1) + "'";
        }
        if(!params.get(2).equals("0")) {
            query += "and cost < " + params.get(2) + " ";
        }
        if(!params.get(3).equals("all")) {
            query += "and locName = '" + params.get(3) + "'";
        }

        return query.trim();
    }

    public String assembleResultString (ResultSet result) {
        String str = null;
        return str;
    }
}