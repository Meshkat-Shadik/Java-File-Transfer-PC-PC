/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sendfile.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author NguyenDang
 */
public class ServerThread implements Runnable {
    
    ServerSocket server;
    MainForm main;
    boolean keepGoing = true;
    
    public ServerThread(int port, MainForm main){
        main.appendMessage("[Server]: The server is currently booting at port "+ port);
        try {
            this.main = main;
            server = new ServerSocket(port);
            main.appendMessage("[Server]: The server has started.!");
        } 
        catch (IOException e) { main.appendMessage("[IOException]: "+ e.getMessage()); }
    }

    @Override
    public void run() {
        try {
            while(keepGoing){
                Socket socket = server.accept();

                /** SOcket thread **/
                new Thread(new SocketThread(socket, main)).start();
            }
        } catch (IOException e) {
            main.appendMessage("[ServerThreadIOException]: "+ e.getMessage());
        }
    }
    
    
    public void stop(){
        try {
            server.close();
            keepGoing = false;
            System.out.println("The server is closed ..!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
