package model.HuffmanCompression;
import java.util.LinkedList;
import java.util.List;

import searchable.Action;
import searchable.Solution;

public class SolutionCoder 
{
	public SolutionCoder() 
	{
		
	}
	
	/**
	 * extracing list of directions from sol
	 * @param sol
	 * @return
	 */
	public List<String> getDirList(List<Action> sol)
	{
		LinkedList<String> newSol = new LinkedList<>();
		for (Action a : sol)
		{
			String dir = a.getAction().substring(5,a.getAction().length());
			newSol.add(dir);
		}
		return newSol;
	}
	
	/**
	 * zipping solution to string
	 * @param s
	 * @return
	 */
	public String encodeSol(List<Action> s)
	{
		StringBuilder sb= new StringBuilder();
//		List<Action> sol = s.getTheSolution();
		for(Action a :s)
		{
			int size= a.getAction().indexOf(" ");
			String dir = a.getAction().substring(size+1, a.getAction().length());
			switch(dir)
			{
			case "up":
			{
				sb.append('u');
				break;
			}
			case "down":
			{
				sb.append('d');
				break;
			}
			case "left":
			{
				sb.append('l');
				break;
			}
			case "right":
			{
				sb.append('r');
				break;
			}
			default:
			{
				break;
			}
			}
			
		}
		StringBuilder zipedSol = new StringBuilder();
		int count = 1;
		for (int i = 1; i<sb.length();++i)
		{
			if(sb.charAt(i) == sb.charAt(i-1))
			{
				count++;
			}
			else 
			{
				zipedSol.append(count).append(sb.charAt(i-1));
				count =1;
			}
			if(i == sb.length())
			{
				if (count >1)
					zipedSol.append(count).append(sb.charAt(i-1));
				else zipedSol.append(count).append(sb.charAt(i));
			}
		}
		zipedSol.append(count).append(sb.charAt(sb.length()-1));
			
		
		return zipedSol.toString();
		
	}
	
	
	/**
	 * unzipping solution from string to list<Action>
	 * @param s
	 * @return
	 */
	public List<Action> decodeSol(String s)
	{
		LinkedList<Action> sol = new LinkedList<>();
		int i = 0;
		boolean jump=false;
		while (i < s.length())
		{
			int count = s.charAt(i)-48;
			int check=0;
			check = s.charAt(i+1);
			
			if ((check<58) && (check>=48))
			{
				String temp = ""+s.charAt(i)+s.charAt(i+1);
				count = Integer.parseInt(temp);
				i++;
				jump=true;
			}
		
			System.out.println(count);
			for (int j=0; j<count;j++)
			{
				Action a = new Action();
				String action = "move ";
				char c= s.charAt(i+1);
				
				switch(c)
				{
				case 'u':
				{
					action = action + "up";
					a = new Action(action);
					break;
				}
				case 'd':
				{
					action = action + "down";
					a = new Action(action);
					break;
				}
				case 'r':
				{
					action = action + "right";
					a = new Action(action);
					break;
				}
				case 'l':
				{
					action = action + "left";
					a = new Action(action);
					break;
				}
				 default:
				 {
					 System.err.println("Solution coder error in parsing the zipped sol");
					 break;
				 }
					
				}
				sol.add(a);
			
			}
			jump=false;
			i+=2;
		}
		return sol;
		
	}
	
}
