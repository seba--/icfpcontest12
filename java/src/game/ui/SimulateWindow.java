package game.ui;

import game.Command;
import game.State;
import game.ai.Fitness;
import game.stepper.SingleStepper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 * @author christoph
 */

public class SimulateWindow extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public LinkedList<State> states;
	public JTextArea textArea;
	public int current = 0;
	public boolean playing = false;
	public Player player;
	public SingleStepper stepper;
	public Fitness fitness;
	public State custom;

	public class Player extends Thread {
		private int delta;
		private long last = 0;
		public boolean stopped = false;
				
		public Player(int time) {
			super();
			this.delta = time;
		}
		
		public void run() {
			if(last == 0) {
				last = System.currentTimeMillis();
			}
			while(!stopped && current < states.size()-1) {
				if(last + delta < System.currentTimeMillis()) {
					updateText(states.get(++current));
					last = System.currentTimeMillis();
				}
			}
		}
		
	}
	
	public SimulateWindow(Fitness fitness, SingleStepper stepper) {		
	  super("Simulation");
	  player = this.new Player(200);
	  custom = null;
	  this.fitness = fitness;
	  this.stepper = stepper;
	  
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.setSize(300, 400);
	  
	  JToolBar tbar = new JToolBar();
	  
	  final JButton buttonBack = new JButton("Back");
	  buttonBack.setEnabled(false);
	  tbar.add(buttonBack);
	  
	  final JButton buttonNext = new JButton("Next");
	  tbar.add(buttonNext);
	  final JButton buttonPlay = new JButton("Play"); 
//	  tbar.add(buttonPlay);
	    
	  buttonPlay.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			playing = !playing;
			if(playing) {
				buttonPlay.setText("Pause");
				player.stopped = false;
				player.start();
			} else {
				buttonPlay.setText("Play");
				player.stopped = true;
			}
			
		}
		  
	  });
	  
	  buttonBack.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateText(states.get(--current));
			if(current == 0) {
				buttonBack.setEnabled(false);		
			}
			buttonNext.setEnabled(true);
		}
			  
	  });

	  buttonNext.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateText(states.get(++current));
			if(current == (states.size()-1)) {
				buttonNext.setEnabled(false);		
			}
			buttonBack.setEnabled(true);
		}
		  
	  });
	  
	  textArea = new JTextArea();
	  textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
	  textArea.setEditable(false);
	  textArea.addKeyListener(this);
	  
	  JPanel jplContentPane = new JPanel();
	  jplContentPane.setLayout(new BorderLayout());
	  jplContentPane.setPreferredSize(new Dimension(400, 100));
	  jplContentPane.add(tbar, BorderLayout.NORTH);
	  jplContentPane.add(textArea, BorderLayout.CENTER);
  	  setContentPane(jplContentPane);  

  	  states = new LinkedList<State>();
	}
	
	public void addState(State state) {
		states.add(state);
		if(states.size() == 1) {
			updateText(state);
		}
	}
	
	public void updateText(State state) {
	  textArea.setText("fitness: " + state.fitness + "\n" + state.toString());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		State parent;
		if(custom == null) {
			parent = states.get(current);
		} else {
			parent = custom; 
		}
		switch(arg0.getKeyCode()) {
			case KeyEvent.VK_UP:
				custom = stepper.step(parent, Command.Up);
				break;
			case KeyEvent.VK_DOWN:
				custom = stepper.step(parent, Command.Down);
				break;
			case KeyEvent.VK_LEFT:
				custom = stepper.step(parent, Command.Left);
				break;
			case KeyEvent.VK_RIGHT:
				custom = stepper.step(parent, Command.Right);
				break;
			case KeyEvent.VK_W:
				custom = stepper.step(parent, Command.Wait);
				break;
			case KeyEvent.VK_U:
				custom = states.get(current);
				break;
		}
		custom.fitness = fitness.evaluate(custom);
		updateText(custom);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
