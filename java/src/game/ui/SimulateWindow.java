package game.ui;

import game.Board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 * @author christoph
 */

public class SimulateWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private LinkedList<Board> states;
	private JTextArea textArea;
	private int current = 0;

	public SimulateWindow() {
	  super("Simulation");
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.setSize(300, 400);
	  
	  JToolBar tbar = new JToolBar();
	  
	  final JButton buttonBack = new JButton("Back");
	  buttonBack.setEnabled(false);
	  tbar.add(buttonBack);
	  
	  final JButton buttonNext = new JButton("Next");
	  
	  buttonBack.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			textArea.setText(states.get(--current).toString());
			if(current == 0) {
				buttonBack.setEnabled(false);		
			}
			buttonNext.setEnabled(true);
		}
			  
	  });

	  buttonNext.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText(states.get(++current).toString());
			if(current == (states.size()-1)) {
				buttonNext.setEnabled(false);		
			}
			buttonBack.setEnabled(true);
		}
		  
	  });
	  
	  tbar.add(buttonNext);
	  
	  textArea = new JTextArea();
	  textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
	  
	  JPanel jplContentPane = new JPanel();
	  jplContentPane.setLayout(new BorderLayout());
	  jplContentPane.setPreferredSize(new Dimension(400, 100));
	  jplContentPane.add(tbar, BorderLayout.NORTH);
	  jplContentPane.add(textArea, BorderLayout.CENTER);
  	  setContentPane(jplContentPane);  

  	  states = new LinkedList<Board>();
	}
	
	public void addState(Board state) {
		states.add(state);
		if(states.size() == 1) {
			textArea.setText(state.toString());
		}
	}
	
}
