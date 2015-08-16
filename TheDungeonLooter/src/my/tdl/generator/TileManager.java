package my.tdl.generator;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Light;
import my.project.gop.main.Vector2F;
import my.tdl.MoveableObjects.Player;
import my.tdl.managers.LightManager;

public class TileManager {

	public static CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<Block>();
	public static CopyOnWriteArrayList<Block> load_blocks = new CopyOnWriteArrayList<Block>();
	
	private World world;
	private LightManager lm;

	public TileManager(World world) {
		this.world = world;
		//lm = new LightManager(load_blocks);
		//lm.init();
	}

	public void tick(double deltaTime) {
		for (Block block : blocks) {
			block.tick(deltaTime);

			if (Player.render.intersects(block)) {
				block.setAlive(true);

				if (!load_blocks.contains(block)) {
					load_blocks.add(block);
				}

			} else {
				if (load_blocks.contains(block)) {
					load_blocks.remove(block);
				}
				block.setAlive(false);
			}

			// block.pos.add(new Vector2F(1,0)); //makes the blocks move
			//lm.tick();
		}
		
		if (!world.getPlayer().isDebuging()) {

			if (!load_blocks.isEmpty()) {
				load_blocks.clear();
			}
		}
	}

	public void render(Graphics2D g) {
		for (Block block : blocks) {
			block.render(g);
		}
		
		//lm.render(g);
	}

	public CopyOnWriteArrayList<Block> getBlocks() {
		return blocks;
	}

	public CopyOnWriteArrayList<Block> getLoadedBlocks() {
		return load_blocks;
	}

}
