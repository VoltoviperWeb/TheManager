package de.voltoviper.objects.benutzer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

import de.voltoviper.web.DBManager;

@Entity
@DiscriminatorValue("Kunde")
public class Kunde extends Benutzer {
	String email, telefon;
	Boolean login;
	
	public Kunde(){
		
	}

	public Kunde(String firstname, String lastname, String email, String telefon, Boolean login) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telefon = telefon;
		this.login = login;
		savekunde(this);
	}

	private void savekunde(Kunde kunde) {
		// TODO Auto-generated method stub
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(kunde);
			tx.commit();
			System.out.println("Kunde "+lastname+" wurde in die Datenbank eingetragen");
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

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}
	

}
