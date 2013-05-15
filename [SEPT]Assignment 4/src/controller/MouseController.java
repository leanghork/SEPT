package controller;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MouseController 
	implements MouseListener 
{
	private Model model;
	private DrawingBoard theBoard;
	private int startX, startY, endX, endY;
	
	public MouseController(DrawingBoard theBoard,Model model)
	{
		this.model = model;
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
		
		if(model.getOption() == Model.rectangle)
		{
			if(endX<startX)
			{
				int temp = endX;
				endX = startX;
				startX = temp;
			}
			if(endY<startY)
			{
				int temp = endY;
				endY = startY;
				startY = temp;
			}
			
			double width = Math.abs(startX-endX);
			double height = Math.abs(startY-endY);
			
			Rectangle2D.Double r = new Rectangle2D.Double(startX, startY, width, height); 	
			theBoard.addShape(new PolyObj(r,2,Color.gray,Color.blue));	
		}
		
		else if(model.getOption() == Model.circle)
		{
			double width = Math.abs(startX-endX);
			double height = Math.abs(startY-endY);
			double radius = Math.sqrt((width*width)+(height*height)); 
			
			Ellipse2D.Double c = new Ellipse2D.Double(startX-radius, startY-radius, 2*radius, 2*radius);
			theBoard.addShape(new PolyObj(c,2,Color.blue,Color.cyan));
		}
		else if(model.getOption() == Model.line)
		{
			Line2D.Double l = new Line2D.Double(startX, startY, endX, endY);
			theBoard.addShape(new PolyObj(l,2,null,Color.cyan));
		}		
		
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		if(model.getOption() == Model.clear)
		{
			theBoard.selectShape(e.getX()-50, e.getY()-50);
		}
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
