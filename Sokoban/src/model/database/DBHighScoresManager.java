package model.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DBHighScoresManager implements DBManager 
{

	private SessionFactory factory;
	
	public DBHighScoresManager() 
	{
		Configuration config = new Configuration();
		config.configure();
		factory=config.buildSessionFactory();
	}
	public void saveHighscore(HighScoreP hs)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(hs);
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


	}
	public void saveLevel(LevelP level)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(level);
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

	}
	public void savePlayer(PlayerP player)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(player);
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

	}
	public void saveToDB(Student s)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(s);
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
	}
}
