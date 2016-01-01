package de.voltoviper.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.voltoviper.web.login.LoginView;

public class DBManager implements ServletContextListener {
	/**
	 * 
	 */
	static SessionFactory factory;
	private static final Logger logger = LogManager.getLogger(DBManager.class);

	public DBManager() {
		super();
	}

	public static SessionFactory getFactory() {
		return factory;
	}

	private void init() {
		logger.trace("DBManager init start!");
		factory = new Configuration().configure().buildSessionFactory();
		logger.trace("DBManager init finish!");
	}

	private void destroy() {
		logger.trace("DBManager destroy start");
		if (factory != null)
			factory.close();
		logger.trace("DBManager destroy complete");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		destroy();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		init();

	}
}
