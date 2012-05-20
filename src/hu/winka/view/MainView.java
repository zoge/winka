package hu.winka.view;

import hu.winka.app.App;
import hu.winka.model.entities.Ertek;
import hu.winka.report.Pdf;
import hu.winka.view.ErtekEditor.EditorSavedEvent;
import hu.winka.view.ErtekEditor.EditorSavedListener;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

/**
 * 
 * @author gere
 *
 */
public class MainView extends HorizontalSplitPanel implements
		ComponentContainer {

	private static final long serialVersionUID = 2373526683681104812L;
	
	final private Table ertekTable;
	final private Button editButton;
	final private Button newButton;

	final private JPAContainer<Ertek> erteks;
	
	public MainView() {
		super();

		erteks = JPAContainerFactory.make(Ertek.class, App.PERSISTENCE_UNIT);
		HorizontalLayout toolbar = new HorizontalLayout();
		ertekTable = new Table("Érték", erteks);
		ertekTable.setSelectable(true);
        ertekTable.setImmediate(true);
        ertekTable.addListener(new Property.ValueChangeListener() {
            
        	private static final long serialVersionUID = 1L;

			@Override
            public void valueChange(ValueChangeEvent event) {
                setModificationsEnabled(event.getProperty().getValue() != null);
            }

            private void setModificationsEnabled(boolean b) {
            	editButton.setEnabled(b);
            }
        });
		setFirstComponent(ertekTable);

        editButton = new Button("Edit");
        editButton.addListener(new Button.ClickListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 9000990530181226113L;

			@Override
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().addWindow(
                        new ErtekEditor(ertekTable.getItem(ertekTable.getValue())));
            }
        });
        editButton.setEnabled(false);
        
        newButton = new Button("Add");
        newButton.addListener(new Button.ClickListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 6996630832813693985L;

			@Override
            public void buttonClick(ClickEvent event) {
                final BeanItem<Ertek> newErtekItem = new BeanItem<Ertek>(
                        new Ertek());
                ErtekEditor ertekEditor = new ErtekEditor(newErtekItem);
                ertekEditor.addListener(new EditorSavedListener() {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 3598427041126947854L;

					@Override
                    public void editorSaved(EditorSavedEvent event) {
                        erteks.addEntity(newErtekItem.getBean());
                    }
                });
                getApplication().getMainWindow().addWindow(ertekEditor);
            }
        });
        
		// Textual link
		Link link = new Link("Click Me!",
		        new ExternalResource("http://vaadin.com/"));
		
		toolbar.addComponent(link);
		
		final Button print = new Button("Print This Page");
		print.addListener( new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        print.getWindow().executeJavaScript("print();");
		    }
		});

		final Button print2 = new Button("Print This Page2");
		print2.addListener( new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
				Window window = new Window();
		        window.setResizable(true);
		        window.setWidth("800");
		        window.setHeight("600");
		        window.center();
		        Embedded e = new Embedded();
		        e.setSizeFull();
		        e.setType(Embedded.TYPE_BROWSER);

		        // Here we create a new StreamResource which downloads our StreamSource,
		        // which is our pdf.
		        StreamResource resource = new StreamResource(new Pdf(), "test.pdf?" + System.currentTimeMillis(),getApplication());
		        // Set the right mime type
		        resource.setMIMEType("application/pdf");

		        e.setSource(resource);
		        window.addComponent(e);
		        print.getWindow().addWindow(window);
		    }
		});


		toolbar.addComponent(print);
		toolbar.addComponent(print2);
        toolbar.addComponent(editButton);
        toolbar.addComponent(newButton);
        
        toolbar.setWidth("100%");
        setSecondComponent(toolbar);
        
	}
	
	

}
