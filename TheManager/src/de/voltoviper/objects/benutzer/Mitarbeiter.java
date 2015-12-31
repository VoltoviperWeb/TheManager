package de.voltoviper.objects.benutzer;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.system.EncryptPassword;
import de.voltoviper.web.DBManager;

@Entity
@DiscriminatorValue("Mitarbeiter")
public class Mitarbeiter extends Benutzer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Mitarbeiter(){
		
	}
	
	public Mitarbeiter(String username, String password, String firstname, String lastname, boolean login){
		this.username = username;
		try {
			this.password = EncryptPassword.SHA512(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.firstname = firstname;
		this.lastname = lastname;
		this.login = login;
		save(this);
	}
	
	@Override
	public void save(Benutzer kunde) {
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(kunde);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		
	}

	@Override
	public void update() {
		Session session = DBManager.getFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(this);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}
		
	}

	
	
}
