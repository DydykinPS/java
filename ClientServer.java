package com.mycompany.timer;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel
 */
public class ClientServer {
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    Thread t;
    Server ms;
    int id;
    Gson convert = new Gson();

    public ClientServer(int id, Socket cs, Server ms) throws IOException {
        this.id = id;
        this.cs = cs;
        this.ms = ms;
        System.out.println("Подключился клиент " + id + "\n");
        
        try {
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        t = new Thread(
//          ()->{
//            try {
//                dis = new DataInputStream(cs.getInputStream());
//                while(true) {
//                    String obj;
//                    obj = dis.readUTF();
//                    
//                    Message msg = convert.fromJson(obj, Message.class);
//                    System.out.println("Получил " + msg);
//
//                    ms.addMsg(msg);
//
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//          }
//        );
//       t.start();
        sendID();
    }
    
    void sendID() {
        Message msg = new Message();
        msg.setId(id);
        
        msg.getMsg().addAll(ms.allEvent1);
        
        String sendStr = convert.toJson(msg);
        
        try {
            dos.writeUTF(sendStr);
            System.out.println("Отправил ID" + msg);
            
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendMsg(Message m){
        
        String sendStr = convert.toJson(m);
        
        try {
            dos.writeUTF(sendStr);
            System.out.println("Отправил строку " + " клиенту (" + id + ")");
            
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
