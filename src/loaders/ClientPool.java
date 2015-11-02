/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaders;

import SimpleBot.Client;
import SimpleBot.*;
import java.awt.Canvas;
import java.util.HashMap;

/**
 *
 * @author Preston Garno
 */
public class ClientPool {
    private static ClientPool instance = new ClientPool();
    private static HashMap<Integer, Client> clients = new HashMap<>();
    //type Clients will go away when other classes are created
    
    private ClientPool(){}
    
    public static ClientPool getInstance(){
        return instance;
    }
    
    public static HashMap<Integer, Client> getClients(){
        return clients;
    }
    
    public static Client getClient(int hashCode) {
        synchronized (clients) {
            return clients.containsKey(hashCode) ? clients.get(hashCode) : null;
        }
    }
    
    public static Client getClient(Canvas canvas) {
        synchronized (clients) {
            int hashCode = canvas.getClass().getClassLoader().hashCode();
            return clients.containsKey(hashCode) ? clients.get(hashCode) : null;
        }
    }
    
}
