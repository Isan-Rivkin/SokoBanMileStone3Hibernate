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
