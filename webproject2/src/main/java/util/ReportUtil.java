package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRExporterContext;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;


@SuppressWarnings({"rawtypes", "unchecked"})
public class ReportUtil implements Serializable {
	
private static final long serialVersionUID = 1L;


public byte[] geraRelatorioExcel (List listaDados, String nomeRelatorio,HashMap<String, Object> params, ServletContext servletContext)throws Exception {
	
	JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
	
	String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
	JasperPrint impressoraJasper =  JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
	
	JRXlsxExporter exporter = new JRXlsxExporter();
	exporter.setExporterInput(new SimpleExporterInput( impressoraJasper));
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
	exporter.setConfiguration(new SimpleXlsxReportConfiguration());
	exporter.exportReport();
	return baos.toByteArray();
}
	
public byte[] geraRelatorioPDF (List listaDados, String nomeRelatorio,HashMap<String, Object> params, ServletContext servletContext)throws Exception {
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		JasperPrint impressoraJasper =  JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	public byte[] geraRelatorioPDF (List listaDados, String nomeRelatorio, ServletContext servletContext)throws Exception {
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		JasperPrint impressoraJasper =  JasperFillManager.fillReport(caminhoJasper, new HashedMap(), jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}
