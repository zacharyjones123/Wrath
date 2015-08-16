package my.tdl.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Light;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.generator.World;
import my.tdl.main.Main;

public class HUDManager {
	
	//100 is the size of the map and 32 is the size of the blocks
	private BufferedImage lightmap = new BufferedImage(100*48, 100*48, BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Vector2F lightm = new Vector2F();
	//private BufferedImage light; 3)
	private World world;

	public HUDManager(World world) {
		this.world = world;
		addLights();
		//light = loadImageFrom.LoadImageFrom(Main.class, "Light.png"); 1)
	}
	
	private static Polygon up;
	private static Polygon down;
	private static Polygon right;
	private static Polygon left;
	
	private void addLights() {
		//lights.add(new Light((int)(Main.width / 2.0 - world.getPlayer().getPos().xpos / 2), (int)(Main.height / 2 - world.getPlayer().getPos().ypos) / 2, 1000, 255));
		lights.add(new Light(0, 0, 1000, 255));
		updateLights();
		
		/*UP*/
		int[] ux = new int[]{Main.width, Main.width / 2, Main.width / 2, 0};
		int[] uy = new int[]{0, Main.height / 2, Main.height / 2, 0};
		up = new Polygon(ux, uy, ux.length);

		
		/*DOWN*/
		int[] dx = new int[]{Main.width, Main.width / 2, Main.width / 2, 0};
		int[] dy = new int[]{Main.height, Main.height / 2, Main.height / 2, Main.height};
		down = new Polygon(dx, dy, dx.length);

		
		/*RIGHT*/
		int[] rx = new int[]{Main.width, Main.width / 2, Main.width / 2, Main.width}; //xtop, xmiddle, xmiddle, xbottom
		int[] ry = new int[]{Main.height, Main.height / 2, Main.height / 2, 0};
		right = new Polygon(rx, ry, rx.length);

		
		/*LEFT*/
		int[] lx = new int[]{0, Main.width / 2, Main.width / 2, 0};
		int[] ly = new int[]{Main.height, Main.height / 2, Main.height / 2, 0};
		left = new Polygon(lx, ly, lx.length);
	}
	
	public void updateLights() {
		Graphics2D g = null;
		if(g == null) {
			g = (Graphics2D) lightmap.getGraphics();
		}
		g.setColor(new Color(0, 0, 0, 255)); //Black Screen
		g.fillRect(0, 0, lightmap.getWidth(), lightmap.getHeight());
		g.setComposite(AlphaComposite.DstOut); //Use lowercase, not DSTOUT
		
		for (Light light : lights) {
			light.render(g); 
		}
		g.dispose();
	}
	
	public void render(Graphics2D g) {
		//updateLights();
		
		//Draws all of the lights on the screen
		//g.drawImage(lightmap, (int)lightm.getWorldLocation().xpos, (int)lightm.getWorldLocation().ypos, null);
		
		
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, Main.width, Main.height / 6);
		//g.fillRect(0, 750, Main.width, Main.height / 6);
		//g.setColor(Color.WHITE);
		

		if (world.getPlayer().isDebuging()) { //The Debugging Screen
			g.drawString("[DEBUG]", 30, 30);
			g.drawString("[MapXpos] " + world.getWorldXpos(), 30, 60);
			g.drawString("[MapYpos] " + world.getWorldYpos(), 30, 90);
			g.drawString("[PlayerXpos] " + world.getPlayer().getPos().xpos, 30, 120);
			g.drawString("[PLayerYpos] " + world.getPlayer().getPos().ypos, 30, 150);
			g.drawString("[Current World Blocks] " + world.getWorldBlocks().getBlocks().size(), 30, 180);
			g.drawString("[Current Loaded World Blocks] " + world.getWorldBlocks().getLoadedBlocks().size(), 30, 210);
			
		}
		
		/**
		 * The Polygons are shaped as triangles and split the screen into 4 sections
		 * If you click in the right triangle, then you are attacking to the right
		 * and so on
		 */
		
		//g.drawImage(light, 0, 0, Main.width, Main.height, null); 2)
		
		//Draws the 4 polgons without loading them over and over again.
		g.drawPolygon(up);
		g.drawPolygon(down);
		g.drawPolygon(right);
		g.drawPolygon(left);
		
	}
	
	public static Polygon getUpPol() {
		return up;
	}
	
	public static Polygon getDownPol() {
		return down;
	}
	
	public static Polygon getRightPol() {
		return right;
	}
	
	public static Polygon getLeftPol() {
		return left;
	}

}
