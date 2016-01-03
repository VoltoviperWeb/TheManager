package de.voltoviper.web.device;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

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

		// Content for the PopupView
		VerticalLayout popupContent = new VerticalLayout();
		ComboBox devices = new ComboBox("Geräte");
		Session session = DBManager.getFactory().openSession();
		try {
			Criteria cr = session.createCriteria(Device.class);
			cr.add(Restrictions.eq("besitzer", device.getBesitzer()));
			List<Device> devices_list = cr.list();
			for (Device d : devices_list) {
				if (!d.equals(device))
					devices.addItem(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		PopupView popup = new PopupView(null, popupContent);
		Button button = new Button("", click -> popup.setPopupVisible(true));
		button.setVisible(false);
		devices.addValueChangeListener(new ComboBox.ValueChangeListener(){

			@Override
			public void valueChange(ValueChangeEvent event) {
				LAN connection = (LAN) lan.getValue();
				connection.connectWith((Device)event.getProperty().getValue());
				logger.trace(device+" wurde mit "+ ((Device)event.getProperty().getValue()).toString()+" verbunden!");
				Notification.show("Verbunden", device+" wurde mit "+ ((Device)event.getProperty().getValue()).toString()+" verbunden!", Notification.Type.TRAY_NOTIFICATION);
				popup.setVisible(false);
				button.setCaption("Zeige Verbindung");
			}
			
		});

		popupContent.addComponent(devices);

		

		

		layout.addComponents(button, popup);

		ComboBox wlan = new ComboBox("WLan Verbindungen");
		Label wlan_connected = new Label("Not connected");
		Button wlan_connect = new Button("Verbinde jetzt");
		for (Connection c : device.getConnections()) {
			if (c.getClass().equals(LAN.class)) {
				lan.addItem(c);
			} else if (c.getClass().equals(WLAN.class)) {
				wlan.addItem(c);
			}
		}

		lan.addValueChangeListener(new ComboBox.ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				LAN lan = (LAN) event.getProperty().getValue();
				if (lan != null) {
					if (lan.connected()) {
						button.setCaption("Zeige Verbindung");
					} else {
						button.setCaption("Verbinde");
					}
					button.setVisible(true);

				} else {
					button.setVisible(false);
				}
			}

		});
		verbindung.addComponent(lan);
		verbindung.addComponent(button);
		verbindung.addComponent(wlan);
		verbindung.addComponent(wlan_connected);
		verbindung.addComponent(wlan_connect);

		for (int i = 0; i < device.getConnections().size(); i++) {

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
