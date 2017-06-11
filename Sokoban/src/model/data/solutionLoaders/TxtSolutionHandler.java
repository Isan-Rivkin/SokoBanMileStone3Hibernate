package model.data.solutionLoaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Target;
import common_data.item.Wall;
import model.data.itemGeneratos.FactoryItemLoader;
import model.policy.PolicyLevelStructure;
import model.policy.SokobanPolicy;
import searchAlgoExtract.Action;
import searchAlgoExtract.Solution;

public class TxtSolutionHandler implements ISolutionHandler
{

	@Override
	public Solution load(InputStream in)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line="";
		LinkedList<Action> actions = new LinkedList<>();
		try 
		{
			while((line=reader.readLine())!=null)
			{
				actions.add(new Action(line));
			}
			reader.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return new Solution(actions);
	}

	@Override
	public void save(OutputStream out, Solution solution)
	{
		PrintWriter writer = new PrintWriter(out);
		for(Action a : solution.getTheSolution())
		{
			writer.write(a.getAction());
			writer.println();
		}
		writer.flush();
	}

}
