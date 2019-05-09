package fzt.socket.onetoone;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description 服务端
 * @Author fengzt
 * @Date 2019/5/8
 * @Version 1.0
 **/
public class Server {

    private ServerSocket ss;

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("等待客户端连接。。。");
        PrintWriter pwToClient = null;
        Scanner keyBordScanner = null;
        Scanner inScanner = null;
        try {
            //创建一个接收连接客户端的对象
            Socket socket = ss.accept();
            System.out.println(socket.getInetAddress()+"已成功连接到此台服务器上。");
            //字符输出流
            pwToClient = new PrintWriter(socket.getOutputStream());
            pwToClient.println("已成功连接到远程服务器！"+"\t"+"请您先发言。");
            pwToClient.flush();
            keyBordScanner = new Scanner(System.in);
            inScanner = new Scanner(socket.getInputStream());
            //阻塞等待客户端发送消息过来
            while(inScanner.hasNextLine()){
                String inData = inScanner.nextLine();
                System.out.println("客户端："+inData);
                System.out.print("服务端：");
                String keyBordData = keyBordScanner.nextLine();
                pwToClient.println(keyBordData);
                pwToClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (pwToClient != null) {
                pwToClient.close();
            }
            if (keyBordScanner != null) {
                keyBordScanner.close();
            }
            if (inScanner != null) {
                inScanner.close();
            }
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1314);
        server.start();
    }
}
