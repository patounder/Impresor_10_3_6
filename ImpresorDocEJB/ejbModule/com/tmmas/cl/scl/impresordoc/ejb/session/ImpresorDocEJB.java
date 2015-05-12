package com.tmmas.cl.scl.impresordoc.ejb.session;

import javax.ejb.Stateless;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;

//import net.sf.jasperreports.engine.JasperExportManager;

import com.tmmas.cl.framework20.util.UtilLog;
import com.tmmas.cl.framework20.util.UtilProperty;
import com.tmmas.cl.scl.impresordoc.common.dto.ImpresorDocDTO;
import com.tmmas.cl.scl.impresordoc.common.exception.ImpresorDocException;
import com.tmmas.cl.scl.impresordoc.dao.ImpresorDocDAO;
import com.tmmas.cl.scl.impresordoc.ejb.helper.HelperEJB;
import com.tmmas.cl.scl.impresordoc.ejb.session.ImpresorDocEJBLocal;
import com.tmmas.cl.scl.impresordoc.ejb.session.ImpresorDocEJBRemote;

/**
 * Session Bean implementation class PDFContadoEJB
 */
@Stateless
public class ImpresorDocEJB implements ImpresorDocEJBRemote,
		ImpresorDocEJBLocal {
	private final Logger logger = Logger.getLogger(ImpresorDocEJB.class);
	private CompositeConfiguration config;

	/**
	 * Default constructor.
	 */
	public ImpresorDocEJB() {
		config = UtilProperty
				.getConfiguration("ImpresorDoc.properties",
						"com/tmmas/cl/scl/impresordoc/ejb/properties/ImpresorDocEJB.properties");
	}

	public ImpresorDocDTO getDocumento(int numVenta) {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("EJB:getDocumento:start");
//		System.out.println("El valor de la ruta externa ******* :"+config.getString("rutaPlantilla2"));
		ImpresorDocDTO documento = new ImpresorDocDTO();
		ImpresorDocDAO dao = new ImpresorDocDAO();

		logger.debug("EJB:dao.buscarDocumentos:antes");
		try {
			documento = dao.buscarDocumento(numVenta);
		logger.debug("EJB:dao.buscarDocumentos:despues");
	/*	System.out.println("******** EN EJB GET DOCUMENTO ******* ");
		System.out.println("TOTAL GRA (EN EJB) "+documento.getTotalGra());
		System.out.println("IVA  (EN EJB) "+documento.getIva());
		System.out.println("TOTAL (EN EJB) "+documento.getTotal());
		
		System.out.println("******** EN EJBBBBB ******* ");
		System.out.println("NOMBRE " + documento.getNombreCliente());
		System.out.println("CALLE " + documento.getCalleCliente());
		System.out.println("COMUNA Y ZIP " + documento.getComunaZip());
		System.out.println("FOLIO " + documento.getNumFolio());
		System.out.println("PREF PLAZA " + documento.getPrefPlaza());
		System.out.println("FECH EXP " + documento.getFecExp());
		System.out.println("FECH VEN " + documento.getFecVen());
		System.out.println("COD CLIENTE " + documento.getCodCliente());
		System.out.println("COD VENDEDOR " + documento.getCodVendedor());
		System.out.println("NOM VENDEDOR " + documento.getNomVendedor());
		System.out.println("COD OFICINA " + documento.getCodOficina());
		System.out.println("DES OFICINA " + documento.getDesOficina());
		System.out.println("TOTAL GRA " + documento.getTotalGra());
		System.out.println("IVA " + documento.getIva());
		System.out.println("TOTAL " + documento.getTotal());
		System.out.println("DES TOtAL " + documento.getDesTotal());
		System.out.println("nombre provincia " + documento.getProvincia());*/
		
				
		} catch (ImpresorDocException e) {
			e.printStackTrace();
			logger.debug("EJB:getDocumento(): Error al obtener documento: dao.buscarDocumento("+numVenta+")");
			System.out.println("Error al obtener documento: dao.buscarDocumento("+numVenta+")");
		}
		logger.debug("EJB:getDocumento():return documento");
		return documento;
	}

	public boolean crearPDF(ImpresorDocDTO documento) {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("EJB:crearPDF():start");

		try {
			// Se crea el objeto que creara las copias del reporte
			HelperEJB helpEJB = new HelperEJB();
			
			/*System.out.println("******** EN EJB CREAR PDF ******* ");
			System.out.println("TOTAL GRA (EN EJB crear PDF) "+documento.getTotalGra());
			System.out.println("IVA  (EN EJB) "+documento.getIva());
			System.out.println("TOTAL (EN EJB) "+documento.getTotal());*/
			
			logger.debug("EJB:crearPDF():helpEJB.crearCopias():start");
			helpEJB.crearCopias(documento);
			logger.debug("EJB:crearPDF():helpEJB.crearCopias():end");
			return true;
		} catch (ImpresorDocException e) {
			logger.debug(e.getMessage());
			logger.debug("EJB:crearPDF(): Error helpEJB.crearCopias()");
			e.printStackTrace();
			return false;
		}

	}

}