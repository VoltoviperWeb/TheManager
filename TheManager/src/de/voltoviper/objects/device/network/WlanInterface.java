package de.voltoviper.objects.device.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.device.Device;
import de.voltoviper.web.DBManager;

/**
 * Wlan Interface
 * 
 * @author Christoph Nebendahl
 *
 */
@Entity
@DiscriminatorValue("WLAN")
public class WlanInterface extends Interface implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String SSID, passwd;
	boolean sender;

	@OneToMany
	Collection<WlanInterface> clients;

	public WlanInterface(Device device, boolean isSender, String ssid, String wlanpasswd) {
		this.home = device;
		this.sender = isSender;
		this.SSID = ssid;
		this.passwd = wlanpasswd;
		this.clients = new ArrayList<WlanInterface>();
		save();
	}

	public WlanInterface() {

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
			s.saveOrUpdate(this);
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
		WlanInterface f = (WlanInterface) device.connected(this);
		if (f != null) {
			f.addClient(this);
			this.setOtherinterface(f);
			this.save();
			f.save();
		} else {
			throw new InterfaceException("No free Interface");
		}
		return null;
	}

	@Override
	public boolean isconnected() {
		if (this.sender) {
			return false;
		} else {
			if (otherinterface != null) {
				return true;
			}
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
		if (this.isSender()) {
			for (WlanInterface wlan : this.clients) {
				wlan.setOtherinterface(null);
				wlan.save();
				this.clients.clear();
				save();
			}
		} else {
			if (otherinterface != null) {
				((WlanInterface) otherinterface).removeClient(this);
				;
				((WlanInterface) otherinterface).save();
				otherinterface = null;
				save();

			} else {
				throw new InterfaceException("Interface not connected");
			}
		}

		return true;
	}

	public void addClient(WlanInterface other) {
		this.clients.add(other);
	}

	public void removeClient(WlanInterface wlan) {
		this.clients.remove(wlan);
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
