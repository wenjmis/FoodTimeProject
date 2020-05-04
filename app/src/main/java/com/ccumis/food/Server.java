package com.ccumis.food;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class Server {

    //服务器
    public static void testServer(){
        //创建一个服务器
        System.out.println("等待客戶端連接聊天室。。。");
        PrintWriter pwtoclien = null;
        Scanner keybordscanner = null;
        Scanner inScanner = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(6666);
            //创建一个接收连接客户端的对象
            Socket socket = ss.accept();
            System.out.println(socket.getInetAddress()+"已成功連接。");
            //字符输出流
            pwtoclien = new PrintWriter(socket.getOutputStream());
            pwtoclien.println("已成功連接！"+"\t"+"發言時請注意言語用詞！");
            pwtoclien.flush();
            keybordscanner = new Scanner(System.in);
            inScanner = new Scanner(socket.getInputStream());
            //阻塞等待客户端发送消息过来
            while(inScanner.hasNextLine()){
                String indata = inScanner.nextLine();
                System.out.println("客户端："+indata);
                System.out.print("服務端：");
                String keyborddata = keybordscanner.nextLine();
                System.out.println("服務端："+keyborddata);
                pwtoclien.println(keyborddata);
                pwtoclien.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            pwtoclien.close();
            keybordscanner.close();
            inScanner.close();
            try {
                ss.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        testServer();
    }



}
