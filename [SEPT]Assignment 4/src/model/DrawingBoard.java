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
	Dimension size = null;
	int zoom = 100;
	File svg = null;
	public LinkedList<PolyObj> shapes = new LinkedList<PolyObj>();
	public LinkedList<PolyObj> select = new LinkedList<PolyObj>();
		
	public DrawingBoard(File f)
	{
		this.svg = f;
		readFile(svg);
		initSize();
		setSize();		
	}
	
	public void selectShape(int startX, int startY)
	{
		select.clear();
			
		for(int i = shapes.size()-1; i>=0; i--)
		{			
			if(shapes.get(i).shape instanceof Rectangle2D)
			{
				Rectangle2D.Double check = (Rectangle2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-5, startY-5, 5, 5))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
			if(shapes.get(i).shape instanceof Ellipse2D)
			{
				Ellipse2D.Double check = (Ellipse2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-5, startY-5, 5, 5))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
			if(shapes.get(i).shape instanceof Line2D)
			{
				Line2D.Double check = (Line2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-15, startY-15, 15, 15))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
		}
		
		this.refresh();
	}
	
	public void selectShape(int startX, int startY, int endX, int endY)
	{
		select.clear();
		
		int height = Math.abs(startX-endX);
		int width = Math.abs(startY-endY);
		
		for(int i = shapes.size()-1; i>=0; i--)
		{			
			if(shapes.get(i).shape instanceof Rectangle2D)
			{
				Rectangle2D.Double check = (Rectangle2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-5, startY-5, height+5, width+5))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
			if(shapes.get(i).shape instanceof Ellipse2D)
			{
				Ellipse2D.Double check = (Ellipse2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-5, startY-5, height+5, width+5))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
			if(shapes.get(i).shape instanceof Line2D)
			{
				Line2D.Double check = (Line2D.Double)(shapes.get(i).shape);
				int strokeWidth = shapes.get(i).strokeWidth;
				
				if(check.intersects(startX-5, startY-5, height+5, width+5))
				{
					shapes.get(i).setSelected();
					select.add(shapes.get(i));
					break;
				}
			}
			
		}
		
		this.refresh();
	}
	
	public void selectAll()
	{
		select.clear();
		
		for(int i=0; i<shapes.size();i++)
		{
			select.add(shapes.get(i));
		}
		
		refresh();
	}
	
	public void remove()
	{
		for(int i=0; i<select.size();i++)
		{
			shapes.remove(select.get(i));
		}
		
		select.clear();
		refresh();
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
	
	private void initSize()
	{
		int maxX = 0;
		int maxY = 0;
		
		for(int i=0;i<shapes.size();i++)
		{			
			if(shapes.get(i).shape instanceof Rectangle2D)
			{
				maxX = (int)((Rectangle2D.Double)shapes.get(i).shape).getMaxX();
				maxY = (int)((Rectangle2D.Double)shapes.get(i).shape).getMaxY();
			}
			
			if(shapes.get(i).shape instanceof Ellipse2D)
			{
				maxX = (int)((Ellipse2D.Double)shapes.get(i).shape).getMaxX();
				maxY = (int)((Ellipse2D.Double)shapes.get(i).shape).getMaxY();
			}
			
			if(shapes.get(i).shape instanceof Line2D)
			{
				maxX = (int)((Line2D.Double)shapes.get(i).shape).getX1();
				
				if(maxX < (int)((Line2D.Double)shapes.get(i).shape).getX2())
					maxX = (int)((Line2D.Double)shapes.get(i).shape).getX2();
				
				maxY = (int)((Line2D.Double)shapes.get(i).shape).getY1();
								
				if(maxY < (int)((Line2D.Double)shapes.get(i).shape).getY2())
					maxY = (int)((Line2D.Double)shapes.get(i).shape).getY2();
			}
			
			if(size.getWidth() > maxX)
				maxX = (int)size.getWidth();
			if(size.getHeight() > maxY)
				maxY = (int)size.getHeight();
			
			size = new Dimension(maxX,maxY);
		}
		
		size = new Dimension(maxX+100,maxY+100);
			
	}
	
	private void setSize()
	{		
		Dimension bSize = new Dimension((int)(size.getWidth()*zoom/100),(int)(size.getHeight()*zoom/100));
	
		this.setPreferredSize(bSize);
		this.refresh();
	}
	
	public void setZoom(int newZoom)
	{
		this.zoom = newZoom;
		setSize();
		refresh();
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
		Graphics2D g = (Graphics2D)gg;
		
		g.scale(zoom/100, zoom/100);
		
		g.setColor(ColorObj.getRGBColor("white"));
		g.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());
			
		g.translate(50,50);
		
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
					
		for(int i=0;i<select.size();i++)
		{			
			PolyObj drawShape = (PolyObj)(select.get(i));
			
			g.setPaint(Color.black);
			g.setStroke(new BasicStroke(0));
			//g.draw(drawShape.shape);
			
			if(drawShape.shape instanceof Line2D)
			{			
				int x1 = (int)((Line2D.Double)(select.get(i).shape)).getX1();
				int y1 = (int)((Line2D.Double)(select.get(i).shape)).getY1();	
				int x2 = (int)((Line2D.Double)(select.get(i).shape)).getX2();
				int y2 = (int)((Line2D.Double)(select.get(i).shape)).getY2();
					
				
				g.fill(new Rectangle2D.Double(x1 - 5.0, y1 - 5.0, 10.0, 10.0));
				g.fill(new Rectangle2D.Double(x2 - 5.0, y2 - 5.0, 10.0, 10.0));
			}
			else if(drawShape.shape instanceof Rectangle2D)
			{				
				int x = (int)((Rectangle2D.Double)(select.get(i).shape)).getX()- select.get(i).strokeWidth/2;
				int y = (int)((Rectangle2D.Double)(select.get(i).shape)).getY()- select.get(i).strokeWidth/2;	
				int w = (int)((Rectangle2D.Double)(select.get(i).shape)).getWidth()+ select.get(i).strokeWidth;
				int h = (int)((Rectangle2D.Double)(select.get(i).shape)).getHeight()+ select.get(i).strokeWidth;
				
			    g.setColor(Color.WHITE);
			    g.fill(new Rectangle2D.Double(x - 5.0, y - 5.0, 10.0, 10.0));
		        g.fill(new Rectangle2D.Double(x + w * 0.5 - 5.0, y - 5.0, 10.0, 10.0));
		        g.fill(new Rectangle2D.Double(x + w - 5.0, y - 5.0, 10.0, 10.0));
		        g.fill(new Rectangle2D.Double(x - 5.0, y + h * 0.5 - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x + w - 5.0, y + h * 0.5 - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x - 5.0, y + h - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x + w * 0.5 - 5.0, y + h - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x + w - 5.0, y + h - 5.0, 10.0, 10.0));
			    
			    g.setColor(Color.BLACK);
			    g.draw(new Rectangle2D.Double(x - 5.0, y - 5.0, 10.0, 10.0));
		        g.draw(new Rectangle2D.Double(x + w * 0.5 - 5.0, y - 5.0, 10.0, 10.0));
		        g.draw(new Rectangle2D.Double(x + w - 5.0, y - 5.0, 10.0, 10.0));
		        g.draw(new Rectangle2D.Double(x - 5.0, y + h * 0.5 - 5.0, 10.0, 10.0));
			    g.draw(new Rectangle2D.Double(x + w - 5.0, y + h * 0.5 - 5.0, 10.0, 10.0));
			    g.draw(new Rectangle2D.Double(x - 5.0, y + h - 5.0, 10.0, 10.0));
			    g.draw(new Rectangle2D.Double(x + w * 0.5 - 5.0, y + h - 5.0, 10.0, 10.0));
			    g.draw(new Rectangle2D.Double(x + w - 5.0, y + h - 5.0, 10.0, 10.0));
			}		
			else if(drawShape.shape instanceof Ellipse2D)
			{
				int x = (int)((Ellipse2D.Double)(select.get(i).shape)).getX();
				int y = (int)((Ellipse2D.Double)(select.get(i).shape)).getY();	
				int w = (int)((Ellipse2D.Double)(select.get(i).shape)).getWidth();
				int h = (int)((Ellipse2D.Double)(select.get(i).shape)).getHeight();
				
				g.fill(new Rectangle2D.Double(x - 5.0, y - 5.0, 10.0, 10.0));
		        g.fill(new Rectangle2D.Double(x + w - 5.0, y - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x - 5.0, y + h - 5.0, 10.0, 10.0));
			    g.fill(new Rectangle2D.Double(x + w - 5.0, y + h - 5.0, 10.0, 10.0));
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
				LinkedList<Integer> gStrokeWidth = new LinkedList<Integer>();
				LinkedList<Color> gStroke = new LinkedList<Color>();
				LinkedList<Color> gFill = new LinkedList<Color>();
				
				public void startElement(String uri,String localName, String qName, Attributes attributes)
				{					
					if(qName.equalsIgnoreCase("svg"))
					{
						int width=0, height=0;
						
						if(attributes.getValue("width")!=null)
							width=unitConvert(attributes.getValue("width"));
						if(attributes.getValue("height")!=null)
							height=unitConvert(attributes.getValue("height"));

						size = new Dimension(width,height);
					}
					
					if(qName.equalsIgnoreCase("g"))
					{
						int strokeWidth = -1;
						Color fill = null;
						Color stroke = null;
						
						
						if(attributes.getValue("stroke-width")!= null)
							strokeWidth = unitConvert(attributes.getValue("strokeWidth"));
						
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						
						if(attributes.getValue("fill")!=null)
							fill = ColorObj.getRGBColor(attributes.getValue("fill"));
						
						gStrokeWidth.add(strokeWidth);
						gStroke.add(stroke);
						gFill.add(fill);						
					}
					
					if(qName.equalsIgnoreCase("rect"))
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
						else if(attributes.getValue("stroke-width")==null)
						{
							for(int i=gStrokeWidth.size()-1;i>=0;i--)
							{
								if(gStrokeWidth.get(i) != -1)
								{
									strokeWidth = gStrokeWidth.get(i);
									break;
								}
							}
						}
						
						if(attributes.getValue("fill")!=null)
							fill = ColorObj.getRGBColor(attributes.getValue("fill"));
						else if(attributes.getValue("fill") == null)
						{
							for(int i=gFill.size()-1;i>=0;i--)
							{
								if(gFill.get(i) != null)
								{
									fill = gFill.get(i);
									break;
								}
							}
						}
						
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						else if(attributes.getValue("stroke") == null)
						{
							for(int i=gStroke.size()-1;i>=0;i--)
							{
								if(gStroke.get(i) != null)
								{
									stroke = gStroke.get(i);
									break;
								}
							}
						}
						
						shapes.add(new PolyObj(new Rectangle2D.Double(x, y, width, height), strokeWidth, fill, stroke));
					}
					
					else if(qName.equalsIgnoreCase("circle"))
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
						else if(attributes.getValue("stroke-width")==null)
						{
							for(int i=gStrokeWidth.size()-1;i>=0;i--)
							{
								if(gStrokeWidth.get(i) != -1)
								{
									strokeWidth = gStrokeWidth.get(i);
									break;
								}
							}
						}
						
						if(attributes.getValue("fill")!=null)
							fill = ColorObj.getRGBColor(attributes.getValue("fill"));
						else if(attributes.getValue("fill") == null)
						{
							for(int i=gFill.size()-1;i>=0;i--)
							{
								if(gFill.get(i) != null)
								{
									fill = gFill.get(i);
									break;
								}
							}
						}
						
						if(attributes.getValue("stroke")!=null)
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						else if(attributes.getValue("stroke") == null)
						{
							for(int i=gStroke.size()-1;i>=0;i--)
							{
								if(gStroke.get(i) != null)
								{
									stroke = gStroke.get(i);
									break;
								}
							}
						}
						
						shapes.add(new PolyObj(new Ellipse2D.Double(x-r, y-r, 2*r, 2*r), strokeWidth, fill, stroke));
					}
					
					else if(qName.equalsIgnoreCase("line"))
					{					
						int x1=0,y1=0,x2=0,y2=0,strokeWidth=0;
						Color stroke = null;
						
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
						else if(attributes.getValue("stroke-width")==null)
						{
							for(int i=gStrokeWidth.size()-1;i>=0;i--)
							{
								if(gStrokeWidth.get(i) != -1)
								{
									strokeWidth = gStrokeWidth.get(i);
									break;
								}
							}
						}
						
						if(attributes.getValue("stroke")!=null)
						{
							stroke = ColorObj.getRGBColor(attributes.getValue("stroke"));
						}
						else if(attributes.getValue("stroke") == null)
						{
							for(int i=gStroke.size()-1;i>=0;i--)
							{
								if(gStroke.get(i) != null)
								{
									stroke = gStroke.get(i);
									break;
								}
							}
						}
						
						shapes.add(new PolyObj(new Line2D.Double(x1, y1, x2, y2), strokeWidth, null, stroke));
					}
				}
				
				public void endElement(String uri, String localName, String qName)
				{
					/*if(qName.equalsIgnoreCase("g"))
					{
						gStrokeWidth.removeLast();
						gStroke.removeLast();
						gFill.removeLast();
					}*/
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
