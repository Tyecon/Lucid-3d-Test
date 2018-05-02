package System;

import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.renderer.Renderer;
import Data.Handlers.Scene;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import java.awt.Canvas;
import java.util.TimerTask;
import org.lwjgl.opengl.GL32;
import java.io.File;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.ARBDepthClamp;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
	private static Main singleton;
	private static Canvas canvas;
	public static Scene currentScene;
	private static GUI gui;
	
	private Main(Canvas parent) {
		canvas=parent;
	}
	
	public static Main get(Canvas parent) {
		if (singleton==null) {singleton=new Main(parent);}
		return singleton;
	}
	
	public final void run() {
		//Load Native Libraries
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"),
				LWJGLUtil.getPlatformName()).getAbsolutePath());
		//Create OpenGL Context
		try {
			/*ContextAttribs context=new ContextAttribs(3, 2)
			.withForwardCompatible(true)
			.withProfileCore(true);*/
			PixelFormat pixfrm = new PixelFormat();
			Display.setParent(canvas);
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setVSyncEnabled(false);
			//Display.create(pixfrm, context);
			Display.create(pixfrm);
		}
		catch (LWJGLException ex) {
			ex.printStackTrace(System.err);
			System.exit(-1);
		}
		//Set OpenGL settings
		glClearColor(0.1f, 0.1f, 0.2f, 0f);
		//glClearColor(1f, 1f, 1f, 0f);
		glEnable(GL_DEPTH_TEST);
		if (glGetBoolean(ARBDepthClamp.GL_DEPTH_CLAMP)) {
			glEnable(GL32.GL_DEPTH_CLAMP);
		}
		glDepthMask(true);
		glDepthFunc(GL_LEQUAL);
		glDepthRange(0, 1);
		glClearDepth(1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		//Setup framerate counter
		Settings.TIMER.schedule(new TimerTask() {
			@Override
			public void run() {
				Settings.tickS();
			}
		}, 0, 1000);
		//Setup TWL Gui interface
		try {
			Renderer renderer = new LWJGLRenderer();
			Widget root = new Widget();
			root.setTheme("");
			gui = new GUI(root, renderer);
			gui.setSize();
			gui.applyTheme(
					ThemeManager.createThemeManager(
					new File(Settings.resourceDirectory + "GUI" + File.separatorChar + "simple.xml").toURI().toURL(),
					renderer));
			//TODO; Development GUI
			FPSCounter fps = new FPSCounter(2, 0);
			fps.setSize(100, 20);
			fps.setPosition(50, 50);
			root.add(fps);
		} catch(LWJGLException | IOException ex) {
			ex.printStackTrace(System.err);
		}
		//Default Scene
		currentScene = new Scene();
		//Gameloop
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			if (Settings.Wireframe) {
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			} else {
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
			Settings.tickF();
			currentScene.Update(Settings.Delta);
			Editor.setTitleS("Lucid Dev Build 0.0.100 FPS: " + Settings.FPS + " Delta: " + Settings.Delta + " Camera:" + currentScene.getMainCamera().getRotation());
			Display.sync(Display.getDesktopDisplayMode().getFrequency());
			gui.update();
			Display.update();
		}
		Display.destroy();
	}
}
