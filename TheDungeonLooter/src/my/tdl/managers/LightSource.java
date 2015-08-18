package my.tdl.managers;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.project.gop.main.Vector2F;

public class LightSource {
	
	private Vector2F lightLocation = new Vector2F();
	private double lightDistance = 32 * 7;
	private double lightSize = 32;
	private Rectangle lightDetection;
	
	public LightSource(float xpos, float ypos) {
		lightLocation.xpos = xpos;
		lightLocation.ypos = ypos;
		this.lightDetection = new Rectangle();
	}
	
	public void tick() {
		this.lightDetection = new Rectangle((int) (lightLocation.xpos - lightDistance / 2 + lightSize / 2),
				(int) (lightLocation.ypos - lightDistance / 2 + lightSize / 2),
				(int) lightDistance,
				(int) lightDistance);
		
		//lightLocation.xpos += 0.01; // to move the light
	}
	
	public void render(Graphics2D g) {
		g.fillRect((int) lightLocation.getWorldLocation().xpos,
						(int) lightLocation.getWorldLocation().ypos,
						(int) lightSize,
						(int) lightSize);
		g.drawRect((int) (lightLocation.getWorldLocation().xpos - lightDistance / 2 + lightSize / 2),
				(int) (lightLocation.getWorldLocation().ypos - lightDistance / 2 + lightSize / 2),
				(int) lightDistance,
				(int) lightDistance);
	}
	
	public Vector2F getLightLocation() {
		return lightLocation;
	}
	
	public Rectangle getLightDetection() {
		return lightDetection;
	}

}
