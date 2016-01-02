package de.voltoviper.web.device;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Device;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.navigation.NavigationView;

public class DeviceUebersicht extends BorderLayout implements KundenAuswahlInterface {
	Table table;
	

	private static final Logger logger = LogManager.getLogger(DeviceUebersicht.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Kunde k;

	public DeviceUebersicht(BorderLayout layout, NavigationView navigation) {

		table = new Table("Geräteübersicht");
		table.addContainerProperty("ID", Integer.class, null);
		table.addContainerProperty("Typ", Device_Typ.class, null);
		table.addContainerProperty("Hersteller", Hersteller.class, null);
		table.addContainerProperty("Verbunden", Boolean.class, null);
		table.setSelectable(true);

		table.addItemClickListener(new ItemClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					Session session = DBManager.getFactory().openSession();
					try {
						Device device = (Device) session.get(Device.class,
								Integer.parseInt(table.getItemCaption(event.getItemId())));
						logger.trace(device.toString()+" wurde ausgewählt");
						navigation.setView(new DeviceMenu(device));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						session.close();

					}
				}

			}

		});

		this.addComponent(table, Constraint.CENTER);

		KundenAuswahlView k_auswahl = new KundenAuswahlView(layout, this);
		layout.addComponent(k_auswahl, Constraint.EAST);
	}

	/**
	 * Setzt den Kunden, zu dem die Geräte angezeigt werden sollen
	 */
	@Override
	public void setKunde(Kunde k) {
		// TODO Auto-generated method stub
		this.k = k;
		table.removeAllItems();
		Session session = DBManager.getFactory().openSession();
		try {
			Criteria cr = session.createCriteria(Device.class);
			cr.add(Restrictions.eq("besitzer", k));
			List<Device> devices = cr.list();
			int i = 0;
			for (Device d : devices) {
				table.setItemCaption(table
						.addItem(new Object[] { d.getDevice_id(), d.getTyp(), d.getHersteller(), d.connected() }, i),
						String.valueOf(d.getDevice_id()));
				;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
	}

}
