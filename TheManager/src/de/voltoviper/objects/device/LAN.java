package de.voltoviper.objects.device;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vaadin.ui.Notification;

import de.voltoviper.web.DBManager;
import de.voltoviper.web.login.LoginView;

@Entity
@DiscriminatorValue("LAN")
public class LAN extends Connection {

	private static final Logger logger = LogManager.getLogger(LAN.class);

	public LAN(Device deviceA) {
		this.devices.add(deviceA);
	}

	public LAN() {

	}
	@Override
	public boolean connectWith(Device deviceB) {
		boolean connected = false;
		Transaction tx = null;

		Session session = DBManager.getFactory().openSession();
		try {
			deviceB.unconnected(this);
			if (this.devices.size() <= 1) {
				this.devices.add(deviceB);
			} else {
				throw new Exception("Verbindung hat bereits eine Verbindung");
			}
			connected = true;
			tx = session.beginTransaction();
			session.update(this);
			session.update(deviceB);
			tx.commit();

			logger.info(getDeviceA() + " wurde mit " + deviceB.toString() + " verbunden!");
			Notification.show("Verbunden", getDeviceA() + " wurde mit " + deviceB.toString() + " verbunden!",
					Notification.Type.TRAY_NOTIFICATION);

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error(getDeviceA() + " konnte nicht mit " + deviceB.toString() + " verbunden werden!");
			Notification.show("Fehler", e.getMessage(), Notification.Type.ERROR_MESSAGE);
			connected = false;
		} finally {
			session.close();
		}
		return connected;
	}

}
