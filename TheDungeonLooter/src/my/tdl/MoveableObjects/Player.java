package my.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.gamestate.GameStateButton;
import my.tdl.gamestates.DungeonLevelLoader;
import my.tdl.generator.World;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUIManager;
import my.tdl.managers.HUDManager;
import my.tdl.managers.MouseManager;

public class Player implements KeyListener {

	Vector2F pos;
	private World world;
	private int width = 32 * 2; // player smaller then blocks (48)
	private int height = 32 * 2;
	private int scale = 2;
	private static boolean up, down, left, right, running;
	private static boolean debug = false; // can be static since only one player
	private float maxSpeed = 3 * 32F;

	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;

	private float slowdown = 4.093F;

	private float fixDt = 1 / 60F; // timeStep
	private long animationSpeed = 180;

	private static boolean moving;
	private static boolean spawned;

	MouseManager playerMM = new MouseManager();

	/*
	 * Rendering
	 *TODO: Need to make automatic for different monitors 
	 */
	private int renderDistanceW = 60; //60
	private int renderDistanceH = 35; //35
	public static Rectangle render;

	// TODO
	private int animationState = 0;

	/*
	 * 0 = up 1 = down 2 = right 3 = left 4 = idle
	 */

	private ArrayList<BufferedImage> listUp;
	Animator ani_up;
	private ArrayList<BufferedImage> listDown;
	Animator ani_down;
	private ArrayList<BufferedImage> listLeft;
	Animator ani_left;
	private ArrayList<BufferedImage> listRight;
	Animator ani_right;

	private ArrayList<BufferedImage> listIdle;
	Animator ani_idle;

	private HUDManager hudm;
	private GUIManager guim;
	private PlayerActions playerActions;

	public Player() {
		//pos = new Vector2F(Main.width / 2 - width / 2, Main.height / 2 - height / 2); // exactly in center
		//pos = new Vector2F(0, 0); //top left of the screen
		//pos = new Vector2F(Main.width, Main.height); //Bottom right of the screen
		pos = new Vector2F(200, 600);
		// This effects where the character is placed on the screen at first
		// If you change the Vector 2F, you change where the player is on the screen
	}

	public void init(World world) {
		playerActions = new PlayerActions(world);
		hudm = new HUDManager(world);
		guim = new GUIManager();
		this.world = world;

		render = new Rectangle((int) (pos.xpos - pos.getWorldLocation().xpos
				+ pos.xpos - renderDistanceW * 32 / 2 + width / 2 + 700),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos
						- renderDistanceH * 32 / 2 + height / 2 - 100),
				renderDistanceW * 32, renderDistanceH * 32); // Changes what is
																// rendered on
																// the screen

		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listIdle = new ArrayList<BufferedImage>();

		listUp.add(Assets.player.getTile(0, 0, 16, 16));
		listUp.add(Assets.player.getTile(16, 0, 16, 16));

		listDown.add(Assets.player.getTile(0, 16, 16, 16));
		listDown.add(Assets.player.getTile(16, 16, 16, 16));

		// DARO
		listRight.add(Assets.daro.getTile(192, 64, 64, 64));
		listRight.add(Assets.daro.getTile(320, 64, 64, 64));
		listRight.add(Assets.daro.getTile(256, 64, 64, 64));

		// INDIE DEVELOPER
		// listRight.add(Assets.player.getTile(32, 16, 16, 16));
		// listRight.add(Assets.player.getTile(48, 16, 16, 16));
		// listRight.add(Assets.player.getTile(64, 16, 16, 16));
		// listRight.add(Assets.player.getTile(80, 16, 16, 16));

		// DARO
		listLeft.add(Assets.daro.getTile(128, 192, 64, 64));
		listLeft.add(Assets.daro.getTile(256, 192, 64, 64));
		listLeft.add(Assets.daro.getTile(192, 192, 64, 64));

