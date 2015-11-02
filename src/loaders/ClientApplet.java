/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaders;

import SimpleBot.Utilities;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Preston Garno
 */
public class ClientApplet extends JPanel implements AppletStub {

    private static final long serialVersionUID = 5627929030422355843L;
    private Applet applet = null;
    private URLClassLoader ClassLoader = null;
    private URL codeBase = null, documentBase = null;
    private HashMap<String, String> parameters = new HashMap<>();
    private static final Pattern codeRegex = Pattern.compile("code=(.*?) ");
    private static final Pattern archiveRegex = Pattern.compile("archive=(.*?) ");
    private static final Pattern parameterRegex = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

    public ClientApplet(String World, boolean downloadGamePack, int Width, int Height) {
        try {
            setLayout(new BorderLayout(0, 0));
            String PageSource = Utilities.downloadPage(new URL(World)); //DL HTML source for specified world
            Matcher codeMatcher = codeRegex.matcher(PageSource); //attach codeRegex Matcher to the source
            Matcher archiveMatcher = archiveRegex.matcher(PageSource); //attach archivePattern matcher to source

            if (codeMatcher.find() && archiveMatcher.find()) {
                String Archive = archiveMatcher.group(1);
                String JarLocation = World + Archive; // world Jar file is: http:OldSchool(world#).runescape.com/ + Archive/JarName
                System.out.println(JarLocation);
                String Code = codeMatcher.group(1).replaceAll(".class", "");
                Matcher ParameterMatcher = parameterRegex.matcher(PageSource);
                this.codeBase = new URL(JarLocation);
                this.documentBase = new URL(World);

                while (ParameterMatcher.find()) { //for every parameter, add to HashMap<ParamKey, ParamValue>
                    this.parameters.put(ParameterMatcher.group(1), ParameterMatcher.group(2));
                }

                if (!downloadGamePack) { //if you don't want to download the JAR
                    ClassLoader = new URLClassLoader(new URL[]{new URL(JarLocation)});
                    applet = (Applet) ClassLoader.loadClass(Code).newInstance();
                } else { //If you DO want to DL the .jar
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm a");
                    Date date = new Date();
                    Utilities.downloadFile(new URL(JarLocation), "./gamepack" + dateFormat.format(date).toString() +".jar");
                    ClassLoader = new URLClassLoader(new URL[]{new URL(JarLocation)});
                    applet = (Applet) ClassLoader.loadClass(Code).newInstance();
                }

                applet.setStub(this); //set stub to applet
                applet.setPreferredSize(new Dimension(Width, Height));
                add(applet, BorderLayout.CENTER); //add applet to the panel
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Loading! Check your Internet connection.", "Error Loading...", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void start() {
        if (this.applet != null) {
            this.applet.init();
        }

        if (this.applet != null) {
            this.applet.start();
        }
    }

    public void destruct() {
        if (this.applet != null) {
            this.remove(this.applet);
            this.applet.stop();
            this.applet.destroy();
            this.applet = null;
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        return this.documentBase;
    }

    @Override
    public URL getCodeBase() {
        return this.codeBase;
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int width, int height) {
    }
}
