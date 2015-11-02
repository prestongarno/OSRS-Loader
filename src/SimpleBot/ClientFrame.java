/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleBot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import loaders.ClientPool;

/**
 *
 * @author Preston Garno
 */
public class ClientFrame extends JFrame {

    private JLabel SplashScreen = null;
    private static final HashMap<Integer, Client> clients = ClientPool.getClients();

    public ClientFrame(String Name) {
        setTitle(Name);
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        //create splashscreen
        try {
            addSplashScreen(new ImageIcon("src/resources/logo.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        Utilities.sleep(1000); // just to show the splashscreen lol
        Client client = new Client("http://oldschool1.runescape.com/", 765, 553);

        while (client.getCanvas() == null) {
            Utilities.sleep(100);
        }
        
        addClient(client);
    }

    private final void addSplashScreen(ImageIcon imageIcon) {
        SplashScreen = new JLabel(imageIcon);
        add(SplashScreen, BorderLayout.CENTER);
        pack();
    }

    private final void removeSplashScreen() {
        if (SplashScreen != null) {
            remove(SplashScreen);
            SplashScreen = null;
        }
    }

    public void addClient(Client client) {
        synchronized (clients) { //support multithreading when adding tabs
            clients.put(client.getCanvas().getClass().getClassLoader().hashCode(), client);
            removeSplashScreen();
            add(client.getLoader(), BorderLayout.CENTER);
            pack();
        }
    }
}
