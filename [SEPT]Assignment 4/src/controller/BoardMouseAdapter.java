package controller;

import model.*;

import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Line2D;

public class BoardMouseAdapter 
	extends MouseAdapter
{
	private DrawingBoard theBoard = null;
	
	public BoardMouseAdapter(DrawingBoard theBoard)
	{
		this.theBoard = theBoard;
	}
		
	public void mousePressed(MouseEvent e)
	{

	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}

	
	public void mouseDragged(MouseEvent e)
	{
		Line2D.Double l = new Line2D.Double(e.getX()-50, e.getY()-50, e.getPoint().getX()-50, e.getPoint().getY()-50);
		theBoard.addShape(new PolyObj(l,2,null,Color.cyan));
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		
	}
}
