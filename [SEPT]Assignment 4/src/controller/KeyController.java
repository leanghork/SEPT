package controller;

import view.*;
import java.awt.event.*;

public class KeyController 
	implements KeyListener
{
	View gui;
	boolean ctrlHold = false;
	
	public KeyController(View gui)
	{
		this.gui=gui;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_CONTROL)
			ctrlHold = true;
		
		if(ctrlHold && (e.getKeyCode()==KeyEvent.VK_EQUALS || e.getKeyCode()==KeyEvent.VK_ADD))
			gui.zoomIn();
		
		if(ctrlHold && (e.getKeyCode()==KeyEvent.VK_MINUS || e.getKeyCode()==KeyEvent.VK_SUBTRACT))
			gui.zoomOut();
		
		if(ctrlHold && e.getKeyCode()==KeyEvent.VK_N)
			gui.newFile();
		
		if(ctrlHold && e.getKeyCode()==KeyEvent.VK_O)
			gui.openFile();
		
		if(ctrlHold && e.getKeyCode()==KeyEvent.VK_S)
			gui.saveFile();
		if(ctrlHold && e.getKeyCode()==KeyEvent.VK_A)
			gui.selectAll();
	}
	
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_CONTROL)
			ctrlHold = false;
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
}
