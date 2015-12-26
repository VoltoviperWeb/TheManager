package de.voltoviper.objects.device;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PC")
@PrimaryKeyJoinColumn(name = "pc_id", referencedColumnName = "device_id")
public class PC extends Device{
	
}
