package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
	GamePanel gp;
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(keyCode == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(keyCode == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(keyCode == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(keyCode == KeyEvent.VK_P) {
			attackPressed = true;
		}
		if(keyCode == KeyEvent.VK_SPACE) {
			if(gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			} else if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(keyCode == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(keyCode == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(keyCode == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(keyCode == KeyEvent.VK_P) {
			attackPressed = false;
		}
	}
	
}
