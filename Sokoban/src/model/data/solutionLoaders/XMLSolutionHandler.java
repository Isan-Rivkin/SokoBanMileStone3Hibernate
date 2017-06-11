package model.data.solutionLoaders;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import searchAlgoExtract.Solution;
import sokoban_utils.XMLUtil;

public class XMLSolutionHandler implements ISolutionHandler 
{

	@Override
	public Solution load(InputStream in)
	{
		Solution solution = null;
		try {
			 solution=(Solution)XMLUtil.decodeXML(in);
			
		}
		catch (FileNotFoundException e) 
		{
            System.out.println(e.toString());
			e.printStackTrace();
		}
	
		return solution;
	}

	@Override
	public void save(OutputStream out, Solution solution)
	{
		try 
		{
			XMLUtil.encodeXML(solution, out);
		}
		catch (FileNotFoundException e) 
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

}
