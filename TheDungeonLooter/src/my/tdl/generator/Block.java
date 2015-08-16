package my.tdl.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.tdl.main.Assets;

public class Block extends Rectangle {

	Vector2F pos = new Vector2F();
	private int BlockSize = 48;
	private BlockType blocktype;
	private BufferedImage block;
	private boolean isSolid;
	private boolean isAlive;
	private boolean droped = false;

	private float lightLevel = 0.5f; // 1f gives pitch black, 0.1f is more
										// transparent

	public Block(Vector2F pos, BlockType blocktype) {
		setBounds((int) pos.xpos, (int) pos.ypos, BlockSize, BlockSize);
		this.pos = pos;
		isAlive = true;
		this.blocktype = blocktype;
		init();
	}

	public Block(Vector2F pos) {
		setBounds((int) pos.xpos, (int) pos.ypos, BlockSize, BlockSize);
		this.pos = pos;
		isAlive = true;
	}

	public Block isSolid(boolean isSolid) {
		this.isSolid = isSolid;
		return this;
	}

	public void init() {
		switch (blocktype) {
		case STONE_1:
			block = Assets.getStone_1();
			break;
		case WALL_1:
			block = Assets.getWall_1();
			break;
		case TEMPLESTONE_1:
			block = Assets.getTempleStone_1();
			break;
		case STONEWINDOW_1:
			block = Assets.getStoneWindow_1();
			break;

		}
	}

	public void tick(double deltaTime) {
		if (isAlive) {
			setBounds((int) pos.xpos, (int) pos.ypos, BlockSize, BlockSize);
		}
	}

	public void render(Graphics2D g) {
		if (isAlive) {
			if (block != null) {

				g.drawImage(block, (int) pos.getWorldLocation().xpos,
						(int) pos.getWorldLocation().ypos, BlockSize,
						BlockSize, null);

				// Next three lines put a faded gray layer over all of the
				// blocks
//				System.out.println(lightLevel);
//				g.setComposite(AlphaComposite.getInstance(
//						AlphaComposite.SRC_OVER, lightLevel));
//				g.setColor(Color.BLACK);
//				g.fillRect((int) pos.getWorldLocation().xpos,
//						(int) pos.getWorldLocation().ypos, BlockSize, BlockSize);
//				g.setColor(Color.WHITE);
//				g.setComposite(AlphaComposite.getInstance(
//						AlphaComposite.SRC_OVER, 1));

			} else {
				g.fillRect((int) pos.getWorldLocation().xpos,
						(int) pos.getWorldLocation().ypos, BlockSize, BlockSize);
			}

			if (isSolid) {
				g.drawRect((int) pos.getWorldLocation().xpos,
						(int) pos.getWorldLocation().ypos, BlockSize, BlockSize);
			}
			// g.drawRect((int) pos.getWorldLocation().xpos,(int) //Debug tool
			// to count rectangles
			// pos.getWorldLocation().ypos, BlockSize, BlockSize);
			/** All for rotation */
		} else {
			if (!droped) {
				float xpos = pos.xpos + 24 - 12; // 24 is the width of the block
													// being rotated
				float ypos = pos.ypos + 24 - 12; // 24 is the height of the
													// block being rotated

				Vector2F newpos = new Vector2F(xpos, ypos);

				// World.dropBlockEntity(newpos, block);

				droped = true;
			}
		}
	}

	public enum BlockType {
		STONE_1, WALL_1, TEMPLESTONE_1, STONEWINDOW_1
	}

	public boolean isSolid() {
		return isSolid;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Vector2F getBlockLocation() {
		return pos;
	}

	public void addShadow(float amount) {
		if (lightLevel != 1) {
			if (lightLevel < 9.001000) {
				lightLevel += amount;
			}
		}
	}

	public void removeShadow(float amount) {
		if (lightLevel > 0.001000) {
			lightLevel -= amount;
		}
	}
}
