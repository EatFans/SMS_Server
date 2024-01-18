import java.io.IOException;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        final String server_host = "127.0.0.1";
        final int server_port = 8585;

        try (Socket socket = new Socket(server_host,server_port)){
            System.out.println("服务器已经连接！");

            boolean flag = false;
            while (flag){


                if(!flag){
                    break;
                }
            }

            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
