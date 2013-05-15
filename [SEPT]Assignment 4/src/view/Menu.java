package view;

import javax.swing.*;

public class Menu 
{
	public Menu()
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
	}
	
}
