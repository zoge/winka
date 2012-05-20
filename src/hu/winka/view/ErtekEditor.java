package hu.winka.view;

import hu.winka.model.entities.Ertek;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ErtekEditor extends Window implements Button.ClickListener,
FormFieldFactory {
	
    private final Item ertekItem;
    private final Form editorForm;
    private final Button saveButton;
    private final Button cancelButton;

	public ErtekEditor(Item item) {
		
		this.ertekItem = item;
		
        editorForm = new BeanValidationForm<Ertek>(Ertek.class);
        editorForm.setFormFieldFactory(this);
        editorForm.setWriteThrough(false);
        editorForm.setImmediate(true);
        editorForm.setItemDataSource(ertekItem);
        editorForm.setVisibleItemProperties(Arrays.asList(new String[] {
                "hrsz", "cim"}));


        saveButton = new Button("Save", this);
        cancelButton = new Button("Cancel", this);

        editorForm.getFooter().addComponent(saveButton);
        editorForm.getFooter().addComponent(cancelButton);
        getContent().setSizeUndefined();
        addComponent(editorForm);
//        setCaption(buildCaption());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7047180866326141828L;
	
//    private String buildCaption() {
//        return String.format("%s %s", ertekItem.getItemProperty("hrsz")
//                .getValue(), ertekItem.getItemProperty("cim").getValue());
//    }

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

        Field field = DefaultFieldFactory.get().createField(item, propertyId,
                uiContext);
        if (field instanceof TextField) {
            ((TextField) field).setNullRepresentation("");
        }
        return field;
	}

	@Override
	public void buttonClick(ClickEvent event) {
        if (event.getButton() == saveButton) {
            editorForm.commit();
            fireEvent(new EditorSavedEvent(this, ertekItem));
        } else if (event.getButton() == cancelButton) {
            editorForm.discard();
        }
        close();
		
	}
	
    public void addListener(EditorSavedListener listener) {
        try {
            Method method = EditorSavedListener.class.getDeclaredMethod(
                    "editorSaved", new Class[] { EditorSavedEvent.class });
            addListener(EditorSavedEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, editor saved method not found");
        }
    }
	
    public static class EditorSavedEvent extends Component.Event {
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1912388049596025079L;
		private Item savedItem;

        public EditorSavedEvent(Component source, Item savedItem) {
            super(source);
            this.savedItem = savedItem;
        }

        public Item getSavedItem() {
            return savedItem;
        }
    }
    
    public interface EditorSavedListener extends Serializable {
        public void editorSaved(EditorSavedEvent event);
    }

}
