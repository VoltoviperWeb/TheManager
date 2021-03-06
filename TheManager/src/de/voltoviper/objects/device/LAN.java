package de.voltoviper.objects.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.vaadin.ui.Notification;

@Entity
@DiscriminatorValue("LAN")
public class LAN extends Connection {
	

	public LAN(Device deviceA) {
		this.devices.add(deviceA);
	}
	
	public LAN(){
		
	}

	public boolean connectWith(Device deviceB) {
		try {
			deviceB.unconnected(this);
			if(this.devices.size()<=1){
			this.devices.add(deviceB);
			}else{
				throw new Exception("Verbindung hat bereits eine Verbindung");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Nicht erlaubt!", e.getMessage(), Notification.Type.ERROR_MESSAGE);
			return false;
		}
	}
	
	
}
