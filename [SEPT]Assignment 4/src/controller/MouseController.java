package controller;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MouseController 
	implements MouseListener 
{
	private DrawingBoard theBoard;
	private Point2D.Double start, end;
	
	public MouseController(DrawingBoard theBoard)
	{
		this.theBoard = theBoard;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		start = (Point2D.Double)this.getCoordinate(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) 
	{
		end = (Point2D.Double)this.getCoordinate(e.getX(), e.getY());		
		
		if(theBoard.getOption() == DrawingBoard.rectangle)
		{
			double height = Math.abs(start.getY() - end.getY());
			double width = Math.abs(start.getX() - end.getX());
			
			Rectangle2D.Double r = new Rectangle2D.Double(start.getX(), start.getY(), width, height); 	
			theBoard.addShape(new PolyObj(r,2,Color.gray,Color.blue));	
		}
		
		else if(theBoard.getOption() == DrawingBoard.circle)
		{
			double r = Math.sqrt((start.getY()-end.getY())*(start.getY()-end.getY()) + (start.getX()-end.getX())*(start.getX()-end.getX()));
			Ellipse2D.Double c = new Ellipse2D.Double(start.getX()-r, start.getY()-r, 2*r, 2*r);
			theBoard.addShape(new PolyObj(c,2,Color.blue,Color.cyan));
		}
		else if(theBoard.getOption() == DrawingBoard.line)
		{
			Line2D.Double l = new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY());
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
