package controller;

import model.*;

import java.awt.Color;
import java.awt.event.*;

import java.awt.geom.*;

public class MouseController 
	implements MouseListener 
{
	private DrawingBoard theBoard;
	private int startX, startY,endX,endY;
	
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
		endX = e.getX();
		endY = e.getY();		
		
		if(theBoard.getOption() == DrawingBoard.rectangle)
		{
			int height = Math.abs(startY - endY);
			int width = Math.abs(startX - endX);
			
			Rectangle2D.Double r = new Rectangle2D.Double(startX, startY, width, height); 	
			theBoard.addShape(new PolyObj(r,2,Color.gray,Color.blue));	
		}
		
		else if(theBoard.getOption() == DrawingBoard.circle)
		{
			double r = Math.sqrt((endY-startY)*(endY-startY) + (endX-startX)*(endX-startX));
			Ellipse2D.Double c = new Ellipse2D.Double(startX-r, startY-r, 2*r, 2*r);
			theBoard.addShape(new PolyObj(c,2,Color.blue,Color.cyan));
		}
		else if(theBoard.getOption() == DrawingBoard.line)
		{
			Line2D.Double l = new Line2D.Double(startX, startY, endX, endY);
			theBoard.addShape(new PolyObj(l,2,Color.blue,Color.cyan));
		}
		
		
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
