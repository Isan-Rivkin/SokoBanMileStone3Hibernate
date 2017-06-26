package model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import metadata.FMGenerator;
import metadata.HSQueryModel;
import metadata.HighScoreModel;
import metadata.METADATA;
import metadata.Protocol;
import model.database.HighScoreP;
import model.database.HighScoreQuery;
import model.database.IDBMapper;
import model.database.IQuery;
import model.database.LevelP;
import model.database.POJO;
import model.database.PlayerP;
import model.services.ServiceRequester;
import model.services.ServiceRequester.ServiceResponse;

public class HSDataManager implements IDataManager 
{
	private IDBMapper mapper;
	private List<HighScoreP> hs_list;
	
	public HSDataManager(IDBMapper mapper) 
	{
		this.mapper=mapper;
	}
	@Override
	public List<HighScoreP> search(String classify, String lName,String pName,String orderType) 
	{
		/**
		 * filer =l,p , playerName/levelName , orderby="","steps","time","name"
		 */
		IQuery query = new HighScoreQuery();
		if(classify.equals(""))
		{
			query.setLevelName(lName);
			query.setPlayerName(pName);
		}
		else if(classify.equals("l"))
		{
			query.setLevelName(lName);
		}
		else if(classify.equals("p"))
		{
			query.setPlayerName(pName);
		}
		if(orderType.equals("") && classify.equals("l"))
		{
			query.initLexiLevelName();
		}
		else if(orderType.equals("steps"))
		{
			query.initOrderBySteps();
		}
		else if(orderType.equals("time"))
		{
			query.initOrderByTime();
		}
		query.setMaxResults(50);
		//hs_list= mapper.searchHighScore(query);
		GsonBuilder b = new GsonBuilder();
		Gson g = b.create();
		HSQueryModel hsQModel = FMGenerator.generateHSQueryModel((HighScoreQuery)query);
		METADATA metaData = new METADATA();
		metaData.setId(Protocol.HS_SEARCH);
		String jsonMetaDataString = g.toJson(metaData);
		String jsonHSQModelString = g.toJson(hsQModel);
		//...
		ServiceRequester req = new ServiceRequester();
		ServiceResponse res = req.requestService(jsonHSQModelString, jsonMetaDataString, Protocol.serverIP, Protocol.serverPort);
		Type listType = new TypeToken<ArrayList<HighScoreModel>>(){}.getType();
		List<HighScoreModel> query_model_list = new Gson().fromJson(res.getJsonObject(), listType);
		hs_list = FMGenerator.getHSPojoList(query_model_list);
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
