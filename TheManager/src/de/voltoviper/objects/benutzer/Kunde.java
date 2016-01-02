package de.voltoviper.objects.benutzer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.device.Device;
import de.voltoviper.web.DBManager;

@Entity
@DiscriminatorValue("Kunde")
public class Kunde extends Benutzer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String email, telefon;
	

	@OneToMany(mappedBy = "besitzer")
	Collection<Device> devices = new ArrayList<>();

	public Kunde() {

	}

	public Kunde(String firstname, String lastname, String email, String telefon, Boolean login) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telefon = telefon;
		this.login = login;
		save(this);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	
	

}
