//Clayton Warstler
//Progamming Paradigms Assignment 5
//10/27/2022

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model;
	View view;
	Controller controller;
	Pipe pipe;

	public Game() {
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);

		this.setTitle("A5 - Goombas Attack!");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);

		Json loadObject = Json.load("pipeMap.json");
		if(loadObject == null)
			System.exit(0);

		model.unmarshal(loadObject);
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.run();
	}

	public void run() {
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); //Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); //Updates screen

			try {
				Thread.sleep(40);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}