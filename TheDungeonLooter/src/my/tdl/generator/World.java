package my.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.loadImageFrom;
import my.project.gop.main.Vector2F;
import my.tdl.MoveableObjects.Player;
import my.tdl.gamestate.GameStateManager;
import my.tdl.gamestates.DungeonLevelLoader;
import my.tdl.generator.Block.BlockType;
import my.tdl.main.Main;

public class World {

	public static Vector2F map_pos = new Vector2F();
	private String worldName;
	private BufferedImage map;
	private int world_width;
	private int world_height;
	private int blockSize = 48;
	private static Player player;
	private boolean hasGenerated;
	
	//LISTS
	private CopyOnWriteArrayList<BlockEntity> blockents;
	public TileManager tiles;
	
	//World Spawm
	private Block spawn;

	// BOOLEANS
	private boolean hasSize = false;
	private GameStateManager gsm;

	public World(String worldName, GameStateManager gsm) {
		this.worldName = worldName;
		this.gsm = gsm;
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
	}

	public void init() {
		blockents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager(this);
		
		map_pos.xpos = spawn.getBlockLocation().xpos - player.getPos().xpos;
		map_pos.ypos = spawn.getBlockLocation().ypos - player.getPos().ypos;
		
		if (player != null) {
			player.init(this);
		}
	}

	public void tick(double deltaTime) {
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
		
		if (!player.hasSpawned()) {
			  spawn.tick(deltaTime);
		}
		
		tiles.tick(deltaTime);
		
		if(!blockents.isEmpty()) {
			for (BlockEntity ent : blockents) {
				if (player.render.intersects(ent)) {
					ent.tick(deltaTime);
					ent.setAlive(true);
				} else {
					ent.setAlive(false);
				}
			}
		}
		
		if (player != null) {
			player.tick(deltaTime);
		}  
	}

	public void render(Graphics2D g) {
		tiles.render(g);
		
		if (!player.hasSpawned()) {
			spawn.render(g);
		}
		
		for (BlockEntity ent : blockents) {
			if (player.render.intersects(ent)) {
				ent.render(g);
				ent.setAlive(true);
			} else {
				ent.setAlive(false);
			}
		}
		
		if (player != null) {
			player.render(g);
		}
	}

	public void generate(String world_image_name) {

		map = null;

		if (hasSize) {

			try {
				map = loadImageFrom.LoadImageFrom(Main.class, world_image_name
						+ ".png");
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int x = 0; x < world_width; x++) {
				for (int y = 0; y < world_height; y++) {
					int col = map.getRGB(x, y);
					
					switch (col & 0xFFFFFF) {
					case 0x505050: // GRAY
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.STONE_1));
						break;
					case 0x404040: // GRAY
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.WALL_1).isSolid(false));
						break;
					case 0xFFD800: // YELLOW
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.WALL_1).isSolid(true));
						break;
					case 0x000000: // BLACK
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.STONE_1));
						break;
					case 0x7F3300: // LIGHT BROWN
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.TEMPLESTONE_1));
						break;
					case 0x1B5F7C: //DARK BLUE
						tiles.blocks.add(new Block(new Vector2F(x * 48, y * 48),
								BlockType.STONEWINDOW_1));
					}
				}
			}
		}
		hasGenerated =  true;
	}

	public void setSize(int world_width, int world_height) {
		this.world_width = world_width;
		this.world_height = world_height;
		hasSize = true;
	}
		
		
		
	public Vector2F getWorldPos() {
		return map_pos;
	}
	
	public float getWorldXpos() {
		return map_pos.xpos;
	}
	
	public float getWorldYpos() {
		return map_pos.ypos;
	}
		
		

	public void addPlayer(Player player) {
		this.player = player;
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage block_image) {
		BlockEntity ent = new BlockEntity(pos, block_image);
		if (!blockents.contains(ent)) {
			blockents.add(ent);
		}
	}
	
	public void setWorldSpawn(float xpos, float ypos) {
		if (xpos < world_width) {
			if (ypos < world_height) {
				Block spawn = new Block(new Vector2F(xpos*blockSize, ypos*blockSize));
				this.spawn = spawn;
			}
		}
	}
	
	public Vector2F getWorldSpawn() {
		return spawn.pos;
	}
	
	public void removeDropedBlockEntity(BlockEntity blockEntity) {
		if (blockents.contains(blockEntity)) {
			blockents.remove(blockEntity);
		}
	}
	
	public TileManager getWorldBlocks() {
		return tiles;
		
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public boolean hasGenerated() {
		return hasGenerated;
	}
	
	public void resetWorld() {
		tiles.getBlocks().clear();
		tiles.getLoadedBlocks().clear();
		blockents.clear();
		spawn = null;
		
	}
	
	public void changeToWorld(String wn, String mn) {
		if (wn != worldName) {
		 resetWorld();
		gsm.states.push(new DungeonLevelLoader(gsm, wn, mn));
		gsm.states.peek().init();
		System.out.println("CHANGED TO WORLD: " + wn + "");
		} else {
			System.err.println("You Are Already In That World!");
		}
	}

}
