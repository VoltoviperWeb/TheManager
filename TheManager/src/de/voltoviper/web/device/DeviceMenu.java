package de.voltoviper.web.device;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

	/**
	 * Initialisiert das accordion f�r die Device �bersicht
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
		Layout ticket = new VerticalLayout();
		accordion.addTab(ticket, "Tickets", null);

	}

	private void initTabVerbindung(Device device) {
		Layout verbindung = new FormLayout();

		ComboBox lan = new ComboBox("Lan Verbindungen");

		// LAN Popup
		VerticalLayout popupContent = new VerticalLayout();
		ComboBox devices = new ComboBox("Ger�te");
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

		// Ende PopUp

		lan.addValueChangeListener(new ComboBox.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					button.setVisible(true);
					if(((LAN)event.getProperty().getValue()).connected()){
						button.setCaption("Zeige Verbindung");
					}else{
						button.setCaption("Verbinde");
					}
				} else {
					button.setVisible(false);
				}

			}

		});

		devices.addValueChangeListener(new ComboBox.ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				LAN connection = (LAN) lan.getValue();
				Transaction tx = null;
				Session session = DBManager.getFactory().openSession();
				try {
					connection.connectWith((Device) event.getProperty().getValue());

					tx = session.beginTransaction();
					session.update(connection);
					session.update((Device) event.getProperty().getValue());
					tx.commit();

					logger.trace(device + " wurde mit " + ((Device) event.getProperty().getValue()).toString()
							+ " verbunden!");
					Notification.show("Verbunden", device + " wurde mit "
							+ ((Device) event.getProperty().getValue()).toString() + " verbunden!",
							Notification.Type.TRAY_NOTIFICATION);
					popup.setVisible(false);
					button.setCaption("Zeige Verbindung");
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
					Notification.show("Fehler", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				} finally {
					session.close();
				}
			}

		});

		popupContent.addComponent(devices);

		ComboBox wlan = new ComboBox("W-Lan Verbindungen");
		// WLAN Popup
		VerticalLayout popupContentwlan = new VerticalLayout();
		ComboBox devices_wlan = new ComboBox("Ger�te");
		Session s = DBManager.getFactory().openSession();
		try {
			Criteria cr = s.createCriteria(Device.class);
			cr.add(Restrictions.eq("besitzer", device.getBesitzer()));

			List<Device> devices_list = cr.list();
			for (Device d : devices_list) {
				if (!d.equals(device))
					devices_wlan.addItem(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();

		}
		PopupView popupwlan = new PopupView(null, popupContentwlan);
		Button buttonwlan = new Button("", click -> popupwlan.setPopupVisible(true));
		buttonwlan.setVisible(false);
		
		wlan.addValueChangeListener(new ComboBox.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					buttonwlan.setVisible(true);
					if(((WLAN)event.getProperty().getValue()).connected()){
						buttonwlan.setCaption("Zeige Verbindung");
					}else{
						buttonwlan.setCaption("Verbinde");
					}
				} else {
					buttonwlan.setVisible(false);
				}

			}

		});
		
		devices_wlan.addValueChangeListener(new ComboBox.ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				WLAN connection = (WLAN) wlan.getValue();
				Transaction tx = null;
				Session session = DBManager.getFactory().openSession();
				try {
					connection.connectWith((Device) event.getProperty().getValue());

					tx = session.beginTransaction();
					session.update(connection);
					session.update((Device) event.getProperty().getValue());
					tx.commit();

					logger.trace(device + " wurde mit " + ((Device) event.getProperty().getValue()).toString()
							+ " verbunden!");
					Notification.show("Verbunden", device + " wurde mit "
							+ ((Device) event.getProperty().getValue()).toString() + " verbunden!",
							Notification.Type.TRAY_NOTIFICATION);
					popupwlan.setVisible(false);
					buttonwlan.setCaption("Zeige Verbindung");
				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
					Notification.show("Fehler", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				} finally {
					session.close();
				}
			}

		});

		for (Connection c : device.getConnections()) {
			if (c.getClass().equals(LAN.class)) {
				lan.addItem(c);
			} else if (c.getClass().equals(WLAN.class)) {
				wlan.addItem(c);
			}
		}

		if (lan.getItemIds().size() == 0) {
			lan.setVisible(false);
		}
		if (wlan.getItemIds().size() == 0) {
			wlan.setVisible(false);
		}

		popupContentwlan.addComponent(devices_wlan);

		layout.addComponents(popup);
		layout.addComponents(popupwlan);
		verbindung.addComponent(lan);
		verbindung.addComponent(button);
		verbindung.addComponent(wlan);
		verbindung.addComponent(buttonwlan);

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
