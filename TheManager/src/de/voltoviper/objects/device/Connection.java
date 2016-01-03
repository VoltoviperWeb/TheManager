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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance
@DiscriminatorColumn(name = "CONNECTION_TYPE")
@Table(name = "CONNECTION")
public abstract class Connection {
	@Id
	@GeneratedValue
	int conn_id;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "connections")
	Collection<Device> devices = new ArrayList<>();

	public boolean connected() {
		if(devices.size()==2){
			return true;
		}else{
			return false;
		}
	}

	public abstract boolean connectWith(Device deviceB);

	/*
	 * Getter and Setter
	 */
	
	
}
