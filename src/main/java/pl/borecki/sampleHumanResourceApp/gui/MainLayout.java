package pl.borecki.sampleHumanResourceApp.gui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        final DrawerToggle drawerToggle = new DrawerToggle();
        final RouterLink home = new RouterLink("Main", MainView.class);
        final RouterLink employeeList = new RouterLink("Employee list", EmployeeList.class);
        final VerticalLayout menuLayout = new VerticalLayout(home, employeeList);
        addToDrawer(menuLayout);
        addToNavbar(drawerToggle);
    }

    protected void onAttach(AttachEvent attachEvent) {
        UI ui = getUI().get();
        Button button = new Button(VaadinIcon.SIGN_OUT.create(), event -> {
            ui.getPage().executeJs("window.location.href='logout'");
            ui.getSession().close();
        });

        addToNavbar(button);

        // Notice quickly if other UIs are closed
        ui.setPollInterval(3000);
    }
}

