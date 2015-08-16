package my.tdl.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {
	
	SpriteSheet blocks = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();
	public static SpriteSheet players = new SpriteSheet(); //TODO: mine
	public static SpriteSheet daro = new SpriteSheet(); //TODO: mine
	
	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	public static BufferedImage templeStone_1;
	public static BufferedImage stoneWindow_1;
	
	public static BufferedImage mouse_pressed;
	public static BufferedImage mouse_unpressed;
	
	private static BufferedImage button_heldover;
	private static BufferedImage button_notover;
	
	//ATTACK STATES
	private static BufferedImage up_attack;
	private static BufferedImage down_attack;
	private static BufferedImage right_attack;
	private static BufferedImage left_attack;
	
	
	public void init() {
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "playersheet.png"));
		players.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "DAROspritesheet.png")); //TODO: mine
		daro.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "DAROdaro.png")); //TODO: mine
	
		stone_1 = blocks.getTile(0, 0, 16, 16);
		wall_1 =  blocks.getTile(16, 0, 16, 16);
		templeStone_1 = blocks.getTile(16, 32, 16, 16);
		stoneWindow_1 = blocks.getTile(32, 16, 16, 16);
		
		mouse_pressed = daro.getTile(128, 0, 8, 8);
		mouse_unpressed = daro.getTile(136, 0, 8, 8);
		
		button_heldover = daro.getTile(128, 8, 48, 16);
		button_notover = daro.getTile(128, 24, 48, 16);
		
		up_attack = blocks.getTile(0, 0, 16, 16);
		down_attack = blocks.getTile(0, 0, 16, 16);
		right_attack = blocks.getTile(0, 0, 16, 16);
		left_attack = blocks.getTile(0, 0, 16, 16);
		
		
		
		
	}
	
	public static BufferedImage getStone_1() {
		return stone_1;
	}
	
	public static BufferedImage getWall_1() {
		return wall_1;
	}
	
	public static BufferedImage getTempleStone_1() {
		return templeStone_1;
	}
	
	public static BufferedImage getStoneWindow_1() {
		return stoneWindow_1;
	}
	
	public static BufferedImage getMouse_pressed() {
		return mouse_pressed;
	}
	
	public static BufferedImage getMouse_unpressed() {
		return mouse_unpressed;
	}
	
	public static BufferedImage getButton_heldover() {
		return button_heldover;
	}
	
	public static BufferedImage getButton_notover() {
		return button_notover;
	}
	
	public static BufferedImage getUp_attack() {
		return up_attack;
	}
	
	public static BufferedImage getDown_attack() {
		return down_attack;
	}
	
	public static BufferedImage getRight_attack() {
		return right_attack;
	}
	
	public static BufferedImage getLeft_attack() {
		return left_attack;
	}
}
