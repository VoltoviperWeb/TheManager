package de.voltoviper.objects.device;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;

@Entity  
@Table(name="Device")
public class Device implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	int device_id;
	
	String bezeichnung;
	Device_Typ typ;
	
	@ManyToOne
	Kunde besitzer;
	@ManyToOne
	Hersteller hersteller;
	
	
	public Device(){
		
	}
	
	public Device(Device_Typ typ, Kunde besitzer, Hersteller hersteller){
		this.typ = typ;
		this.besitzer=besitzer;
		this.hersteller = hersteller;
		saveDevice(this);
	}

	private void saveDevice(Device device) {
		// TODO Auto-generated method stub
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(device);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
}
