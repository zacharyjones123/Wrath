package my.tdl.gamestates;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.gamestate.GameState;
import my.tdl.gamestate.GameStateButton;
import my.tdl.gamestate.GameStateManager;
import my.tdl.main.Main;
import my.tdl.managers.MouseManager;

//This is where the game is always made
public class MenuState extends GameState {
	
	GameStateButton startGame;
	GameStateButton multiplayer;
	GameStateButton options;
	GameStateButton quit;
	MouseManager mm;

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mm = new MouseManager();
		startGame = new GameStateButton(Main.width / 3, 200, new DungeonLevelLoader(gsm), gsm, "StartGame");
		multiplayer = new GameStateButton(Main.width / 3, 300, new DungeonLevelLoader(gsm), gsm, "Multiplayer");
		options = new GameStateButton(Main.width / 3, 400, new DungeonLevelLoader(gsm), gsm, "Options");
		quit = new GameStateButton(Main.width / 3, 500, new QuitState(gsm), gsm, "Quit");

	}

	@Override
	public void tick(double deltaTime) {
		mm.tick();
		startGame.tick();
		multiplayer.tick();
		options.tick();
		quit.tick();
	}

	@Override
	public void render(Graphics2D g) {
		RenderedImage ri = loadImageFrom.LoadImageFrom(Main.class, "TItleScreen.png");
		AffineTransform at =  new AffineTransform();
		//g.clipRect(0, 0, Main.width, Main.height);
		g.drawRenderedImage(ri, at);
		startGame.render(g);
		multiplayer.render(g);
		options.render(g);
		quit.render(g);
		mm.render(g);
	}

}
