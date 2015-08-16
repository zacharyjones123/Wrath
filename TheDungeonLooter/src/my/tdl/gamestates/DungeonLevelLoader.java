package my.tdl.gamestates;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.MoveableObjects.Player;
import my.tdl.gamestate.GameState;
import my.tdl.gamestate.GameStateManager;
import my.tdl.generator.World;
import my.tdl.main.Main;

//This is where the game is always made
public class DungeonLevelLoader extends GameState {
	
	public static World world;
	private String worldName;
	private String map_name;
	
	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}
	
	public DungeonLevelLoader(GameStateManager gsm, String worldName, String map_name) {
		super(gsm);
		this.worldName = worldName;
		this.map_name = map_name;
	}

	@Override
	public void init() {
		
		if (worldName == null) {
			worldName = "NULL";
			map_name = "Wrath"; //default map
		}
		world = new World(worldName, gsm);
		world.setSize(100,100); //set size of world
		world.setWorldSpawn(10, 49 );
		
		world.addPlayer(new Player());
		
		world.init();
		world.generate(map_name); //Name of the map your using\
	}

	@Override
	public void tick(double deltaTime) {
		if (world.hasGenerated()) {
			world.tick(deltaTime);
		}
	}

	@Override
	public void render(Graphics2D g) {
		RenderedImage ri = loadImageFrom.LoadImageFrom(Main.class, "background.jpg");
		AffineTransform at =  new AffineTransform();
		g.drawRenderedImage(ri, at);
		if (world.hasGenerated()) {
			world.render(g);
		}
		//g.clipRect(0, 0, Main.width, Main.height);
	}

}
