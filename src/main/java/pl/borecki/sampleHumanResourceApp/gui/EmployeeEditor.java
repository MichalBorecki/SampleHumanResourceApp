package pl.borecki.sampleHumanResourceApp.gui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borecki.sampleHumanResourceApp.model.Employee;
import pl.borecki.sampleHumanResourceApp.repository.EmployeeRepository;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

    private final EmployeeRepository repository;
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DatePicker dateOfBirth = new DatePicker( "Date of birth");
    EmailField email = new EmailField("Email");
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    Binder<Employee> binder = new Binder<>(Employee.class);
    private Employee employee;
    private ChangeHandler changeHandler;

    @Autowired
    public EmployeeEditor(EmployeeRepository repository) {
        this.repository = repository;

        add(firstName, lastName, dateOfBirth, email, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    void delete() {
        repository.delete(employee);
        changeHandler.onChange();
    }

    void save() {
        repository.save(employee);
        changeHandler.onChange();
    }

    public final void editEmployee(Employee c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            employee = repository.findById(c.getId()).get();
        } else {
            employee = c;
        }

        cancel.setVisible(persisted);
        binder.setBean(employee);
        setVisible(true);
        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