		// INDIE DEVELOPER
		// listLeft.add(Assets.player.getTile(32, 0, 16, 16));
		// listLeft.add(Assets.player.getTile(48, 0, 16, 16));
		// listLeft.add(Assets.player.getTile(64, 0, 16, 16));
		// listLeft.add(Assets.player.getTile(80, 0, 16, 16));

		// DARO
		listIdle.add(Assets.daro.getTile(0, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(192, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(256, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(64, 128, 64, 64));
		// INDIE DEVELOPER
		// listIdle.add(Assets.player.getTile(0, 32, 16, 16));
		// listIdle.add(Assets.player.getTile(16, 32, 16, 16));
		// listIdle.add(Assets.player.getTile(32, 32, 16, 16));
		// listIdle.add(Assets.player.getTile(48, 32, 16, 16));

		// UP
		ani_up = new Animator(listUp);
		ani_up.setSpeed(animationSpeed); // 180ms, 1000 is 1 sec.
		ani_up.play();

		// DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(animationSpeed);
		ani_down.play();

		// RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(animationSpeed);
		ani_right.play();

		// LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(animationSpeed);
		ani_left.play();

		// IDLE
		ani_idle = new Animator(listIdle);
		ani_idle.setSpeed(animationSpeed);
		ani_idle.play();

		ani_up.setSpeed(animationSpeed); // 180ms, 1000 is 1 sec.
		ani_down.setSpeed(animationSpeed);
		ani_right.setSpeed(animationSpeed);
		ani_left.setSpeed(animationSpeed);
		ani_idle.setSpeed(animationSpeed);

		spawned = true;
	}

	public void tick(double deltaTime) {

		playerMM.tick();
		playerActions.tick();
		

		render = new Rectangle((int) (pos.xpos - pos.getWorldLocation().xpos
				+ pos.xpos - renderDistanceW * 32 / 2 + width / 2 + 700),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos
						- renderDistanceH * 32 / 2 + height / 2 - 100),
				renderDistanceW * 32, renderDistanceH * 32); // Changes what is
																// rendered on
																// the screen

		// 1 pixel per deltaTime
		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);
		// kind of a glidish kind of movement
		// To make the make move
		if (up) {
			moveMapUp(moveAmountu);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountu);
		}

