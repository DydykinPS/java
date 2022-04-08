package com.mycompany.timer;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel
 */
public class Server {
    int port = 3124;
    InetAddress ip;
    ServerSocket ss;
    MyTimer timer = new MyTimer();
    Gson convert = new Gson();
    
    ArrayList<Message> allEvent = new ArrayList<>();
    ArrayList<ClientServer> allClient = new ArrayList<>();
    ArrayList<String> allEvent1 = new ArrayList<>();
    
    int count = 0;
    
    public void addMsg(Message m) {
        for (ClientServer serverClient: allClient) {
            serverClient.sendMsg(m);
        }
    }
    
    public Server() {
        try {
            ip = InetAddress.getLocalHost();
            
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Сервер запущен!");
            
            Thread t = new Thread(
                ()->{
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        timer.Tick();
                        System.out.println(timer.getHour() + " " + timer.getMin() + " " + timer.getSec());
                    }
                }
            );
            t.start();
            
            while (true) {
                Socket cs = ss.accept();
                System.out.println("Подключен клиент");
                count++;
                ClientServer sc = new ClientServer(count, cs, this);
                allClient.add(sc);
                Thread t1 = new Thread(
                    ()->{
                        try {
                            while (true) {
                                OutputStream os = cs.getOutputStream();
                                DataOutputStream obos = new DataOutputStream(os);
                                String str = convert.toJson(new Message(timer.getHour(), timer.getMin(), timer.getSec(), "", 0));
                                obos.writeUTF(str);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                );
                t1.start();
                
                Thread t2 = new Thread(
                        ()->{
                             try {
                                while (true) {
                                    InputStream is = cs.getInputStream();
                                    DataInputStream obis = new DataInputStream(is);
                                    String obj = obis.readUTF();
                                    Message message = convert.fromJson(obj, Message.class);
                                    allEvent.add(message);
                                    System.out.println("Запись");
                                    allEvent.get(0).setFl(1);
                                    
                                    for (Message i: allEvent) {
                                        //String str = convert.toJson(i);
                                        //obos.writeUTF(str);
                                        addMsg(i);
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                );
                t2.start();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Не могу создать сервер");
        }
    }
    
    
    public static void main(String[] args) {
        new Server();
    }
}
