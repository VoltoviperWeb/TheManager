package de.voltoviper.objects.device.network;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.device.Device;
import de.voltoviper.web.DBManager;

@Entity
@DiscriminatorValue("LAN")
public class LanInterface extends Interface implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LanInterface(Device device, int position) {
		this.setHome(device);
		this.setPosition_id(position);
		save();
	}

	public LanInterface() {

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
		Interface f = device.connected(this);
		if (f != null) {
			f.setOtherinterface(this);
			this.setOtherinterface(f);
			this.save();
			((LanInterface) f).save();
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

}
