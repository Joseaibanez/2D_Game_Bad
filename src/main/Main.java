package main;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		// Creación del marco en el que se situará el
		// juego con sus correspondientes opciones
		JFrame ventana = new JFrame();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setTitle("2D Cutre AF Boiiii");
		
		GamePanel gamePanel = new GamePanel();
		ventana.add(gamePanel);
		ventana.pack();
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
