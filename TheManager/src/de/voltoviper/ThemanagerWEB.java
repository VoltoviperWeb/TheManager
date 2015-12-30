package de.voltoviper;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import de.voltoviper.web.Dashboard;
import de.voltoviper.web.TopLeiste;
import de.voltoviper.web.navigation.NavigationView;

@SuppressWarnings("serial")
@Theme("themanager")
public class ThemanagerWEB extends UI {

	public UI ui;
	public BorderLayout layout;
	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ThemanagerWEB.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		ui = this;
		layout = new BorderLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		ui.setContent(layout);
		layout.addComponent(new TopLeiste(), Constraint.NORTH);
		layout.addComponent(new Dashboard(), Constraint.CENTER);
		
		layout.addComponent(new NavigationView(layout), Constraint.WEST);
		
	}

}