package my.tdl.gamestates;

import java.awt.Graphics2D;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import my.tdl.gamestate.GameState;
import my.tdl.gamestate.GameStateButton;
import my.tdl.gamestate.GameStateManager;
import my.tdl.main.Main;
import my.tdl.managers.MouseManager;

//This is where the game is always made
public class QuitState extends GameState {
	
	GameStateButton yes;
	GameStateButton no;

	MouseManager mm;

	public QuitState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mm = new MouseManager();
		yes = new GameStateButton(Main.width / 3, 200, "Yes");
		no = new GameStateButton(Main.width / 3 + 200, 200, "No");

	}

	@Override
	public void tick(double deltaTime) {
		mm.tick();
		yes.tick();
		no.tick();
		
		if (yes.isHeldOver()) {
			if (yes.isPressed()) {
				System.exit(1);
			}
		}
		
		if (no.isHeldOver()) {
			if (no.isPressed()) {
				gsm.states.push(new MenuState(gsm));
				gsm.states.peek().init();
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		yes.render(g);
		no.render(g);
		mm.render(g);
		g.clipRect(0, 0, Main.width, Main.height);
	}

}
