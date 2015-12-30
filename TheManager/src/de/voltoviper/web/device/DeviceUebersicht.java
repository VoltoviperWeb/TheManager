package de.voltoviper.web.device;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.ui.Table;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Device;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;

public class DeviceUebersicht extends BorderLayout implements KundenAuswahlInterface {
	Table table;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Kunde k;
	
	public DeviceUebersicht(BorderLayout layout){
		
		table = new Table("Geräteübersicht");
		table.addContainerProperty("Typ", Device_Typ.class, null);
		table.addContainerProperty("Hersteller", Hersteller.class, null);
		table.addContainerProperty("Verbunden", Boolean.class, null);
		table.setSelectable(true);
		table.setImmediate(true);
		
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
				table.addItem(new Object[] { d.getTyp(), d.getHersteller(), d.connected()}, i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
	}

}
