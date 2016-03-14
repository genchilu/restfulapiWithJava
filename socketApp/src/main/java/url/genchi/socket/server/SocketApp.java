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

public class SocketApp {

    public static final int LISTEN_PORT = 5987;
    private static enum CMD {GET, POST, DELETE, PUT}
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

    private static class RequestThread implements Runnable {
        private Socket clientSocket;

        public RequestThread( Socket clientSocket ) {
            this.clientSocket = clientSocket;
        }

        /**
         * @param username
         * @param field
         * @return value for a field, or "" while there is no value in a field in a user
         */
        static private String getUserFieldData(String username, String field) {
            if(userMap.get(username) != null && userMap.get(username).has(field)) {
                return userMap.get(username).getString(field);
            } else {
                return "";
            }
        }

        /**
         * @param username
         * @param field
         * @param value
         * @return "SUCESSED", or "FAILED" while there is already a value in a field in a user or input value is EMPTY
         */
        static private String addUserFieldData(String username, String field, String value) {
            if(userMap.get(username) != null) {
                if(userMap.get(username).has(field)) {
                    return "FAILED";
                } else {
                    if(!value.isEmpty()) {
                        userMap.get(username).put(field, value);
                        return "SUCCESSED";
                    } else {
                        return "FAILED";
                    }
                }
            } else {
                if(!value.isEmpty()) {
                    JSONObject userdata = new JSONObject();
                    userdata.put(field, value);
                    userMap.put(username, userdata);
                    return "SUCCESSED";
                } else {
                    return "FAILED";
                }
            }
        }

        /**
         * @param username
         * @param field
         * @return "SUCESSED", or "FAILED" while there is no user or input value is EMPTY
         */
        static private String deleteUserFieldData(String username, String field) {
            if(userMap.get(username) != null && userMap.get(username).has(field)) {
                userMap.get(username).remove(field);
                return "SUCCESSED";
            } else {
                return "FAILED";
            }
        }

        /**
         * @param username
         * @param field
         * @param value
         * @return "SUCESSED", or "FAILED" while there is no user input value is EMPTY
         */
        static private String updateUserFieldData(String username, String field, String value) {
            if(userMap.get(username) != null && !value.isEmpty()) {
                userMap.get(username).put(field, value);
                return "SUCCESSED";
            } else {
                return "FAILED";
            }
        }

        public void run() {
            System.out.printf("connection from %s in!\n", clientSocket.getRemoteSocketAddress());
            DataInputStream input = null;
            DataOutputStream output = null;
            CMD cmd;
            String response = "";
            String username;
            String field;
            String value;
            try {
                input = new DataInputStream( this.clientSocket.getInputStream());
                String inJsonStr = input.readUTF();
                JSONObject inJson = new JSONObject(inJsonStr);
                cmd = CMD.valueOf(inJson.getString("cmd"));
                username = inJson.getString("username");
                field = inJson.getString("field");
                value = inJson.getString("value");
                switch (cmd) {
                case GET:
                    response = getUserFieldData(username, field);
                    break;
                case POST:
                    response = addUserFieldData(username, field, value);
                    break;
                case DELETE:
                    response = deleteUserFieldData(username, field);
                    if(userMap.get(username) != null && !userMap.get(username).has("country") && !userMap.get(username).has("city")) {
                        userMap.remove(username);
                    }
                    break;
                case PUT:
                    response = updateUserFieldData(username, field, value);
                    break;
                default:
                    response = getUserFieldData(username, field);
                    break;
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                output = new DataOutputStream( this.clientSocket.getOutputStream());
                output.writeUTF(response);
                System.out.printf("connection from %s finish!\n", clientSocket.getRemoteSocketAddress());
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

    public static void main( String[] args ) throws Throwable
    {
        SocketApp server = new SocketApp();
        server.listenRequest();
    }
}