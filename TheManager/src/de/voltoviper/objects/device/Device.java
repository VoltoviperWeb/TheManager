package de.voltoviper.objects.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.network.Interface;
import de.voltoviper.objects.device.network.LanInterface;
import de.voltoviper.objects.device.network.WlanInterface;
import de.voltoviper.objects.standards.Device_Status;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.login.LoginView;

@Entity
@Table(name = "DEVICE")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(LoginView.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int device_id;

	String bezeichnung;
	Device_Typ typ;

	@ManyToOne
	Kunde besitzer;
	@ManyToOne
	Hersteller hersteller;

	Device_Status status;

	@OneToMany(mappedBy = "home")
	Collection<Interface> interfaces = new ArrayList<Interface>();

	public Device() {

	}

	public Device(Device_Typ typ, Kunde besitzer, Hersteller hersteller, int lan, int wlan, String bezeichnung) {
		this.typ = typ;
		this.besitzer = besitzer;
		this.hersteller = hersteller;
		this.bezeichnung = bezeichnung;
		this.status = Device_Status.OK;
		saveDevice(this);
		for(int i=0;i<lan;i++){
			interfaces.add(new LanInterface(this, i+1));
		}
		
		for(int i=0;i<wlan;i++){
			interfaces.add(new WlanInterface(this));
		}
		saveDevice(this);
		


	}

	/**
	 * Speichert das Übergebene Objekt in der Datenbank.
	 * 
	 * @param device
	 *            Objekt vom Typ Device, dass in der Datenbank gespeichert
	 *            werden soll.
	 */
	private void saveDevice(Device device) {
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.saveOrUpdate(device);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
	}

	
	public Interface connected(Interface f) {
		for (Interface i : interfaces) {
			if (!i.isconnected()) {
				if (i.getClass().equals(f.getClass())) {
					return i;
				}
			}
		}

		return null;
	}

	public boolean isconnected() {
		for (Interface i : interfaces) {
			if (i.isconnected()) {
				return true;
			}

		}
		return false;
	}

	/*
	 * toString Override
	 */
	@Override
	public String toString() {
		return bezeichnung;
	}

	/*
	 * Getter and Setter
	 */

	public int getDevice_id() {
		return device_id;
	}

	public Collection<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Collection<Interface> interfaces) {
		this.interfaces = interfaces;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + device_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (device_id != other.device_id)
			return false;
		return true;
	}

}
