
package view;

import model.*;
import controller.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.File;

public class View 
	extends JFrame
{
	JMenuBar menu = new JMenuBar();
	JTabbedPane tab = new JTabbedPane();
	Model svg;
	
	public View(Model svg)
	{
		this.svg=svg;
		
		setMenu();
		setTab();
		addToFrame();
		showFrame();
	}
		
	private void setMenu()
	{
		JMenu file = new JMenu("File");
		JMenu zoom = new JMenu("Zoom");
		
		JMenuItem newFile = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem zoomIn = new JMenuItem("Zoom In");
		JMenuItem zoomOut = new JMenuItem("Zoom Out");
		
		newFile.setActionCommand("new");
		open.setActionCommand("open");
		save.setActionCommand("save");
		zoomIn.setActionCommand("zoomin");
		zoomOut.setActionCommand("zoomOut");
			
		file.add(newFile);
		file.add(open);
		file.add(save);
		zoom.add(zoomIn);
		zoom.add(zoomOut);
		
		newFile.addActionListener(new ActionController(this));
		open.addActionListener(new ActionController(this));
		save.addActionListener(new ActionController(this));
		zoomIn.addActionListener(new ActionController(this));
		zoomOut.addActionListener(new ActionController(this));
		
		menu.add(file);
		menu.add(zoom);
	}
	
	public void newFile()
	{
		
	}
	
	public void openFile()
	{
		JFileChooser op = new JFileChooser(System.getProperty("user.home"));
		
		op.setFileFilter
		( 
			new javax.swing.filechooser.FileFilter()
			{
				public boolean accept(File f)
				{
					return (f.getName().toLowerCase()).endsWith("svg")||f.isDirectory();
				}
				public String getDescription()
				{
					return "SVG file";
				}
			}
		);
				
		int fc = op.showOpenDialog(this);
						
		if(fc == JFileChooser.APPROVE_OPTION)
		{
			if(svg.openFile(op.getSelectedFile()))
			{
				DrawingBoard db = (DrawingBoard)svg.getBoard().getLast();
				String title = db.getFileName();
				
				System.out.println(title);
										
				JScrollPane sp = new JScrollPane();
				JViewport vp = sp.getViewport();
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				panel.add(db.getPanel());
				
				vp.add(panel);		
						
				tab.add(sp);
				tab.setTitleAt(tab.getTabCount()-1,title);
				tab.setSelectedIndex(tab.getTabCount()-1);
				
				this.revalidate();
				this.repaint();
			}
		}
	}
	
	public void saveFile()
	{
		
	}
	
	public void zoomIn()
	{
		int index = tab.getSelectedIndex();
		
		JScrollPane scroll = (JScrollPane)tab.getComponentAt(index);
		JViewport viewport = scroll.getViewport();
		JPanel pane;
		pane= (JPanel)viewport.getView();
		DrawingBoard edit = (DrawingBoard)pane.getComponent(0);
		
		if(edit.getZoom()<1000)
			edit.setZoom(edit.getZoom()+100);
	}
	
	public void zoomOut()
	{
		int index = tab.getSelectedIndex();
		
		JScrollPane scroll = (JScrollPane)tab.getComponentAt(index);
		JViewport viewport = scroll.getViewport();
		JPanel pane;
		pane= (JPanel)viewport.getView();
		DrawingBoard edit = (DrawingBoard)pane.getComponent(0);
		
		if(edit.getZoom()>100)
			edit.setZoom(edit.getZoom()-100);
	}
	
	private void setTab()
	{
		tab.addKeyListener(new KeyController(this));
		
		for(int i=0; i< svg.getBoard().size();i++)
		{
			DrawingBoard db = (DrawingBoard)svg.getBoard().get(i);
			String title = db.getFileName();
									
			JScrollPane sp = new JScrollPane();
			JViewport vp = sp.getViewport();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			panel.add(db.getPanel());
			
			vp.add(panel);		
						
			tab.add(sp);
			tab.setTitleAt(i,title);
		}
	}
	
	private void addToFrame()
	{
		this.setJMenuBar(menu);
		
		//this.add(,BorderLayout.WEST);
		this.add(tab,BorderLayout.CENTER);
	}
	
	private void showFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100);
		this.setLocation(50,50);
	}
	
}
