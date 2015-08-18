package my.tdl.main;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

import my.project.gop.main.GameWindow;
import my.project.gop.main.SpriteSheet;
import my.tdl.MoveableObjects.Player;
import my.tdl.gameloop.GameLoop;
import my.tdl.managers.MouseManager;

public class Main {
	
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int width = gd.getDisplayMode().getWidth();
	public static int height = gd.getDisplayMode().getHeight();
	

	public static void main(String[] args) {
		GameWindow frame = new GameWindow("TheDlooter", width, height);
		//frame.setFullscreen(1); //Used to set fullscreen for the game
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0,0), "Cursor");
		frame.setCursor(cursor);
		
		frame.addMouseListener(new MouseManager());
		frame.addMouseMotionListener(new MouseManager());
		frame.addMouseWheelListener(new MouseManager());
		
		frame.addKeyListener(new Player());
		frame.add(new GameLoop(width, height));
		frame.setVisible(true); //Needs to be after full screen

	}

}
