package de.voltoviper.objects.network;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.voltoviper.objects.device.Device;
@Entity
@Table(name = "NetworkDevice")
@PrimaryKeyJoinColumn(name = "networkdevice_id", referencedColumnName = "device_id")

public class NetworkDevice extends Device {
	String ip;
	String mac_adresse;
}
