package model;

import java.util.List;

import model.database.HighScoreP;
import model.database.HighScoreQuery;
import model.database.IDBMapper;
import model.database.IQuery;
import model.database.LevelP;
import model.database.POJO;
import model.database.PlayerP;

public class HSDataManager implements IDataManager 
{
	private IDBMapper mapper;
	private List<HighScoreP> hs_list;
	
	public HSDataManager(IDBMapper mapper) 
	{
		this.mapper=mapper;
	}
	@Override
	public List<HighScoreP> search(String classify, String name, String orderType) 
	{
		/**
		 * filer =l,p , playerName/levelName , orderby="","steps","time","name"
		 */
		IQuery query = new HighScoreQuery();
		if(classify.equals("l"))
		{
			query.setLevelName(name);
		}
		else if(classify.equals("p"))
		{
			query.setPlayerName(name);
		}
		if(orderType.equals("steps"))
		{
			query.initOrderBySteps();
		}
		else if(orderType.equals("time"))
		{
			query.initOrderByTime();
		}
		else if(orderType.equals("name"))
		{
			if(classify.equals("p"))
				query.initLexiPlayerName();
			else
				query.initLexiLevelName();
		}
		query.setMaxResults(50);
		hs_list= mapper.searchHighScore(query);
		return hs_list;
	}

	@Override
	public void signUpHighScore(String pName, String lName, Integer currentSteps, Long currentTime)
	{	
		if(mapper.isEntityExist(new LevelP(lName, "")) == null)
		{
			return;
		}
	
		signUpUser(pName);
		mapper.savePOJO(new HighScoreP(pName, lName, currentSteps, currentTime));

	}

	@Override
	public boolean deleteQuery(POJO idPOJO,String... delete)
	{
	/**
	 * player -> playerName = delete[0]
	 * level  -> levelName  = delete[0] ^ levelPath = delete[1]
	 * HighScore -> playerName = delete[0] , levelName = delete[1] , steps = delete[2] , playerTime = delete[3]
	 */
		POJO pojo = null;
		if(idPOJO instanceof PlayerP)
		{
			pojo = new PlayerP(delete[0]);
			IQuery query = new HighScoreQuery();
			query.setPlayerName(delete[0]);
			query.setMaxResults(Integer.MAX_VALUE);
			List<HighScoreP> list = mapper.searchHighScore(query);
			for(HighScoreP hs : list)
			{
				mapper.deletePOJO(hs);
			}
		}
		else if(idPOJO instanceof LevelP)
		{
			pojo = new LevelP(delete[0],"");
			IQuery query = new HighScoreQuery();
			query.setLevelName(delete[0]);
			query.setMaxResults(Integer.MAX_VALUE);
			List<HighScoreP> list = mapper.searchHighScore(query);
			for(HighScoreP hs : list)
			{
				mapper.deletePOJO(hs);
			}
		}
		else if(idPOJO instanceof HighScoreP)
		{
			pojo = new HighScoreP(delete[0],delete[1], Integer.parseInt(delete[2]),Long.parseLong(delete[3]));
		}
		if(pojo == null)
		{
			return false;
		}
		mapper.deletePOJO(pojo);
		return true;
	}

	@Override
	public void save(POJO line)
	{
		mapper.savePOJO(line);

	}
	@Override
	public PlayerP signUpUser(String pName) 
	{
		PlayerP p=new PlayerP(pName);
		if(mapper.isEntityExist(p) == null)
		{
			mapper.savePOJO(p);
			return p;
		}
		return null;
	}
	@Override
	public List<HighScoreP> getCurrentHighScoreList() 
	{
		return hs_list;
	}

}
