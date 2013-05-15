package controller;

import model.*;

import java.awt.Color;
import java.awt.event.*;

import java.awt.geom.*;

public class MouseController 
	implements MouseListener 
{
	private DrawingBoard theBoard;
	private int startX, startY;
	
	public MouseController(DrawingBoard theBoard)
	{
		this.theBoard = theBoard;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		startX = e.getX();
		startY = e.getY();
		System.out.println(startX + " " + startY);
	}

	public void mouseReleased(MouseEvent e) 
	{
		int height = Math.abs(startY - e.getY());
		int width = Math.abs(startX - e.getX());
		
		Rectangle2D.Double r = new Rectangle2D.Double(startX, startY, width, height); 
		
		theBoard.addShape(new PolyObj(r,2,Color.gray,Color.blue));	
	}
	
	public void mouseClicked(MouseEvent e) 
	{

	}

	public void mouseEntered(MouseEvent e) 
	{

	}

	public void mouseExited(MouseEvent e) 
	{

	}

}
