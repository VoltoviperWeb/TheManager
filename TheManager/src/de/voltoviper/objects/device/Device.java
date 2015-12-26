package de.voltoviper.objects.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity  
@Table(name="Device")  
@Inheritance(strategy=InheritanceType.JOINED)
public class Device {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	int device_id;
	
	
}
