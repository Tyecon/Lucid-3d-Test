package System;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class Settings {
    /*private static Settings singleton;
    
    public static Settings getSettings() {
        if (singleton==null) {singleton=new Settings();}
        return singleton;
    }*/
    
    /** Prints a stack trace of the input exception to System.err
     * @param ex The exception to be printed */
    public static void Catch(Exception ex) {
        ex.printStackTrace(System.err);
    }
    
    /** Creates an alert and exits
     @param msg The message to be displayed in the alert */
    public static void Crash(String msg) {
        Sys.alert("Unrecoverable Crash", msg);
        System.exit(-1);
    }
    
    //Timing
    /** A timer used for repetive timed tasks */
    public static final Timer TIMER=new Timer(true);
    /** The amount of time in milliseconds between frames */
    public static float Delta=0;
	private static long lastTime=0;
    /** The amount of frames that have passed since the last second */
    private static int frameCount=0;
    /** The amount of frames per second */
    public static int FPS=0;
    /** Increments frameCount
     * @return The current frameCount */
    public static int tickF() {Delta=System.currentTimeMillis()-lastTime; lastTime=System.currentTimeMillis(); return ++frameCount;}
    /** Measures the framerate and resets frameCount
     * @return The calculated framerate */
    public static int tickS() {FPS=frameCount; frameCount=0; return FPS;}
    
    //Final
    public static final String OS=LWJGLUtil.getPlatformName();
    public static final String LWJGL_VER=Sys.getVersion();
    public static final boolean IS_64_BIT=Sys.is64Bit();
    public static final Random RAN=new Random();
    public static final String VID_CARD=Display.getAdapter()+" v:"+Display.getVersion();
    public static final String GL_VER=GL11.glGetString(GL11.GL_VERSION);
	public static final String workingDirectory=System.getProperty("user.dir");
	public static final String resourceDirectory="src"+File.separatorChar+"Resources"+File.separatorChar;
    
    //Toggle
    public static boolean Wireframe=false;
}
