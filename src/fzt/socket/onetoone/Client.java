package fzt.socket.onetoone;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description 客户端
 * @Author fengzt
 * @Date 2019/5/8
 * @Version 1.0
 **/
public class Client{

    private Socket socket;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        System.out.println("正在向服务器请求连接。。。");
        Scanner keyBordScanner = null;
        Scanner inScanner = null;
        PrintWriter pwToServer = null;
        try {
            inScanner = new Scanner(socket.getInputStream());
            System.out.println(inScanner.nextLine());
            pwToServer = new PrintWriter(socket.getOutputStream());
            System.out.print("客户端：");
            //先读取键盘录入方可向服务端发送消息
            keyBordScanner = new Scanner(System.in);
            while(keyBordScanner.hasNextLine()){
                String keyBordData = keyBordScanner.nextLine();
                //写到服务端的的控制台
                pwToServer.println(keyBordData);
                pwToServer.flush();
                //阻塞等待接收服务端的消息
                String inData = inScanner.nextLine();
                System.out.println("服务端："+inData);
                System.out.print("客户端：");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (keyBordScanner != null) {
                keyBordScanner.close();
            }
            if (pwToServer != null) {
                pwToServer.close();
            }
            if (inScanner != null) {
                inScanner.close();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 1314);
        client.start();
    }
}
