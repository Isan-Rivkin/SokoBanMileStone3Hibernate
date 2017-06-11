package solver;

import java.util.LinkedList;

import searchable.Solution;

public class FinalSolution 
{
	public LinkedList<Solution> solutions_compilation;

	public FinalSolution()
	{
		solutions_compilation = new LinkedList<>();
	}
	public LinkedList<Solution> getFinalSolution()
	{
		return solutions_compilation;
	}
	
	
}
