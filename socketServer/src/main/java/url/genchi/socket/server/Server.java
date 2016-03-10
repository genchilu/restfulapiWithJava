package url.genchi.socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class Server {

    public static final int LISTEN_PORT = 5987;
    private static enum CMD {GET, POST, DELETE, UPDATE}
    private static ConcurrentHashMap<String, JSONObject> userMap = new ConcurrentHashMap<String, JSONObject>();
    public void listenRequest() {
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket( LISTEN_PORT );
            System.out.println("Server listening requests...");
            while ( true ) {
                Socket socket = serverSocket.accept();
                threadExecutor.execute(new RequestThread( socket ));
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( threadExecutor != null ) {
                threadExecutor.shutdown();
            }
            if ( serverSocket != null ) {
                try {
                    serverSocket.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * process request
     */
    private static class RequestThread implements Runnable {
        private Socket clientSocket;

        public RequestThread( Socket clientSocket ) {
            this.clientSocket = clientSocket;
        }

        static private JSONObject genReturnJson(String status, String msg) {
            JSONObject errJson = new JSONObject();
            errJson.put("status", status);
            if(!msg.isEmpty()){
                errJson.put("msg", msg);
            }
            return errJson;
        }

        static private String getUserProfileJsonStr(String username) {
            if(userMap.get(username) != null) {
                return userMap.get(username).toString();
            } else {
                return genReturnJson("error", "no user exist").toString();
            }
        }

        static private String addUserProfile(String username, JSONObject userProfile) {
            if(userMap.get(username) != null) {
                return genReturnJson("error", "user already exist").toString();
            } else {
                userMap.put(username, userProfile);
                return genReturnJson("successed", "").toString();
            }
        }

        static private String deleteUserProfile(String username) {
            if(userMap.get(username) != null) {
                userMap.remove(username);
                return genReturnJson("successed", "").toString();
            } else {
                return genReturnJson("error", "no user exist").toString();
            }
        }

        static private String updateUserProfile(String username, JSONObject newUserProfile) {
            if(userMap.get(username) != null) {
                JSONObject oriUserProfile = userMap.get(username);
                if(newUserProfile.has("address")) {
                    oriUserProfile.put("address", newUserProfile.getString("address"));
                }
                if(newUserProfile.has("age")) {
                    oriUserProfile.put("age", newUserProfile.getInt("age"));
                }
                userMap.put(username, oriUserProfile);
                return genReturnJson("successed", "").toString();
            } else {
                return genReturnJson("error", "no user exist").toString();
            }
        }

        public void run() {
            System.out.printf("connection from %s in!\n", clientSocket.getRemoteSocketAddress());
            DataInputStream input = null;
            DataOutputStream output = null;
            CMD cmd;
            String response = "";
            String username;
            try {
                input = new DataInputStream( this.clientSocket.getInputStream());
                String inJsonStr = input.readUTF();
                JSONObject inJson = new JSONObject(inJsonStr);
                cmd = CMD.valueOf(inJson.getString("cmd"));
                username = inJson.getString("username");
                switch (cmd) {
                case GET:
                    response = getUserProfileJsonStr(username);
                    break;
                case POST:
                    response = addUserProfile(username, inJson.getJSONObject("profile"));
                    break;
                case DELETE:
                    response = deleteUserProfile(username);
                    break;
                case UPDATE:
                    response = updateUserProfile(username, inJson.getJSONObject("profile"));
                    break;
                default:
                    response = getUserProfileJsonStr(username);
                    break;
                }
                output = new DataOutputStream( this.clientSocket.getOutputStream());
                output.writeUTF(response);
                output.flush();
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                try {
                    if ( input != null ) {
                        input.close();
                    }
                    if ( output != null ) {
                        output.close();
                    }
                    if ( this.clientSocket != null && !this.clientSocket.isClosed()) {
                        this.clientSocket.close();
                    }
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main( String[] args )
    {
        Server server = new Server();
        server.listenRequest();
    }
}