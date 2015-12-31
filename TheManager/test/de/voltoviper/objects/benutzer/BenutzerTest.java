package de.voltoviper.objects.benutzer;

import static org.junit.Assert.*;

import java.util.List;

import javax.servlet.ServletContextEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;

import de.voltoviper.web.DBManager;

public class BenutzerTest {
	static Mitarbeiter mitarbeiter;
	static List<Benutzer> user;
	@BeforeClass
	public static void setUp() throws Exception {
		DBManager manager = new DBManager();
		manager.contextInitialized(null);
		mitarbeiter = new Mitarbeiter("user", "test", "Christoph", "Nebendahl", true);

	}

	@Test
	public void testSave() {
		mitarbeiter.save(mitarbeiter);
		
		Session session = DBManager.getFactory().openSession();
		Criteria cr = session.createCriteria(Benutzer.class);
		cr.add(Restrictions.eq("login", true));
		cr.add(Restrictions.eq("username", "user"));
		user = cr.list();
		assertEquals(user.size(), 1);
		assertEquals(user.get(1).getLastname(), "Nebendahl");
	}

	@Test
	public void testUpdate() {
		
		mitarbeiter.setLastname("Rathje");
		mitarbeiter.update();
		Session session = DBManager.getFactory().openSession();
		Criteria cr = session.createCriteria(Benutzer.class);
		cr.add(Restrictions.eq("login", true));
		cr.add(Restrictions.eq("username", "user"));
		List<Benutzer> user = cr.list();
		assertEquals(user.size(), 1);
		assertEquals(user.get(1).getLastname(), "Nebendahl");
		
	}

	@Test
	public void testSetPassword() {
		fail("Not yet implemented");
	}

}
