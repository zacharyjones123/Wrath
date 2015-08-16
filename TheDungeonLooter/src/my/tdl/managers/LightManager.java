package my.tdl.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.generator.Block;

public class LightManager {

	private ArrayList<LightSource> lights;
	private CopyOnWriteArrayList<Block> load_blocks;

	public LightManager(CopyOnWriteArrayList<Block> load_blocks) {
		this.load_blocks = load_blocks;
		lights = new ArrayList<LightSource>();

	}
	
	public void init() {
		lights.add(new LightSource(200, 200));
		lights.add(new LightSource(300, 900));
	}

	public void tick() {
		for (LightSource light : lights) {
			light.tick();
			for (Block blocks : load_blocks) {
				float distance = (float) blocks.getBlockLocation().getDistanceBetweenWorldVectors(light.getLightLocation());
				
				if (blocks.intersects(light.getLightDetection())) {
					blocks.removeShadow(0.01f); //speed of light
				} else {
					blocks.addShadow(0.01f); //speed of light
				}
			}
		}

	}

	public void render(Graphics2D g) {
		for (LightSource light : lights) {
			light.render(g);
		}
	}

}
