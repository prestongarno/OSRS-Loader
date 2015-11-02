/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleBot;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

/**
 *
 * @author Preston Garno
 */
public final class Utilities {

    private static Utilities instance = new Utilities();

    private Utilities() {
    }

    public static Utilities getInstance() {
        return instance;
    }

    public static int random(int min, int max) {
        Random rand = null;
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void sleep(int Time) {
        try {
            Thread.sleep(Time);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void sleepRandom(int min, int max) {
        Random rand = null;
        try {
            Thread.sleep(rand.nextInt((max - min) + 1) + min);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static void screenShot(BufferedImage image) {
        try {
            File Directory = new File("ScreenShots/");
            if (!Directory.exists()) {
                Directory.mkdir();
            }

            if (Directory.exists()) {
                File Picture = new File("ScreenShots/" + getDate().toString() + ".png");
                ImageIO.write(image, "png", Picture);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void downloadFile(URL Address, String Path) throws IOException {
        URLConnection Connection = Address.openConnection();
        try {
            FileOutputStream OutFile = new FileOutputStream(Path);
            InputStream IStream = Connection.getInputStream();
            byte[] Buffer = new byte[1024];
            for (int Read = 0; (Read = IStream.read(Buffer)) != -1;) {
                OutFile.write(Buffer, 0, Read);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String downloadPage(URL Address) throws IOException {
        try {
            URLConnection Connection = Address.openConnection();
            Connection.addRequestProperty("Protocol", "HTTP/1.1");
            Connection.addRequestProperty("Connection", "keep-alive");
            Connection.addRequestProperty("Keep-Alive", "200");
            Connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");
            byte[] Buffer = new byte[Connection.getContentLength()];
            try {
                DataInputStream Stream = new DataInputStream((Connection.getInputStream()));
                Stream.readFully(Buffer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new String(Buffer);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Image scaleImage(int width, int height, Image image) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    public ImageIcon scaleIcon(int width, int height, ImageIcon imageIcon) {
        return new ImageIcon(scaleImage(width, height, imageIcon.getImage()));
    }
    
    public Image loadResourceImage(String ResourcePath) {
        try {
            return ImageIO.read(getClass().getResource(ResourcePath));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Image grayScale(Image image, int GrayPercentage) {
        ImageProducer producer = new FilteredImageSource(image.getSource(), new GrayFilter(true, GrayPercentage));
        return java.awt.Toolkit.getDefaultToolkit().createImage(producer);
    }
}
