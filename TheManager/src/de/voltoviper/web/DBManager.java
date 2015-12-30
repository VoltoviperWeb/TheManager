package de.voltoviper.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBManager implements ServletContextListener {
	/**
	 * 
	 */
	static SessionFactory factory;

	public DBManager() {
		super();
	}

	public static SessionFactory getFactory() {
		return factory;
	}

	private void init() {
		System.out.println("----------");
		System.out.println("---------- DBManager Initializing ----------");
		System.out.println("----------");
		factory = new Configuration().configure().buildSessionFactory();

		System.out.println("----------");
		System.out.println("---------- DBManager Initialized successfully ----------");
		System.out.println("----------");
	}

	private void destroy() {
		System.out.println("----------");
		System.out.println("---------- DBManager start closing ----------");
		System.out.println("----------");
		if (factory != null)
			factory.close();
		System.out.println("----------");
		System.out.println("---------- DBManager closing successfully ----------");
		System.out.println("----------");
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
