

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;




public class Client {

    public static void testClient(){
        System.out.println("等待連接服務端");
        Socket socket = null;
        Scanner keybordscanner = null;
        Scanner inScanner = null;
        PrintWriter pwtoserver = null;
        try {
            socket = new Socket("127.0.0.1", 6666);
            inScanner = new Scanner(socket.getInputStream());
            System.out.println(inScanner.nextLine());
            pwtoserver = new PrintWriter(socket.getOutputStream());
            System.out.print("客户端：");
            //先读取键盘录入方可向服务端发送消息
            keybordscanner = new Scanner(System.in);
            while(keybordscanner.hasNextLine()){
                String keyborddata = keybordscanner.nextLine();
                //展示到己方的控制台
                System.out.println("客户端："+keyborddata);
                //写到服务端的的控制台
                pwtoserver.println(keyborddata);
                pwtoserver.flush();
                //阻塞等待接收服务端的消息
                String indata = inScanner.nextLine();
                System.out.println("服務端："+indata);
                System.out.print("客户端：");
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            keybordscanner.close();
            pwtoserver.close();
            inScanner.close();
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        testClient();
    }



}