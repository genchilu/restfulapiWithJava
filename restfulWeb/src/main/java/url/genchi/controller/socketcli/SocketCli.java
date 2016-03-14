package url.genchi.controller.socketcli;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.json.JSONObject;

public class SocketCli implements Callable<String> {
    private String cmd;
    private String username;
    private String field;
    private String value;
    public SocketCli(String cmd, String username, String field, String value) {
        this.cmd = cmd;
        this.field = field;
        this.username = username;
        this.value = value;
    }
    public String call() throws Exception {
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
}