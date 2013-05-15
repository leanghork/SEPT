package controller;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MouseController 
	implements MouseListener 
{
	private DrawingBoard theBoard;
	private int startX, startY, endX, endY;
	
	public MouseController(DrawingBoard theBoard)
	{
		this.theBoard = theBoard;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		this.startX = e.getX()-50;
		this.startY = e.getY()-50;
	}

	public void mouseReleased(MouseEvent e) 
	{
		this.endX = e.getX()-50;
		this.endY = e.getY()-50;
		
		if(theBoard.getOption() == DrawingBoard.rectangle)
		{
			double width = Math.abs(startX-endX);
			double height = Math.abs(startY-endY);
						
			Rectangle2D.Double r = new Rectangle2D.Double(startX, startY, width, height); 	
			theBoard.addShape(new PolyObj(r,2,Color.gray,Color.blue));	
		}
		
		else if(theBoard.getOption() == DrawingBoard.circle)
		{
			double width = Math.abs(startX-endX);
			double height = Math.abs(startY-endY);
			double radius = Math.sqrt((width*width)+(height*height)); 
			
			Ellipse2D.Double c = new Ellipse2D.Double(startX, startY, 2*radius, 2*radius);
			theBoard.addShape(new PolyObj(c,2,Color.blue,Color.cyan));
		}
		else if(theBoard.getOption() == DrawingBoard.line)
		{
			Line2D.Double l = new Line2D.Double(startX, startY, endX, endY);
			theBoard.addShape(new PolyObj(l,2,null,Color.cyan));
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

	private Point2D getCoordinate(int x, int y)
	{
		Point p = theBoard.getLocation();
		
		return new Point2D.Double(x,y);
	}
}
