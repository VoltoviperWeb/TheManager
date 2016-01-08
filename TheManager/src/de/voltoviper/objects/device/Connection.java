package de.voltoviper.objects.device;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance
@DiscriminatorColumn(name = "CONNECTION_TYPE")
@Table(name = "CONNECTION")
public abstract class Connection {
	@Id
	@GeneratedValue
	int conn_id;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "connections")
	Collection<Device> devices = new ArrayList<>();

	public boolean connected() {
		if (devices.size() == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Stellt eine Verbindung zwischen der Verbindung (die einem Device
	 * zugeordnet ist und einem anderen Device her.
	 * 
	 * @param deviceB
	 *            Device, dass mit dieser Verbindung verbunden werden soll
	 * @return boolscher Wert, ob die Verbindung funktioniert.
	 */
	public abstract boolean connectWith(Device deviceB);

	/*
	 * Getter and Setter
	 */

	protected Device getDeviceA() {
		for (Device c : devices) {
			return c;
		}
		return null;

	}

	protected Device getDeviceB() {
		int i = 0;
		for (Device c : devices) {
			i++;
			if (i == 2) {
				return c;
			}
		}
		return null;
	}

	public Collection<Device> getDevices() {

		return this.devices;
	}
/**
 * Überprüft, ob das übergebene Device mit dieser Verbindung verbunden ist.
 * @param device Device das überpüft werden soll
 * @return bolscher Wert.
 */
	public boolean connectedWidth(Device device) {
		for (Device d : devices) {
			if (d.equals(device)) {
				return true;

			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
