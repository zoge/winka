package hu.winka;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

	private static String contextPath = "/";
	private static String resourceBase = "WebContent";
	private static int httpPort = 8888;

    public static void main(String[] args) throws Exception {
    	
    	System.out.println("Start szerver");
        Server server = new Server(httpPort);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(contextPath);
        webapp.setResourceBase(resourceBase);
        webapp.setDescriptor("WebContent/WEB-INF/web.xml");
		webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webapp);
        server.start();
        //server.join();
        System.out.println("Started Jetty " + Server.getVersion()
                + ", go to http://localhost:" + httpPort + contextPath);
        
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {

            System.err.println( "Desktop doesn't support the browse action (fatal)" );
            System.exit( 1 );
        }
        
        try {

            java.net.URI uri = new java.net.URI( "http://localhost:" + httpPort + contextPath );
            desktop.browse( uri );
        }
        catch ( Exception e ) {

            System.err.println( e.getMessage() );
        }
	
    }

}
