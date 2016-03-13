package url.genchi.controller.socketcli;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

@Component
public class SocketCli {

    @Async
    public Future<String> sendCmdAsync(String cmd, String username, String field, String value) throws InterruptedException, IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
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
        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        return new AsyncResult<String>(response);
    }

}