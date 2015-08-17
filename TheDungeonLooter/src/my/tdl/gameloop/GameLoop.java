package my.tdl.gameloop;

import java.awt.Component;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.Vector2F;
import my.tdl.gamestate.GameStateManager;
import my.tdl.main.Assets;

public class GameLoop extends IDGameLoop {
	
	GameStateManager gsm;
	public static Assets assets = new Assets();
	
	public GameLoop(int fwidth, int fheight) {
		super(fwidth, fheight);
	}
	
	@Override
	public void init() {
		assets.init(); // gives all of the tilemaps and art
		gsm = new GameStateManager();
		gsm.init();
		super.init();
	}
	
	@Override
	public void tick(double deltaTime) {
		gsm.tick(deltaTime);
	}
	
	@Override
	public void render() {
		super.render();
		gsm.render(graphics2D);
		clear();
	}
	
	@Override
	public void clear() {
		super.clear();
	}
}
