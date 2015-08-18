package my.project.gop.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
	
	boolean fse = false;
	int fsm = 0; //Fullscreen Mode
	GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];//multiple screens, for 3 screens, 0, 1 ,2
	//Use this to choose the screen you want

	public GameWindow(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	private void setfullscreen() {
		switch(fsm) {
		case 0:
			System.out.println("No Fullscreen");
			setUndecorated(false);
			break;
		case 1:
			setUndecorated(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			break;
		case 2:
			setUndecorated(true);
			device.setFullScreenWindow(this);
			break;
		}
	}
	
	public void setFullscreen(int fsnm) { //Fullscreen New Mode
		fse = true;
		if (fsm <= 2) {
			this.fsm = fsnm;
			setfullscreen();
		} else {
			System.err.println("Error " + fsnm + " is not Supported!");
			
		}
	}

}
