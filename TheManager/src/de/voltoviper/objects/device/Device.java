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
import de.voltoviper.objects.standards.Device_Status;
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
	
	Device_Status status;
	
	
	public Device(){
		
	}
	
	public Device(Device_Typ typ, Kunde besitzer, Hersteller hersteller){
		this.typ = typ;
		this.besitzer=besitzer;
		this.hersteller = hersteller;
		this.status = Device_Status.OK;
		saveDevice(this);
	}

	private void saveDevice(Device device) {
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

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Device_Typ getTyp() {
		return typ;
	}

	public void setTyp(Device_Typ typ) {
		this.typ = typ;
	}

	public Kunde getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Kunde besitzer) {
		this.besitzer = besitzer;
	}

	public Hersteller getHersteller() {
		return hersteller;
	}

	public void setHersteller(Hersteller hersteller) {
		this.hersteller = hersteller;
	}

	public Device_Status getStatus() {
		return status;
	}

	public void setStatus(Device_Status status) {
		this.status = status;
	}
	

}
