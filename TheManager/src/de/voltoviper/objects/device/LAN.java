package de.voltoviper.objects.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LAN")
public class LAN extends Connection {
	

	public LAN(Device deviceA) {
		this.deviceA = deviceA;
	}

	public boolean connectWith(Device deviceB) {
		try {
			deviceB.unconnected(this);
			this.deviceB = deviceB;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
