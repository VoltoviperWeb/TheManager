package de.voltoviper.objects.device;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance
@DiscriminatorColumn(name = "CONNECTION_TYPE")
@Table(name = "CONNECTION")
public abstract class Connection {
	@Id
	@GeneratedValue
	int id;
	@ManyToOne
	Device deviceA;
	Device deviceB;
	
	Collection<Device> devices = new ArrayList<>();

	public boolean connected() {
		if (deviceA != null && deviceB != null) {
			return true;
		} else {
			return false;
		}
	}

	public abstract boolean connectWith(Device deviceB);

	/*
	 * Getter and Setter
	 */
	
	public Device getDeviceA() {
		return deviceA;
	}

	public void setDeviceA(Device deviceA) {
		this.deviceA = deviceA;
	}

	public Device getDeviceB() {
		return deviceB;
	}

	public void setDeviceB(Device deviceB) {
		this.deviceB = deviceB;
	}

}
