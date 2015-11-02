/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleBot;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import loaders.*;

/**
 *
 * @author Preston Garno
 */
public class Client {

    private ClientApplet loader = null;
    private BufferedImage gameBuffer = null;
    private BufferedImage paintBuffer = null;

    public Client(String World, int Width, int Height) {
        this.loader = new ClientApplet(World, true, Width, Height);

        //create buffered that are the same size as the applet's canvas
        this.gameBuffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        this.gameBuffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        
        //start applet
        this.getLoader().start();
    }

    public Canvas getCanvas() {
        return this.getApplet().getComponentCount() > 0 ? (Canvas) this.getApplet().getComponent(0) : null;
    }

    public ClientApplet getLoader() {
        return this.loader;
    }

    public Applet getApplet() {
        //really JPanel with an applet inside of it
        return this.loader != null ? (Applet) this.loader.getComponent(0) : null;
    }
    
    public Graphics drawGraphics(Graphics2D G) {
        Graphics paintGraphics = paintBuffer.getGraphics(); //get paintGraphics GDI+ handle
        paintGraphics.drawImage(this.gameBuffer, 0, 0, null);
        
        //draw string test onto paintBuffer
        paintGraphics.setColor(Color.WHITE);
        paintGraphics.drawString("This is just a test I hope to God this works xDDDDDDD", 100, 100);
        paintGraphics.dispose(); // clean handle
        
        if (G != null) {
            G.drawImage(this.paintBuffer, 0, 0, null); //draw DebugBuffer onto Canvas component/gameBuffer
        }
        
        return this.gameBuffer.getGraphics(); //return gameBuffer with all all drawings
    }
}
