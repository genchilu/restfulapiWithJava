package url.genchi.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

public class Client {
    public static String testServer(String cmd, String username, JSONObject profile) throws IOException {
        Socket socket = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        String response = "";
        try {
            socket = new Socket( "127.0.0.1", 5987 );
            input = new DataInputStream( socket.getInputStream() );
            output = new DataOutputStream( socket.getOutputStream());
            //handle output to server
            JSONObject outJson = new JSONObject();
            outJson.put("cmd", cmd);
            outJson.put("username", username);
            if(profile != null) {
                outJson.put("profile", profile);
            }
            output.writeUTF(outJson.toString());
            output.flush();
            response = input.readUTF();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( socket != null ) {
                socket.close();
            }
            if ( input != null ) {
                input.close();
            }
            if ( output != null ) {
                output.close();
            }
        }
        return response;
    }

    public static void main( String[] args ) throws IOException {
        JSONObject profile1 = new JSONObject();
        profile1.put("address", "TPE");
        profile1.put("age", 1);
        JSONObject profile2 = new JSONObject();
        profile2.put("age", 2);
        JSONObject profile3 = new JSONObject();
        profile3.put("address", "KH");
        System.out.println(testServer("GET", "g7", null));
        System.out.println(testServer("UPDATE", "g7", profile2));
        System.out.println(testServer("UPDATE", "g7", profile3));
        System.out.println(testServer("POST", "g7", profile1));
        System.out.println(testServer("POST", "g7", profile1));
        System.out.println(testServer("GET", "g7", profile1));
        System.out.println(testServer("UPDATE", "g7", profile2));
        System.out.println(testServer("GET", "g7", null));
        System.out.println(testServer("UPDATE", "g7", profile3));
        System.out.println(testServer("GET", "g7", null));
        System.out.println(testServer("DELETE", "g7", null));
        System.out.println(testServer("DELETE", "g7", null));
    }
}
