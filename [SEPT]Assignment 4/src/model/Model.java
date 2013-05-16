package model;

import view.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

import controller.MouseController;

public class Model 
	extends JFrame
{
	public static final int clear = 0;
	public static final int rectangle = 1;
	public static final int circle = 2;
	public static final int line = 3;
	
	private View gui;
	private int draw = clear;
	private LinkedList<File> svgFiles = new LinkedList<File>();
	
	private static int newFileCount = 1;
	
	public Model()
	{	
		
	}
	
	public Model(String[] args)
	{
		for(int i=0; i<args.length;i++)
		{
			if(checkFile(new File(args[i])))
			{
				svgFiles.add(new File(args[i]));
			}
		}
		
		System.out.println("Size: "+svgFiles.size());
	}
	
	public void setGUI(View gui)
	{
		this.gui = gui;
	}
		
	public LinkedList<DrawingBoard> getBoard()
	{
		LinkedList<DrawingBoard> board = new LinkedList<DrawingBoard>();
		
		for(int i=0; i<svgFiles.size();i++)
		{
			board.add(new DrawingBoard(svgFiles.get(i)));
			board.getLast().addMouseListener(new MouseController(board.getLast(),this));
		}
		
		return board;
	}
	
	public void addEmptyBoard()
	{		
		svgFiles.add(new File("New File "+Integer.toString(newFileCount++)));
	}
		
	public boolean openFile(File f)
	{
		if(checkFile(f))
		{
			svgFiles.add(f);
			
			return true;
		}
		
		return false;
	}
	
	public boolean checkFile(File f)
	{
		
		if(!f.exists())
		{
			String msg = "File <"+f.getName()+"> does not exists";
			
			System.out.println(msg);
			JOptionPane.showMessageDialog(this,msg,"Unable to open file",JOptionPane.ERROR_MESSAGE);
			this.dispose();
			
			return false;
		}
		
		if(!f.getName().toLowerCase().endsWith("svg"))
		{
			String msg = "File: <"+f.getName()+"> is not a valid SVG file";
			
			System.out.println(msg);
			JOptionPane.showMessageDialog(this,msg,"Unable to open file",JOptionPane.ERROR_MESSAGE);
			this.dispose();
			
			return false;
		}
		
		return true;
	}

	public void setOption(int option)
	{
		this.draw = option;
	}
	
	public int getOption()
	{
		return draw;
	}
	
	public void remove()
	{
		gui.removeSelected();
	}
}
