package hu.winka.app;

import hu.winka.view.MainView;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class App extends Application {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PERSISTENCE_UNIT = "winkaPU";

	@Override
	public void init() {
		Window window = new Window();
		setMainWindow(window);
		window.setContent(new MainView());
		//window.addComponent(new Label("Hello World!"));
	}

}
