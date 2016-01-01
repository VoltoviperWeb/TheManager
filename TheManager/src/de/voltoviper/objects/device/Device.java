package de.voltoviper.objects.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.standards.Device_Status;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.login.LoginView;

@Entity  
@Table(name="DEVICE")
public class Device implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(LoginView.class);

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	int device_id;
	
	String bezeichnung;
	Device_Typ typ;
	
	@ManyToOne
	Kunde besitzer;
	@ManyToOne
	Hersteller hersteller;
	
	Device_Status status;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CONN_DEVICE", joinColumns = { @JoinColumn(name = "device_id") }, inverseJoinColumns = { @JoinColumn(name = "conn_id") })
	Collection<Connection> connections = new ArrayList<>();
	
	public Device(){
		
	}
	
	public Device(Device_Typ typ, Kunde besitzer, Hersteller hersteller, int lan, int wlan, String bezeichnung){
		this.typ = typ;
		this.besitzer=besitzer;
		this.hersteller = hersteller;
		this.bezeichnung = bezeichnung;
		this.status = Device_Status.OK;
	
		for(int i=0;i<lan;i++){
			connections.add(new LAN(this));
		}
		for(int i=0;i<wlan;i++){
			connections.add(new WLAN(this));
		}
		
		saveDevice(this);
		
	}
/**
 * Speichert das �bergebene Objekt in der Datenbank.
 * @param device Objekt vom Typ Device, dass in der Datenbank gespeichert werden soll.
 */
	private void saveDevice(Device device) {
		Session s = DBManager.getFactory().openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(device);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
	/**
	 * �berpr�ft, ob das Device mit irgendetwas anderem verbunden ist.
	 * @return
	 */
	
	/**
	 * �berpr�ft, ob das Device mit irgendeinem anderen Ger�t verbunden ist.
	 * @return Gibt einen boolschen Wert zur�ck, ob eines der Connections verbunden ist.
	 */
	public boolean connected(){
		for(Connection c: connections){
			if(c.connected()){
				return true;
			}
		}
		return false;
	}
	/**
	 * �berpr�ft ob eine Verbindung vorhanden ist, die nicht verbunden ist. Diese wird durch die �bergebene ersetzt. 
	 * @param conn Connection, die eingetragen werden soll
	 * @return Boolscher Wert, ob das eintragen funktioniert hat
	 * @throws Exception wirft eine Exception mit der Nachricht, dass keine Verbindung ausgetauscht werden kann.
	 */
	public boolean unconnected(Connection conn) throws Exception {
		
		
		ArrayList<Connection> speicher = new ArrayList<>();
		for(Connection c: connections){
			speicher.add(c);
		}
		for(Connection c: speicher){
			if(c.getClass().equals(conn.getClass())){
				if(!c.connected()){
					connections.remove(c);
					connections.add(conn);
					return true;
				}
			}
		}
		throw new Exception("No unconnected Interface");
		
	}
	
	/*
	 * toString Override
	 */
	@Override
	public String toString() {
		return bezeichnung;
	}
	
	
	/*
	 * Getter and Setter
	 */

	public int getDevice_id() {
		return device_id;
	}

	

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Device_Typ getTyp() {
		return typ;
	}

	public void setTyp(Device_Typ typ) {
		this.typ = typ;
	}

	public Kunde getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Kunde besitzer) {
		this.besitzer = besitzer;
	}

	public Hersteller getHersteller() {
		return hersteller;
	}

	public void setHersteller(Hersteller hersteller) {
		this.hersteller = hersteller;
	}

	public Device_Status getStatus() {
		return status;
	}

	public void setStatus(Device_Status status) {
		this.status = status;
	}
	

}
