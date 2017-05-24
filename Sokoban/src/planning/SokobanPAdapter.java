package planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common_data.item.Box;
import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Player;
import common_data.item.Target;
import common_data.item.Wall;
import common_data.level.Level;
import plannable.Action;
import plannable.Goal;
import plannable.Plannable;
import plannable.Predicate;
import plannable.State;

public class SokobanPAdapter implements Plannable
{
	Level level;
	public SokobanPAdapter(Level l) 
	{
		level=l;
	}

	@Override
	public Goal getGoal() 
	{
		List<Predicate> list = new ArrayList<Predicate>();
		for(int i=0;i<level.getTargets().size();++i)
		{
			list.add(new Predicate("On",level.getBoxes().get(i),level.getTargets().get(i)));
		}
		for(int i=0;i<level.getAllFloors().size()-1;++i)
		{
			list.add(new Predicate("Clear",level.getAllFloors().get(i)));
		}
		Floor f = level.getAllFloors().get(level.getAllFloors().size()-1);
		
		list.add(new Predicate("On", level.getPlayers().get(0),f));
		Goal goal = new Goal(list);
		return goal;
	}

	@Override
	public State getInitialState() 
	{
		State state = new State();
		Player p = level.getPlayers().get(0);
		Item underPlayerItem = level.getStaticItemAtPos(p.getPosX(), p.getPosY());
		state.addPredicate(new Predicate("On",p,underPlayerItem));
		List<Box> boxes= level.getBoxes();
		for(Box b : boxes)
		{
			state.addPredicate(new Predicate("On", b, level.getStaticItemAtPos(b.getPosX(), b.getPosY())));
		}
		List<Target> targets = extractClearTargets();
		for (Target t : targets)
		{
			state.addPredicate(new Predicate("Clear",t));
		}
		
		List<Floor> floors = extractClearFloors();
		if(floors != null)
		{
			for(Floor f : floors)
			{
				state.addPredicate(new Predicate("Clear",f));
			}
		}
		
		return state;
	}

	@Override
	public Set<Predicate> groundTruth() 
	{
		HashSet<Predicate> groundTruth = new HashSet<Predicate>();
		//groundTruth.add(new Predicate("Clear", "Table"));
		return groundTruth;
	}

	@Override
	public List<Action> getAllActions() 
	{
		List<Action> actions = new ArrayList<Action>();
		actions.add(new MoveAction());
		actions.add(new PushAction());
		return actions;
	}

	@Override
	public List<Object> getAllObjects() 
	{
		List<Object> objects = new ArrayList<>();
		for(Player p : level.getPlayers())
		{
			objects.add(p);
		}
		for(Target t: level.getTargets())
		{
			objects.add(t);
		}
		for(Floor f : level.getAllFloors())
		{
			objects.add(f);
		}
		for(Wall w: level.getAllWalls())
		{
			objects.add(w);
		}
		for(Box b : level.getBoxes())
		{
			objects.add(b);
		}
		return objects;
	}
	private List<Floor> extractClearFloors()
	{
		boolean exist=false;
		List<Floor> cleared = new ArrayList<Floor>();
		for(Floor f : level.getAllFloors())
		{
			if(level.getMovableAtPos(f.getPosX(),f.getPosY()) == null)
			{
				exist=true;
				cleared.add(f);
			}
		}
		if(exist)
			return cleared;
		return null;
	}

	private List<Target> extractClearTargets()
	{
		boolean exist=false;
		List<Target> cleared = new ArrayList<Target>();
		for(Target t : level.getTargets())
		{
			if(level.getMovableAtPos(t.getPosX(),t.getPosY()) == null)
			{
				exist=true;
				cleared.add(t);
			}
		}
		if(exist)
			return cleared;
		return null;
	}
	private List<Target> extractNotClearTargets()
	{
		ArrayList<Target> targets = level.getTargets();
		List<Target> free = extractClearTargets();
		ArrayList<Target> not_free = new ArrayList<>();
		for(Target t : targets)
		{
			if(!free.contains(t))
			{
				not_free.add(t);
			}
		}
		return not_free;
	}
}
