package de.voltoviper;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import de.voltoviper.objects.benutzer.Mitarbeiter;
import de.voltoviper.web.Dashboard;
import de.voltoviper.web.TopLeiste;
import de.voltoviper.web.login.LoginView;
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
		standard();
		
		ui = this;
		

		layout = new BorderLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		initlogin();
		
		ui.setContent(layout);
	}

	private void standard() {
		// TODO Auto-generated method stub
		Mitarbeiter mitarbeiter = new Mitarbeiter( "user", "test", "Christoph", "Nebendahl", true);
	}

	public void initmain() {
		clearLayout();
		layout.addComponent(new TopLeiste(), Constraint.NORTH);
		layout.addComponent(new Dashboard(), Constraint.CENTER);
		layout.addComponent(new NavigationView(layout), Constraint.WEST);
	}

	private void initlogin() {
		layout.addComponent(new LoginView(this), Constraint.CENTER);
	}

	private void clearLayout() {
		layout.removeComponent(Constraint.NORTH);
		layout.removeComponent(Constraint.EAST);
		layout.removeComponent(Constraint.SOUTH);
		layout.removeComponent(Constraint.WEST);
		layout.removeComponent(Constraint.CENTER);
	}

}