		if (down) {
			moveMapDown(moveAmountd);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountd);
		}

		if (right) {
			moveMapRight(moveAmountr);
			animationState = 2;
		} else {
			moveMapRightGlide(moveAmountr);
		}

		if (left) {
			moveMapLeft(moveAmountl);
			animationState = 3;
		} else {
			moveMapLeftGlide(moveAmountl);
		}

		if (!up && !down && !right && !left) {
			/** standing still */
			animationState = 4;
			if (moving) {
				moving = false;
			}
		}

		if (running) {
			if (animationSpeed != 100) {
				animationSpeed = 100;
				ani_up.setSpeed(animationSpeed); // 180ms, 1000 is 1 sec.
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed += 100;
			}
		} else {
			if (animationSpeed != 180) {
				animationSpeed = 180;
				ani_up.setSpeed(animationSpeed); // 180ms, 1000 is 1 sec.
				ani_down.setSpeed(animationSpeed);
				ani_right.setSpeed(animationSpeed);
				ani_left.setSpeed(animationSpeed);
				ani_idle.setSpeed(animationSpeed);
				maxSpeed -= 100;
			}
		}
	}

	public void PlayerMoveCode(float speed) {
		if (up) {
			moveMapUp(speed);
		} else {
			moveMapUpGlide(speed);
		}

		if (down) {
			moveMapDown(speed);
		} else {
			moveMapDownGlide(speed);
		}

		if (left) {
			moveMapLeft(speed);
		} else {
			moveMapLeftGlide(speed);
		}

		if (right) {
			moveMapRight(speed);
		} else {
			moveMapRightGlide(speed);
		}
	}

	public void moveMapUp(float speed) {

		// the two points are
		// p1 = bottom right
		// p2 = bottom right, plus width of player
		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos), // Up
				(int) (pos.ypos + world.map_pos.ypos - speed)), new Point(
				(int) (pos.xpos + world.map_pos.xpos + width), (int) (pos.ypos
						+ world.map_pos.ypos - speed)))) {

			if (speedUp < maxSpeed) { // Speed up player
				speedUp += slowdown;
			} else {
				speedUp = maxSpeed;
			}
			world.map_pos.ypos -= speed; // To move the map
			// pos.ypos -= speed; //To move the player
		} else {
			speedUp = 0;
		}

	}

	public void moveMapUpGlide(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos), (int) (pos.ypos
				+ world.map_pos.ypos - speed)), new Point((int) (pos.xpos
				+ world.map_pos.xpos + width), (int) (pos.ypos
				+ world.map_pos.ypos - speed)))) {
			if (speedUp != 0) {
				speedUp -= slowdown; // Slow Down player

				if (speedUp < 0) { // Will eventually stop if keep slowing down
					speedUp = 0;
				}
			}
			world.map_pos.ypos -= speed; // To move the map
			// pos.ypos -= speed; //To move the player

		} else {
			speedUp = 0;
		}

	}

	public void moveMapDown(float speed) {

		if (!Check
				.CollisionPlayerBlock(

						new Point(
								(int) (pos.xpos + world.map_pos.xpos), // Up
								(int) (pos.ypos + world.map_pos.ypos + height + speed)),
						new Point(
								(int) (pos.xpos + world.map_pos.xpos + width),
								(int) (pos.ypos + world.map_pos.ypos + height + speed)))) {

			if (speedDown < maxSpeed) {
				speedDown += slowdown;
			} else {
				speedDown = maxSpeed;
			}
			world.map_pos.ypos += speed; // To move the map
			// pos.ypos += speed; //To move the player
		} else {
			speedDown = 0;
		}

	}

	public void moveMapDownGlide(float speed) {

		if (!Check
				.CollisionPlayerBlock(

						new Point(
								(int) (pos.xpos + world.map_pos.xpos), // Up
								(int) (pos.ypos + world.map_pos.ypos + height + speed)),
						new Point(
								(int) (pos.xpos + world.map_pos.xpos + width),
								(int) (pos.ypos + world.map_pos.ypos + height + speed)))) {

			if (speedDown != 0) {
				speedDown -= slowdown; // Slow Down player

				if (speedDown < 0) { // Will eventually stop
					speedDown = 0;
				}
			}
			world.map_pos.ypos += speed; // To move the map
			// pos.ypos += speed; //To move the player
		} else {
			speedDown = 0;
		}

	}

	public void moveMapRight(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos + width + speed), // Up
				(int) (pos.ypos + world.map_pos.ypos)), new Point(
				(int) (pos.xpos + world.map_pos.xpos + width + speed),
				(int) (pos.ypos + world.map_pos.ypos + height)))) {
			if (speedRight < maxSpeed) {
				speedRight += slowdown;
			} else {
				speedRight = maxSpeed;
			}
			world.map_pos.xpos += speed; // To move the map
			// pos.xpos += speed; //To move the player
		} else {
			speedRight = 0;
		}

	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos + width + speed), // Up
				(int) (pos.ypos + world.map_pos.ypos)), new Point(
				(int) (pos.xpos + world.map_pos.xpos + width + speed),
				(int) (pos.ypos + world.map_pos.ypos + height)))) {
			if (speedRight != 0) {
				speedRight -= slowdown; // Slow Down player

				if (speedRight < 0) { // Will eventually stop
					speedRight = 0;
				}
			}
			world.map_pos.xpos += speed; // To move the map
			// pos.xpos += speed; //To move the player
		} else {
			speedRight = 0;
		}

	}

	public void moveMapLeft(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos - speed), // Up
				(int) (pos.ypos + world.map_pos.ypos + height)), new Point(
				(int) (pos.xpos + world.map_pos.xpos - speed),
				(int) (pos.ypos + world.map_pos.ypos)))) {
			if (speedLeft < maxSpeed) {
				speedLeft += slowdown;
			} else {
				speedLeft = maxSpeed;
			}
			world.map_pos.xpos -= speed; // To move the map
			// pos.xpos -= speed; //To move the player
		} else {
			speedLeft = 0;
		}

	}

	public void moveMapLeftGlide(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + world.map_pos.xpos - speed), // Up
				(int) (pos.ypos + world.map_pos.ypos + height)), new Point(
				(int) (pos.xpos + world.map_pos.xpos - speed),
				(int) (pos.ypos + world.map_pos.ypos)))) {

			if (speedLeft != 0) {
				speedLeft -= slowdown; // Slow Down player

				if (speedLeft < 0) { // Will eventually stop
					speedLeft = 0;
				}
			}
			world.map_pos.xpos -= speed; // To move the map
			// pos.xpos -= speed; //To move the player
		} else {
			speedLeft = 0;
		}

	}

	public void render(Graphics2D g) {
		 g.fillRect((int) pos.xpos, (int) pos.ypos, width, height); //gives
		// the white square for debugging

		// To make the movie look
		 g.clearRect(0, 0, Main.width, Main.height / 6); //Makes a black box
		 //given the parameters
		 g.clearRect(0, Main.height - Main.height / 6, Main.width, Main.height
		 / 6);
		
		if (playerActions.attack_state != null) {
			if (!playerActions.hasCompleted) {
				if (playerActions.attack) {

					/* UP ATTACK */
					if (playerActions.getAttack_state() == Assets.getUp_attack()) {

						g.drawImage(playerActions.attack_state, (int) pos.xpos
								- width / 2, (int) pos.ypos - height - 16  , width
								* scale, height * scale, null);
					} 
					
					/* DOWN ATTACK */
					if (playerActions.getAttack_state() == Assets.getDown_attack()) {

						g.drawImage(playerActions.attack_state, (int) pos.xpos
								- width / 2, (int) pos.ypos - height + 16  , width
								* scale, height * scale, null);
					} 
					
					/* RIGHT ATTACK */
					if (playerActions.getAttack_state() == Assets.getRight_attack()) {

						g.drawImage(playerActions.attack_state, (int) pos.xpos
								- width / 2 + 16, (int) pos.ypos - height  , width
								* scale, height * scale, null);
					} 
					
					/* LEFT ATTACK */
					if (playerActions.getAttack_state() == Assets.getLeft_attack()) {

						g.drawImage(playerActions.attack_state, (int) pos.xpos
								- width / 2 - 16, (int) pos.ypos - height  , width
								* scale, height * scale, null);
					} 
					
				}
			}
		}
		

		if (animationState == 0) {
			// UP
			g.drawImage(ani_up.sprite, (int) pos.xpos - width / 2,
					(int) pos.ypos - height, width * scale, height * scale,
					null);
			if (up) {
				ani_up.update(System.currentTimeMillis());
			}
		}
		if (animationState == 1) {
			// DOWN
			g.drawImage(ani_down.sprite, (int) pos.xpos - width / 2,
					(int) pos.ypos - height, width * scale, height * scale,
					null);
			if (down) {
				ani_down.update(System.currentTimeMillis());
			}
		}
		if (animationState == 2) {
			// RIGHT
			g.drawImage(ani_right.sprite, (int) pos.xpos - width / 2,
					(int) pos.ypos - height, width * scale, height * scale,
					null);
			if (right) {
				ani_right.update(System.currentTimeMillis());
			}
		}
		if (animationState == 3) {
			// LEFT
			g.drawImage(ani_left.sprite, (int) pos.xpos - width / 2,
					(int) pos.ypos - height, width * scale, height * scale,
					null);
			if (left) {
				ani_left.update(System.currentTimeMillis());
			}
		}
		if (animationState == 4) {
			// IDLE
			g.drawImage(ani_idle.sprite, (int) pos.xpos - width / 2,
					(int) pos.ypos - height, width * scale, height * scale,
					null);
			ani_idle.update(System.currentTimeMillis());
		}

		// the timing for the strings
		//g.drawString(playerActions.getAttackTime() + "", 200, 500);
		//g.drawString(playerActions.hasCompleted() + "", 200, 550);
		//g.drawString(playerActions.attacked() + "", 200, 600);

		// The white rectangle around the rendering
		 g.drawRect((int) pos.xpos - renderDistanceW * 32 / 2 + width / 2 + 700,
		 (int) pos.ypos - renderDistanceH * 32 / 2 + height / 2 - 100,
		 renderDistanceW * 32, renderDistanceH * 32);
		 
		hudm.render(g);
		guim.render(g);
		playerMM.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			if (!moving) {
				moving = true;
			}
			up = true;
		}
		if (key == KeyEvent.VK_A) {
			if (!moving) {
				moving = true;
			}
			left = true;
		}
		if (key == KeyEvent.VK_D) {
			if (!moving) {
				moving = true;
			}
			right = true;
		}
		if (key == KeyEvent.VK_S) {
			if (!moving) {
				moving = true;
			}
			down = true;
		}
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		if (key == KeyEvent.VK_Z) {
			running = true;
		}
		if (key == KeyEvent.VK_F3) {
			if (!debug) {
				debug = true;
			} else {
				debug = false;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == KeyEvent.VK_A) {
			left = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
		}
		if (key == KeyEvent.VK_Z) {
			running = false;
		}
		if (key == KeyEvent.VK_P) {
			DungeonLevelLoader.world.changeToWorld("world2", "DAROmap2");
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** --------------------GETTERS------------------- */

	public Vector2F getPos() {
		return pos;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getSlowdown() {
		return slowdown;
	}

	public World getWorld() {
		return world;
	}

	public boolean isDebuging() {
		return debug;
	}

	public boolean isMoving() {
		return moving;
	}

	public boolean hasSpawned() {
		return spawned;
	}

	public PlayerActions getPlayerActions() {
		return playerActions;
	}

	// PLAYER ACTIONS -------------------------
	public static class PlayerActions {

		private World world;
		private BufferedImage attack_state;
		private static boolean hasCompleted = true;
		private static boolean attack = false;
		private double attackTime = 1;

		public PlayerActions(World world) {
			this.world = world;
		}

		public void tick() {
			if (!hasCompleted) {
				if (attack) {
					if (attack_state != null) {
						startAttack();
					}
				}
			}
		}

		private void startAttack() {

			if (attackTime != 0) {
				attackTime -= 0.1;
			}

			if (attackTime <= 0) {
				attack = false;
				hasCompleted = true;
				attack_state = null;
				attackTime = 1;
			}

		}

		public void attackUP() {
			attack_state = Assets.getUp_attack();
			System.out.println("kdjkfj");
			attack = true;
			hasCompleted = false;

		}

		public void attackDOWN() {
			attack_state = Assets.getDown_attack();
			attack = true;
			hasCompleted = false;
		}

		public void attackRIGHT() {
			attack_state = Assets.getRight_attack();
			attack = true;
			hasCompleted = false;
		}

		public void attackLEFT() {
			attack_state = Assets.getLeft_attack();
			attack = true;
			hasCompleted = false;
		}

		public void run() {

		}

		public boolean hasCompleted() {
			return hasCompleted;

		}

		public boolean attacked() {
			return attack;
		}

		public BufferedImage getAttack_state() {
			return attack_state;
		}

		public double getAttackTime() {
			return attackTime;
		}

	}

}
