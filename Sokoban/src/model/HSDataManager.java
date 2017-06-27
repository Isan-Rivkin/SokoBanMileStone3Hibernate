package model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import metadata.DBLevelModel;
import metadata.FMGenerator;
import metadata.HSQueryModel;
import metadata.HighScoreModel;
import metadata.METADATA;
import metadata.PlayerModel;
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
        GsonBuilder b = new GsonBuilder();
        Gson g = b.create();
        HSQueryModel hsQModel = FMGenerator.generateHSQueryModel((HighScoreQuery)query);
        METADATA metaData = new METADATA();
        metaData.setId(Protocol.HS_SEARCH);
        String jsonMetaDataString = g.toJson(metaData);
        String jsonHSQModelString = g.toJson(hsQModel);
        
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
        ServiceRequester sr = new ServiceRequester();
        Gson g = sr.getGson();
        METADATA m = new METADATA();
        m.setId(Protocol.HS_SIGNUP_HS);
        HighScoreP p = new HighScoreP(pName, lName, currentSteps, currentTime);
        HighScoreModel hsModel = FMGenerator.generateHighScoreModel(p);
        String objString = g.toJson(hsModel);
        String mdString = g.toJson(m);
        ServiceResponse sres = sr.requestService(objString, mdString, Protocol.serverIP, Protocol.serverPort);
    }
	@Override

    public boolean deleteQuery(POJO idPOJO,String... delete)
    {
    /**
     * player -> playerName = delete[0]
     * level  -> levelName  = delete[0] ^ levelPath = delete[1]
     * HighScore -> playerName = delete[0] , levelName = delete[1] , steps = delete[2] , playerTime = delete[3]
     */
        ServiceRequester sr = new ServiceRequester();
        Gson g = sr.getGson(); 
        METADATA m = new METADATA();
        m.setId(Protocol.HS_DELETE_POJO);
        String metadataString = "";
        String objString = "";
        POJO pojo = null;

        if(idPOJO instanceof PlayerP)
        {
            PlayerP p = new PlayerP(delete[0]);
            pojo = p;
            PlayerModel pModel = FMGenerator.generatePlayerModel(p);
            objString = g.toJson(pModel);
            m.setAdditionalInfo(Protocol.POJO_PLAYER);
            metadataString = g.toJson(m);

            List<HighScoreP> list = search("p", "", delete[0],"");

            for(HighScoreP hs : list)
            {

                METADATA m1 = new METADATA();
                m1.setId(Protocol.HS_DELETE_POJO);
                HighScoreModel hsModel = FMGenerator.generateHighScoreModel(hs);
                m1.setAdditionalInfo(Protocol.POJO_HS);
                String objListString = g.toJson(hsModel);
                String mdString = g.toJson(m1);
                ServiceResponse sres = sr.requestService(objListString, mdString, Protocol.serverIP, Protocol.serverPort);   
                }
        }
        else if(idPOJO instanceof LevelP)
        {
            LevelP p = new LevelP(delete[0],"");
            pojo = p;
            DBLevelModel lModel = FMGenerator.generateDBLevelModel(p);
            objString = g.toJson(lModel);
            m.setAdditionalInfo(Protocol.POJO_LEVEL);
            metadataString = g.toJson(m);

            List<HighScoreP> l = search("l",delete[0], "", "");
           
            for(HighScoreP hs : l)
            {
                METADATA m1 = new METADATA();
                m1.setAdditionalInfo(Protocol.POJO_HS);
                HighScoreModel hsModel = FMGenerator.generateHighScoreModel(hs);
                m1.setAdditionalInfo(Protocol.POJO_HS);
                String objListString = g.toJson(hsModel);
                String mdString = g.toJson(m);
                ServiceResponse sres = sr.requestService(objListString, mdString, Protocol.serverIP, Protocol.serverPort);
//              mapper.deletePOJO(hs);
            }
        }
        else if(idPOJO instanceof HighScoreP)
        {
            pojo = new HighScoreP(delete[0],delete[1], Integer.parseInt(delete[2]),Long.parseLong(delete[3]));
            HighScoreP p =(HighScoreP)pojo;
            HighScoreModel hsModel = FMGenerator.generateHighScoreModel(p);
            objString = g.toJson(hsModel);
            m.setAdditionalInfo(Protocol.POJO_HS);
            metadataString = g.toJson(m);
        }
        if(pojo == null)
        {
            return false;
        }
        ServiceResponse sres = sr.requestService(objString, metadataString, Protocol.serverIP, Protocol.serverPort);        
//      mapper.deletePOJO(pojo);
        return true;
    }

	@Override
	public void save(POJO line)
    {
        ServiceRequester sr = new ServiceRequester();
        Gson g = sr.getGson();
        METADATA m = new METADATA();
        m.setId(Protocol.HS_SAVE_POJO);
        String objString = "";
        POJO pp = line;
        if (pp instanceof PlayerP)
        {
            PlayerP p = (PlayerP)pp; 
            PlayerModel pModel = FMGenerator.generatePlayerModel(p);
            m.setAdditionalInfo(Protocol.POJO_PLAYER);
            objString = g.toJson(pModel);
            
        }
        if (pp instanceof LevelP)
        {
            LevelP p =(LevelP)pp;
            
            DBLevelModel lModel = FMGenerator.generateDBLevelModel(p);
            m.setAdditionalInfo(Protocol.POJO_LEVEL);
            objString = g.toJson(lModel);
        }
        if (pp instanceof HighScoreP)
        {
            HighScoreP p = (HighScoreP)pp;
            HighScoreModel hsModel = FMGenerator.generateHighScoreModel(p);
            m.setAdditionalInfo(Protocol.POJO_HS);
            objString = g.toJson(hsModel);
        }
        
        String metaDataString = g.toJson(m);
        
        ServiceResponse sp = sr.requestService(objString, metaDataString, Protocol.serverIP,Protocol.serverPort);

//      mapper.savePOJO(line);
    }
	
	
	@Override
	public PlayerP signUpUser(String pName) 
    {
        METADATA m = new METADATA();
        ServiceRequester sr = new ServiceRequester();
        Gson g = sr.getGson();
        m.setId(Protocol.HS_SIGNUP_P);
        PlayerP p = new PlayerP(pName);
        PlayerModel pModel = FMGenerator.generatePlayerModel(p);
        String objString = g.toJson(pModel);
        String mdString = g.toJson(m);
        ServiceResponse sres = sr.requestService(objString, mdString, Protocol.serverIP, Protocol.serverPort);
        return null;
    }
	@Override
	public List<HighScoreP> getCurrentHighScoreList() 
	{
		return hs_list;
	}

}
