package model;

import controller.*;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DrawingBoard 
	extends JPanel
{
	public static final int clear = 0;
	public static final int rectangle = 1;
	public static final int circle = 2;
	public static final int line = 3;
	
	static int count = 1;
	Dimension size = null;
	int zoom = 100;
	File svg = null;
	public LinkedList<PolyObj> shapes = new LinkedList<PolyObj>();
	
	
	public DrawingBoard()
	{
		svg = new File("New File "+(count++));
		
		this.addMouseListener(new MouseController(this));
	}
	
	public DrawingBoard(File f)
	{
		this.svg = f;
		readFile(svg);
		setSize();
		
		this.addMouseListener(new MouseController(this));
	}
	
	public int getZoom()
	{
		return zoom;
	}
	
	public Dimension getBoardSize()
	{
		
		return new Dimension((int)size.getWidth()*zoom/100,(int)size.getHeight()*zoom/100);
	}
	
	public String getFileName()
	{
		return svg.getName();
	}
	
	public DrawingBoard getPanel()
	{		
		return this;
	}
	
	private void setSize()
	{
		Dimension bSize = new Dimension((int)(size.getWidth()*zoom/100),(int)(size.getHeight()*zoom/100));
		
		this.setPreferredSize(bSize);
	}
	
	public void setZoom(int newZoom)
	{
		this.zoom = newZoom;
		setSize();
		refresh();
	}
	private int sadd ;
	public void setCurrent(int wateva)
	{
		
	}
	
	public void addShape(PolyObj drawObj)
	{
		shapes.add(drawObj);
		
		refresh();
	}
	
	public void refresh()
	{
		this.revalidate();
		this.repaint();
	}
	
	public void paintComponent(Graphics gg)
	{			
		Rectangle2D.Double r = new Rectangle2D.Double(10, 10, 10, 10); 
		addShape(new PolyObj(r,1,Color.black,Color.black));
		
		Graphics2D g = (Graphics2D)gg;
		
		g.scale(zoom/100, zoom/100);
		
		g.setColor(ColorObj.getRGBColor("white"));
		g.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());
		
		for(int i=0;i<shapes.size();i++)
		{
			PolyObj drawShape = (PolyObj)(shapes.get(i));
			
			if(drawShape.shape instanceof Line2D)
			{
				g.setColor(drawShape.stroke);
				g.setStroke(new BasicStroke(drawShape.strokeWidth));				
				g.draw(drawShape.shape);	
			}
			else if(drawShape.shape instanceof Ellipse2D || drawShape.shape instanceof Rectangle2D)
			{				
				g.setColor(drawShape.stroke);
				g.setStroke(new BasicStroke(drawShape.strokeWidth));				
				g.draw(drawShape.shape);				
				g.setColor(drawShape.fill);
				g.fill(drawShape.shape);
			}
		}
	}
	
	private void readFile(File f)
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler()
			{
				public void startElement(String uri,String localName, String qName, Attributes attributes)
				{
					if(qName.equals("svg"))
					{
						int width=0, height=0;
						
						if(attributes.getValue("width")!=null)
							width=unitConvert(attributes.getValue("width"));
						if(attributes.getValue("height")!=null)
							height=unitConvert(attributes.getValue("height"));

						size = new Dimension(width,height);
					}
					
					if(qName.equals("rect"))
					{
						int x=0,y=0,width=0,height=0,strokeWidth=0;
						Color fill=null,stroke=null;
						
						if(attributes.getValue("x")!=null)
							x = unitConvert(attributes.getValue("x"));
						if(attributes.getValue("y")!=null)
							y = unitConvert(attributes.getValue("y"));
						if(attributes.getValue("width")!=null)
							width=unitConvert(attributes.getValue("width"));
						if(attributes.getValue("height")!=null)
							height=unitConvert(attributes.getValue("height"));
						if(attributes.getValue("stroke-width")!=null)
							strokeWidth=unitConvert(attributes.getValue("stroke-width"));
						if(attributes.getValue("fill")!=null)
							fill = ColorObj.getRGBColor(attributes.getValue("fill"));
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						
						shapes.add(new PolyObj(new Rectangle2D.Double(x, y, width, height), strokeWidth, fill, stroke));
					}
					
					else if(qName.equals("circle"))
					{
						int x=0,y=0,r=0,strokeWidth=0;
						Color fill=null, stroke=null;
						
						if(attributes.getValue("cx")!=null)
							x = unitConvert(attributes.getValue("cx"));
						if(attributes.getValue("cy")!=null)
							y = unitConvert(attributes.getValue("cy"));
						if(attributes.getValue("r")!=null)
							r = unitConvert(attributes.getValue("r"));
						if(attributes.getValue("stroke-width")!=null)
							strokeWidth=unitConvert(attributes.getValue("stroke-width"));
						if(attributes.getValue("fill")!=null)
							fill = ColorObj.getRGBColor(attributes.getValue("fill"));
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						
						shapes.add(new PolyObj(new Ellipse2D.Double(x-r, y-r, 2*r, 2*r), strokeWidth, fill, stroke));
					}
					
					else if(qName.equals("line"))
					{
						int x1=0,y1=0,x2=0,y2=0,strokeWidth=0;
						Color stroke = Color.BLACK;
						
						if(attributes.getValue("x1")!=null)
							x1 = unitConvert(attributes.getValue("x1"));
						if(attributes.getValue("y1")!=null)
							y1 = unitConvert(attributes.getValue("y1"));
						if(attributes.getValue("x2")!=null)
							x2 = unitConvert(attributes.getValue("x2"));
						if(attributes.getValue("y2")!=null)
							y2 = unitConvert(attributes.getValue("y2"));
						if(attributes.getValue("stroke-width")!=null)
							strokeWidth=unitConvert(attributes.getValue("stroke-width"));
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						
						shapes.add(new PolyObj(new Line2D.Double(x1, y1, x2, y2), strokeWidth, null, stroke));
					}
				}
				
			};
			
			parser.parse(f.getPath(), handler);
		}
		
		
		catch(Exception e)
		{
			
		}
	}
	
	private int unitConvert(String input)
	{
		String part1="", part2="";
		double result=0;
		int i=0;
	
		//Breaking string into 2parts, to get the value and unit used
		for	(i=0; i<input.length(); i++)
		{
			if	(Character.isDigit(input.charAt(i)))
				part1=part1+input.charAt(i);
	
			else if	(Character.isLetter(input.charAt(i)))
				part2=part2+input.charAt(i);
			
			else
				return 0;
		}
	
		double dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		
		//Units conversion
		if	((part2.equalsIgnoreCase("px"))||(part2.equals("")))
			result=Double.parseDouble(part1);
		
		else if	(part2.equalsIgnoreCase("cm"))
			result=Double.parseDouble(part1)*dpi/2.54;
		
		else if	(part2.equalsIgnoreCase("in"))
			result=Double.parseDouble(part1)*dpi;
		
		else if	(part2.equalsIgnoreCase("mm"))
			result=Double.parseDouble(part1)*dpi/25.4;
		
		else if	(part2.equalsIgnoreCase("pt"))
			result=Double.parseDouble(part1)*dpi/72;
		
		else if	(part2.equalsIgnoreCase("pc"))
			result=Double.parseDouble(part1)*dpi/6;
				
		return (int)result; 
	}
}
