package my.tdl.managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import my.tdl.generator.World;
import my.tdl.main.Assets;

public class MouseManager implements MouseListener, MouseMotionListener,
		MouseWheelListener {

	private static int mouseMovedX, mouseMovedY;
	public static Point mouse;

	public static boolean pressed;

	public void tick() {
		mouse = new Point(mouseMovedX, mouseMovedY);

		if (World.getPlayer() != null) {
			if (World.getPlayer().getPlayerActions().hasCompleted()) {
				if (!World.getPlayer().getPlayerActions().attacked()) {

					/* UP */
					if (HUDManager.getUpPol() != null) {
						if (HUDManager.getUpPol().contains(mouse)) {
							if (pressed) {
								World.getPlayer().getPlayerActions().attackUP();
								pressed = false;
							}
						}
					}

					/* DOWN */
					if (HUDManager.getDownPol() != null) {
						if (HUDManager.getDownPol().contains(mouse)) {
							if (pressed) {
								World.getPlayer().getPlayerActions()
										.attackDOWN();
								pressed = false;
							}
						}
					}

					/* RIGHT */
					if (HUDManager.getRightPol() != null) {
						if (HUDManager.getRightPol().contains(mouse)) {
							if (pressed) {
								World.getPlayer().getPlayerActions()
										.attackRIGHT();
								pressed = false;
							}
						}
					}

					/* LEFT */
					if (HUDManager.getLeftPol() != null) {
						if (HUDManager.getLeftPol().contains(mouse)) {
							if (pressed) {
								World.getPlayer().getPlayerActions()
										.attackLEFT();
								pressed = false;
							}
						}
					}
				}
			}
		}
	}

	public void render(Graphics2D g) {
		// g.fillRect(mouseMovedX, mouseMovedY, 4, 4); //for little white square
		// on point

		if (pressed) {
			g.drawImage(Assets.getMouse_pressed(), mouseMovedX, mouseMovedY,
					32, 32, null);
		} else {
			g.drawImage(Assets.getMouse_unpressed(), mouseMovedX, mouseMovedY,
					32, 32, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			pressed = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			pressed = false;
		}
	}

}
