package de.voltoviper.objects.device.network;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.voltoviper.objects.device.Device;

@Entity
@Inheritance
@DiscriminatorColumn(name = "INTERFACE_TYPE")
@Table(name = "INTERFACE")
/**
 * Das Interface stellt die Klasse dar, um zu ermöglichen, eine Verbindung
 * zwischen Geräten herzustellen.
 * 
 * @author Christoph Nebendahl
 *
 */
public abstract class Interface implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	int interfaceid;

	@ManyToOne
	Device home;

	int position_id;
	String Bezeichnung;

	@OneToOne
	Interface otherinterface;

	/*
	 * Methods
	 */
	/**
	 * Verbindet das Interface mit einem gegenüberliegenden Device, wenn dieses
	 * ein gleiches Interface aufweist.
	 * 
	 * @param device
	 *            Device, das verbunden werden soll
	 * @return Interface, dass verbunden wurde
	 */
	public abstract Interface connectwith(Device device) throws InterfaceException;

	/**
	 * Überprüft, ob das Interface verbunden ist
	 * 
	 * @return Gibt einen boolschen Wert zurück, ob das Gerät verbunden ist.
	 */
	public abstract boolean isconnected();

	/**
	 * Überprüft, ob das Interface mit einer Gegenstelle verbunden wird.
	 * 
	 * @return Gibt das Device zurück, mit dem das Interface verbunden ist.
	 */
	public abstract Device getConnectedDevice() throws InterfaceException;

	
	public abstract boolean unconnect() throws InterfaceException;
	/*
	 * to String
	 */
	@Override
	public String toString() {
		if (this.getClass().equals(LanInterface.class)) {
			return "LAN" + position_id;
		} else if (this.getClass().equals(WlanInterface.class)) {
			return "WLAN" + position_id;
		} else {
			return this.getClass().getSimpleName() + position_id;
		}
	}

	/*
	 * Getter & Setter
	 */

	public int getInterfaceid() {
		return interfaceid;
	}

	public void setInterfaceid(int interfaceid) {
		this.interfaceid = interfaceid;
	}

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public String getBezeichnung() {
		return Bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		Bezeichnung = bezeichnung;
	}

	public Device getHome() {
		return home;
	}

	public void setHome(Device home) {
		this.home = home;
	}

	public Interface getOtherinterface() {
		return otherinterface;
	}

	public void setOtherinterface(Interface otherinterface) {
		this.otherinterface = otherinterface;
	}

}
