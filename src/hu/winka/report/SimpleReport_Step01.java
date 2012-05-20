package hu.winka.report;

import java.math.BigDecimal;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import net.sf.dynamicreports.examples.DataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class SimpleReport_Step01 {

	public SimpleReport_Step01() {
		build();
	}

	private void build() {
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
					//.toPdf(pdfExporterBuilder)
					.show();// create and show report
		} catch (DRException e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		new SimpleReport_Step01();
	}

}
