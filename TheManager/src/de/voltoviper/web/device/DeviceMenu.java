package de.voltoviper.web.device;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

import com.vaadin.ui.TextField;

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
	BorderLayout layout;
	private static final Logger logger = LogManager.getLogger(DeviceMenu.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceMenu(Device device) {
		this.layout = this;
		init(device);

	}

	/**
	 * Initialisiert das accordion für die Device Übersicht
	 * 
	 * @param device
	 */
	private void init(Device device) {
		accordion = new Accordion();

		initTabAllgemein(device);
		initTabVerbindung(device);
		initTabTicket(device);

		addComponent(accordion, Constraint.CENTER);

	}

	private void initTabTicket(Device device) {

	}

	/**
	 * Baut den Tab der Verbindungen auf.
	 * 
	 * @param device
	 *            Das Device für den die Verbindungen verwaltet werden sollen
	 * @author Christoph Nebendahl
	 */
	private void initTabVerbindung(Device device) {
		GridLayout layout = new GridLayout(2, device.getConnections().size());
		int i = 0;
		for (Connection c : device.getConnections()) {
			Label name = new Label(c.toString());
			layout.addComponent(name, 0, i);
			layout.setComponentAlignment(name, Alignment.MIDDLE_CENTER);
			ComboBox devices = new ComboBox();
			Session session = DBManager.getFactory().openSession();
			try {
				Criteria cr = session.createCriteria(Device.class);
				cr.add(Restrictions.eq("besitzer", device.getBesitzer()));
				List<Device> devices_list = cr.list();
				for (Device d : devices_list) {
					if (!d.equals(device))
						devices.addItem(d);
					if (c.connectedWidth(d)) {
						devices.setValue(d);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			devices.addValueChangeListener(new ComboBox.ValueChangeListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (event.getProperty().getValue() != null) {
						if (c.getClass().equals(LAN.class)) {
							LAN connection = (LAN) c;
							connection.connectWith((Device) event.getProperty().getValue());
						} else if (c.getClass().equals(WLAN.class)) {
							WLAN connection = (WLAN) c;
							connection.connectWith((Device) event.getProperty().getValue());
						}
					}else{
						//Unconnect muss implementiert werden.
					}

				}
			});
			layout.addComponent(devices, 1, i);
			layout.setComponentAlignment(devices, Alignment.MIDDLE_CENTER);
			i++;
		}

		accordion.addTab(layout, "Verbindung", null);

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
