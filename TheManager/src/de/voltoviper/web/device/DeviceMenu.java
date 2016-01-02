package de.voltoviper.web.device;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.data.Item;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Connection;
import de.voltoviper.objects.device.Device;
import de.voltoviper.objects.device.LAN;
import de.voltoviper.objects.device.WLAN;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;

public class DeviceMenu extends BorderLayout {
	Accordion accordion;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceMenu(Device device) {
		init(device);
	}

	private void init(Device device) {
		accordion = new Accordion();

		initTabAllgemein(device);
		initTabVerbindung(device);
		initTabTicket(device);

		addComponent(accordion, Constraint.CENTER);

	}

	private void initTabTicket(Device device) {
		Layout ticket = new VerticalLayout();
		accordion.addTab(ticket, "Tickets", null);

	}

	private void initTabVerbindung(Device device) {
		Layout verbindung = new FormLayout();
		
		ComboBox lan = new ComboBox("Lan Verbindungen");
		ComboBox wlan = new ComboBox("WLan Verbindungen");
		for(Connection c: device.getConnections()){
			if(c.getClass().equals(LAN.class)){
				lan.addItem(c);
			}else if(c.getClass().equals(WLAN.class)){
				wlan.addItem(c);
			}
		}
		verbindung.addComponent(lan);
		verbindung.addComponent(wlan);
		
		for(int i=0; i<device.getConnections().size();i++){
			
		}
		accordion.addTab(verbindung, "Verbindung", null);

	}

	private void initTabAllgemein(Device device) {
		// TODO Auto-generated method stub
		Layout allgemeines = new FormLayout();
		TextField bezeichnung = new TextField("Bezeichnung");
		bezeichnung.setValue(device.getBezeichnung());
		allgemeines.addComponent(bezeichnung);

		ComboBox typ = new ComboBox("Typ");
		for (Device_Typ device_typ : Device_Typ.values()) {
			typ.addItem(device_typ);
			if (device_typ.equals(device.getTyp())) {
				typ.setValue(device_typ);
			}
		}
		allgemeines.addComponent(typ);

		ComboBox hersteller = new ComboBox("Hersteller");
		Session session = DBManager.getFactory().openSession();
		try {
			List<Hersteller> hersteller_list = session.createCriteria(Hersteller.class).list();
			for (Hersteller k : hersteller_list) {

				hersteller.addItem(k);
				if (k.equals(device.getHersteller())) {
					hersteller.setValue(k);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		allgemeines.addComponent(hersteller);

		ComboBox besitzer = new ComboBox("Besitzer");
		Session s = DBManager.getFactory().openSession();
		try {
			List<Kunde> kunden = s.createCriteria(Kunde.class).list();
			for (Kunde k : kunden) {

				besitzer.addItem(k);
				if (k.equals(device.getBesitzer())) {
					besitzer.setValue(k);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		allgemeines.addComponent(besitzer);

		accordion.addTab(allgemeines, "Allgemeines", null);
	}
}
