package de.voltoviper.objects.device.network;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.device.Device;
import de.voltoviper.web.DBManager;

/**
 * Wlan Interface
 * @author Christoph Nebendahl
 *
 */
@Entity
@DiscriminatorValue("WLAN")
public class WlanInterface extends Interface implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String SSID, passwd;
	boolean sender;
	ArrayList<WlanInterface> clients;
	
	
	public WlanInterface(Device device, boolean isSender) {
		this.home=device;
		this.sender = isSender;
		save();
	}
	
	public WlanInterface(){

	}

	/**
	 * Speichert das Übergebene Objekt in der Datenbank.
	 * 
	 * @param device
	 *            Objekt vom Typ Device, dass in der Datenbank gespeichert
	 *            werden soll.
	 */
	protected void save() {
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(this);
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
	public Interface connectwith(Device device) throws InterfaceException {
		Interface f = device.connected(this);
		if (f != null) {
			f.setOtherinterface(this);
			this.setOtherinterface(f);
			this.save();
			((WlanInterface) f).save();
		} else {
			throw new InterfaceException("No free Interface");
		}
		return null;
	}


	@Override
	public boolean isconnected() {
		if (otherinterface != null) {
			return true;
		}
		return false;
	}


	@Override
	public Device getConnectedDevice() throws InterfaceException {
		if (otherinterface != null) {
			return otherinterface.getHome();
		} else {
			throw new InterfaceException("Interface not connected");
		}
	}
	
	@Override
	public boolean unconnect() throws InterfaceException {
		if (otherinterface != null) {
			otherinterface.setOtherinterface(null);
			((LanInterface) otherinterface).save();
			otherinterface = null;
			save();

		} else {
			throw new InterfaceException("Interface not connected");
		}
		return true;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isSender() {
		return sender;
	}

	public void setSender(boolean sender) {
		this.sender = sender;
	}

	
	
	
}
