package model.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class SokoDBMapper implements IDBMapper 
{

	final private static String PLAYERS_TABLE="Players";
	final private static String LEVELS_TABLE="Levels";
	final private static String HIGHSCORE_TABLE="HighScores";
	private SessionFactory factory;

	public SokoDBMapper() 
	{
		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();
	}
	
	@Override
	public HighScoreP[] searchHighScore(IQuery query)
	{
		Session session=factory.openSession(); 
		HighScoreP[] res = null;
//		Query q = session.createQuery("From model.database.HighScoreP hs WHERE (:levelName IS NULL OR hs.levleName = :levelName) AND "
//				+ "(:playerName IS NULL OR hs.playerName = :playerName) ORDER BY hs."
//				+(query.getOrderBy()));
//		Query q = session.createQuery("From "+HIGHSCORE_TABLE+" hs WHERE (:levelName IS NULL OR hs.levleName = :levelName) AND "
//				+ "(:playerName IS NULL OR hs.playerName = :playerName) ORDER BY hs."
//				+(query.getOrderBy()));
		Query q = session.createQuery("From "+HIGHSCORE_TABLE+" hs WHERE "+
				 "(:levelName IS NULL OR hs.levleName = :levelName) AND "
				 		+ "(:playerName IS NULL OR hs.playerName = :playerName)");
		//TODO::in create table allow nulls in fields otherwise cannot traverse
		q.setMaxResults(query.getMaxResults());
	//	q.setParameter("levelName", query.getLevelName());
		q.setParameter("playerName", query.getPlayerName());
		List list  = q.list();
		res = new HighScoreP[list.size()];
		res = (HighScoreP[]) list.toArray(res);
		return res;
	}

	@Override
	public List<LevelP> getAllLevels() {
		return (List<LevelP>) this.getAllrows(LEVELS_TABLE);
	}

	@Override
	public List<PlayerP> getAllPlayers() {
		return (List<PlayerP>) this.getAllrows(PLAYERS_TABLE);
	}

	@Override
	public List<HighScoreP> getAllHighScores() {
		return (List<HighScoreP>) this.getAllrows(HIGHSCORE_TABLE);
	}

	@Override
	public boolean savePOJO(POJO pojo) {
		Session session=null;
		Transaction tx=null;
		boolean worked = true;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();
		}
		catch(HibernateException e)
		{
			if(tx != null)
			{
				tx.rollback();
				worked = false;
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return worked;
	}

	
	@Override
	public boolean deletePOJO(POJO pojo) {
		Transaction tx = null;
		boolean worked = true;
		Session session = null;
		//TODO::
		try {
		session = factory.openSession();
		tx = session.beginTransaction();
		session.delete(pojo);
		tx.commit();
		}
		catch(HibernateException he)
		{
			if(tx!=null)
			{
			tx.rollback();
			}
			worked = false;
		}
		finally
		{
			if (session!=null)
			{
			session.close();
			}
		}
		
		return worked;
	}


	@Override
	public boolean isEntityExist(POJO pojo) 
	{
		//TODO::
//		Session session=null;
//		Transaction tx=null;
//		try
//		{
//			session=factory.openSession();
//			tx=session.beginTransaction();
//			session.get();
//		}
//		catch(HibernateException e)
//		{
//			if(tx!= null)
//			{
//				tx.rollback();
//			}
//		}
//		finally
//		{
//			if(session != null)
//			{
//				session.close();
//			}
//		}
		return false;
	}

	@Override
	public List<? extends POJO> getAllrows(String tableName) {
		Transaction tx=null;
		Session session = null;
		List<POJO> res = null;
		try
		{
		session=factory.openSession(); 
	     tx=session.beginTransaction();
		Query q = session.createQuery("from "+tableName+" p");
		res  = q.list();
		tx.commit();
	}
	catch(HibernateException e)
	{
		if(tx != null)
		{
			tx.rollback();
		}
	}
	finally
	{
		if(session != null)
		{
			session.close();
		}
	}
		return res;
	}

}
