package url.genchi.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

public class Client {
    public static String testServer(String cmd, String username, String field, String value) throws IOException {
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
            outJson.put("field", field);
            outJson.put("value", value);
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

    private static void test(String cmd, String username, String field, String value, String expectResult, int id) throws IOException {
        String result = testServer(cmd, username, field, value);
        if(!result.equals(expectResult)) {
            System.out.println("err at " + Integer.toString(id));
            System.out.println("except: " + expectResult + ", result: " + result);
        }
    }
    public static void main( String[] args ) throws IOException {
        test("GET", "g7", "country", "", "", 1);
        test("GET", "g7", "city", "", "", 2);
        test("POST", "g7", "country", "", "FAILED", 3);     // FAILED
        test("POST", "g7", "country", "TW", "SUCCESSED", 4);
        test("GET", "g7", "country", "", "TW", 5);
        test("POST", "g7", "country", "TW", "FAILED", 6);   // FAILED
        test("POST", "g7", "city", "KH", "SUCCESSED", 7);
        test("DELETE", "g7", "country", "", "SUCCESSED", 8);
        test("DELETE", "g7", "country", "", "FAILED", 9);   // FAILED
        test("DELETE", "g7", "city", "", "SUCCESSED", 10);
        test("GET", "g7", "country", "", "", 11);      // FAILED
        test("GET", "g7", "city", "", "", 12);         // FAILED
        test("PUT", "g7", "country", "TW", "FAILED", 13);    // FAILED
        test("POST", "g7", "country", "TW", "SUCCESSED", 14);
        test("PUT", "g7", "country", "", "FAILED", 15);      // FAILED
        test("PUT", "g7", "country", "US", "SUCCESSED", 16);
    }
}
