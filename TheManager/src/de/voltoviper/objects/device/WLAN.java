package de.voltoviper.objects.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WLAN")
public class WLAN extends Connection {
	String ssid;
	

	public WLAN(Device deviceA) {
		this.devices.add(deviceA);
	}
	
	public WLAN(){
		
	}

	@Override
	public boolean connectWith(Device deviceB) {
		// TODO Auto-generated method stub
		return false;
	}

}
