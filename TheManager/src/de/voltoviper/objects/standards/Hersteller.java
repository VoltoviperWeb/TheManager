package de.voltoviper.objects.standards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.device.Device;
import de.voltoviper.web.DBManager;

@Entity  
@Table(name="HERSTELLER")
public class Hersteller implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	int id;
	
	String name;
	
	@OneToMany(mappedBy="hersteller")
	Collection<Device> devices = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Device> getDevices() {
		return devices;
	}

	public void setDevices(Collection<Device> devices) {
		this.devices = devices;
	}

	public Hersteller() {
		super();
	}
	public Hersteller(String name){
		this.name = name;
		saveHersteller(this);
	}

	private void saveHersteller(Hersteller hersteller) {
		// TODO Auto-generated method stub
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(hersteller);
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
	public String toString() {
		return name;
	}
	
	
	
	
	
}
