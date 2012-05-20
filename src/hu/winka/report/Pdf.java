package hu.winka.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.vaadin.terminal.StreamResource.StreamSource;

public class Pdf implements StreamSource {

	private static final long serialVersionUID = -8848617087943876590L;

	private final ByteArrayOutputStream os = new ByteArrayOutputStream();

	public Pdf() {
		try {
			report()// create new report design
			.columns(
					// add columns
					// title, field name data type
					col.column("Item", "item", type.stringType()),
					col.column("Quantity", "quantity", type.integerType()),
					col.column("Unit price", "unitprice", type.bigDecimalType()))
					.title(cmp.text("Getting started"))// shows report title
					.pageFooter(cmp.pageXofY())// shows number of page at page
												// footer
					.setDataSource(createDataSource())// set datasource
					.toPdf(os);
			// .show();// create and show report
		} catch (DRException e) {
			throw new RuntimeException("You can't catch this.");
		}
	}

	@Override
	public InputStream getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	private JRDataSource createDataSource() {
		DataSource dataSource = new DataSource("item", "quantity", "unitprice");
		dataSource.add("Notebook", 1, new BigDecimal(500));
		dataSource.add("DVD", 5, new BigDecimal(30));
		dataSource.add("DVD", 1, new BigDecimal(28));
		dataSource.add("DVD", 5, new BigDecimal(32));
		dataSource.add("Book", 3, new BigDecimal(11));
		dataSource.add("Book", 1, new BigDecimal(15));
		dataSource.add("Book", 5, new BigDecimal(10));
		dataSource.add("Book", 8, new BigDecimal(9));
		return dataSource;
	}

}
