package com.tmmas.cl.scl.impresordoc.ejb.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import com.tmmas.cl.framework20.util.UtilLog;
import com.tmmas.cl.framework20.util.UtilProperty;
import com.tmmas.cl.scl.impresordoc.common.dto.ImpresorDocDTO;
import com.tmmas.cl.scl.impresordoc.common.exception.ImpresorDocException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class HelperEJB {

	private final Logger logger = Logger.getLogger(HelperEJB.class);
	private CompositeConfiguration config;
	String ruta = "com/tmmas/cl/scl/ImpresorDoc/plantilla";
	private ImpresorDocDTO docRoot = new ImpresorDocDTO();

	public HelperEJB() {
		config = UtilProperty
				.getConfiguration("ImpresorDoc.properties",
						"com/tmmas/cl/scl/impresordoc/ejb/properties/ImpresorDocEJB.properties");
	}

	public void crearCopias(ImpresorDocDTO doc) throws ImpresorDocException {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("HelperEJB:crearCopias():start");
		ArrayList<JasperPrint> listaJasperPrint = new ArrayList<JasperPrint>();

		setDoc(doc);

//		System.out.println("NUMERO DE unidades (EN HELPER EJB CREAR COPIAS) "+doc.getDetalle().get(0).getNumUnidades());
//		System.out.println("NUMERO DE impcargo (EN HELPER EJB CREAR COPIAS)"+doc.getDetalle().get(0).getPrecioUni());
		try {
			
//			System.out.println("Valor total gra "+ doc.getTotalGra());
			JRBeanCollectionDataSource dsCliente = new JRBeanCollectionDataSource(
					doc.getDetalle());
			logger.debug("HelperEJB:crearCopias():jasperPrintCliente:start");
			JasperPrint jasperPrintCliente = JasperFillManager.fillReport(config.getString("rutaPlantilla"), getParams("Cliente"), dsCliente);
			logger.debug("HelperEJB:crearCopias():listaJasperPrint.add(jasperPrintCliente):start");
			listaJasperPrint.add(jasperPrintCliente);

			JRBeanCollectionDataSource dsCaja1 = new JRBeanCollectionDataSource(
					doc.getDetalle());
			logger.debug("HelperEJB:crearCopias():jasperPrintCaja1:start");
			JasperPrint jasperPrintCaja1 = JasperFillManager.fillReport(config.getString("rutaPlantilla"), getParams("Caja"), dsCaja1);
			logger.debug("HelperEJB:crearCopias():listaJasperPrint.add(jasperPrintCaja1):start");
			listaJasperPrint.add(jasperPrintCaja1);
			
			JRBeanCollectionDataSource dsCaja2 = new JRBeanCollectionDataSource(
					doc.getDetalle());
			logger.debug("HelperEJB:crearCopias():jasperPrintCaja2:start");
			JasperPrint jasperPrintCaja2 = JasperFillManager.fillReport(config.getString("rutaPlantilla"), getParams("Caja"), dsCaja2);
			logger.debug("HelperEJB:crearCopias():listaJasperPrint.add(jasperPrintCaja2):start");
			listaJasperPrint.add(jasperPrintCaja2);
			
			JRBeanCollectionDataSource dsBodega = new JRBeanCollectionDataSource(
					doc.getDetalle());
			logger.debug("HelperEJB:crearCopias():jasperPrintBodega:start");
			JasperPrint jasperPrintBodega = JasperFillManager.fillReport(config.getString("rutaPlantilla"), getParams("Bodega"), dsBodega);
			logger.debug("HelperEJB:crearCopias():listaJasperPrint.add(jasperPrintBodega):start");
			listaJasperPrint.add(jasperPrintBodega);
			
			JRPdfExporter exporter = new JRPdfExporter();

			logger.debug("HelperEJB:crearCopias():setParameter(JRExporterParameter.JASPER_PRINT_LIST)");
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					listaJasperPrint);
			logger.debug("HelperEJB:crearCopias():setParameter(JRExporterParameter.OUTPUT_FILE_NAME)");
			exporter.setParameter(
					JRExporterParameter.OUTPUT_FILE_NAME,
					(config.getString("rutaDestino")
							+ doc.getPrefPlaza() + "-"+ doc.getNumFolio() +".pdf"));
			logger.debug("HelperEJB:crearCopias():setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS)");
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);

			logger.debug("HelperEJB:crearCopias():exporter.exportReport()");
			exporter.exportReport();
			logger.debug("HelperEJB:crearCopias():end");
//			System.out.println("Fin !!! Valor total gra "+ doc.getTotalGra());
		} catch (JRException jre) {
			jre.printStackTrace();
			logger.error(jre);
			if (jre.getMessage().contains("Error trying to export to file")){
				logger.debug("Debe cerrar antiguo pdf para poder generar el nuevo.");
			}
			throw new ImpresorDocException("Ocurrio un error general al llenar documento.");
			
		}

	}

	public Map<String, Object> getParams(String key) {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("HelperEJB:getParams():start");

		Map<String, Object> paramAux = new HashMap<String, Object>();

//		System.out.println("NUMERO DE FOLIO (EN HELPER EJB) "+this.getDoc().getNumFolio());
//		System.out.println("NUMERO DE Plaza (EN HELPER EJB)"+this.getDoc().getPrefPlaza());
		logger.debug("HelperEJB:getParams():paramAux.put():start");
		paramAux.put("nombreCliente", this.getDoc().getNombreCliente());
		paramAux.put("calleCliente", this.getDoc().getCalleCliente());
		paramAux.put("provinciaCliente", this.getDoc().getProvincia());
		paramAux.put("comunaZipCliente", this.getDoc().getComunaZip());
		 
		String patron = "dd/MM/yyyy";//PARA DARLE EL FORMATO A LA FECHA EXTRAIDA DESDE BD
		SimpleDateFormat df = new SimpleDateFormat(patron);
		
		paramAux.put("prefPlazaNumFolio", this.getDoc().getTipDocu()+" No. "+this.getDoc().getPrefPlaza()+"-"+this.getDoc().getNumFolio());
		/*System.out.println("FECHA PROBLEMA ***********"+this.getDoc().getFecExp());
		System.out.println("FECHA PROBLEMA ***********"+this.getDoc().getFecVen());
		paramAux.put("fecExp", df.format(this.getDoc().getFecExp().toString()));
		paramAux.put("fecVen", df.format(this.getDoc().getFecVen().toString()));*/
		paramAux.put("fecExp", df.format(this.getDoc().getFecExp()));
		paramAux.put("fecVen", df.format(this.getDoc().getFecVen()));
		
		
		paramAux.put("codCliente", this.getDoc().getCodCliente());
		
		paramAux.put("oficina", this.getDoc().getCodOficina()+" "+this.getDoc().getDesOficina());
		paramAux.put("vendedor", this.getDoc().getCodVendedor()+" "+this.getDoc().getNomVendedor());
		
//		System.out.println("TOTAL GRA (EN HELPER EJB) "+this.getDoc().getTotalGra());
		paramAux.put("totalGra", this.getDoc().getTotalGra());
//		System.out.println("IVA  (EN EJB) "+this.getDoc().getIva());
//		System.out.println("TOTAL (EN EJB) "+this.getDoc().getTotal());
		paramAux.put("iva", this.getDoc().getIva());
		paramAux.put("total", this.getDoc().getTotal());
		paramAux.put("desTotal", this.getDoc().getDesTotal());
		paramAux.put("numArticulos", this.getDoc().getNumArticulos());
		
		
		logger.debug("HelperEJB:getParams():paramAux.put():end");

		if (key.equals("Cliente")) {
			// Footer para la copia Cliente
			paramAux.put("footer", "CLIENTE");
			logger.debug("HelperEJB:getParams():paramAux.put(footer,Cliente):end");

		} else {
			if (key.equals("Caja")) {
				// Footer para la copia Caja
				paramAux.put("footer", "CAJA");
				logger.debug("HelperEJB:getParams():paramAux.put(footer,Caja):end");
			} else {
				// Footer para la copia Bodega
				paramAux.put("footer", "BODEGA");
				logger.debug("HelperEJB:getParams():paramAux.put(footer,Bodega):end");
			}
		}

		logger.debug("HelperEJB:getParams():return paramAux");
		return paramAux;
	}

	private ImpresorDocDTO getDoc() {
		return docRoot;
	}

	private void setDoc(ImpresorDocDTO doc) {
		this.docRoot = doc;
	}
}