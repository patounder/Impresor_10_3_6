package com.tmmas.cl.scl.impresordoc.web;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;

import com.tmmas.cl.framework20.util.UtilLog;
import com.tmmas.cl.framework20.util.UtilProperty;
import com.tmmas.cl.scl.impresordoc.common.dto.ImpresorDocDTO;
import com.tmmas.cl.scl.impresordoc.ejb.session.ImpresorDocEJBRemote;

/**
 * Servlet implementation class PDFContadoServlet
 */
public class ImpresorDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(ImpresorDocServlet.class);
	private CompositeConfiguration config;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		config = UtilProperty
				.getConfiguration("ImpresorDoc.properties",
						"com/tmmas/cl/scl/impresordoc/web/properties/ImpresorDocWEB.properties");
		UtilLog.setLog(config.getString("ImpresorDocWEB.log"));
		PrintWriter out = response.getWriter();
		ImpresorDocDTO documento = new ImpresorDocDTO();
		Context context;
		int paramEntrada = 0;

		logger.debug("Servlet.doPost():start");

		try {
			paramEntrada = Integer.parseInt(request.getParameter("numVenta"));
		} catch (Exception e) {
			out.println("Parametro " + request.getParameter("numVenta")
					+ " no corresponde a un numero entero");
			logger.debug("Parametro " + request.getParameter("numVenta")
					+ " no corresponde a un numero entero");
			return;
		}

		if (paramEntrada != 0) {

			try {

				context = new InitialContext();

				logger.debug("Srvlt:context.lookup():antes");
				ImpresorDocEJBRemote iRemota = (ImpresorDocEJBRemote) context
						.lookup("ImpresorDocEJB");
				logger.debug("Srvlt:context.lookup():despues");

				logger.debug("Srvlt:iRemota.getDocumento(paramEntrada):antes");
				documento = iRemota.getDocumento(paramEntrada);
				logger.debug("Srvlt:iRemota.getDocumento(paramEntrada):despues");

/*				out.println("Nombre :" + documento.getNombreCliente());
				out.println("Calle :" + documento.getCalleCliente());
				out.println("Plaza :" + documento.getPrefPlaza());

				System.out.println("******** EN SERVLET ******* ");
				System.out.println("NOMBRE " + documento.getNombreCliente());
				System.out.println("CALLE " + documento.getCalleCliente());
				System.out.println("COMUNA Y ZIP " + documento.getComunaZip());
				System.out.println("FOLIO " + documento.getNumFolio());
				System.out.println("PREF PLAZA " + documento.getPrefPlaza());
				System.out.println("FECH EXP " + documento.getFecExp());
				System.out.println("FECH VEN " + documento.getFecVen());
				System.out.println("COD CLIENTE " + documento.getCodCliente());
				System.out
						.println("COD VENDEDOR " + documento.getCodVendedor());
				System.out
						.println("NOM VENDEDOR " + documento.getNomVendedor());
				System.out.println("COD OFICINA " + documento.getCodOficina());
				System.out.println("DES OFICINA " + documento.getDesOficina());
				System.out.println("TOTAL GRA " + documento.getTotalGra());
				System.out.println("IVA " + documento.getIva());
				System.out.println("TOTAL " + documento.getTotal());
				System.out.println("DES TOTAL " + documento.getDesTotal());*/
				if (documento.getCodError() == 0) {
				
						// Se prueba el metodo que hace el reporte
					logger.debug("Srvlt:getReporte():antes");
					boolean isPdfCreado = iRemota.crearPDF(documento);
					logger.debug("Srvlt:getReporte():despues");
					
					if(isPdfCreado){
						out.println("PDF generado exitosamente !");
					//PARA LA PRUEBA***************************
					String ruta = "com/tmmas/cl/scl/ImpresorDoc/reportes/";
					File archivo = new File (ruta+documento.getPrefPlaza()+"-"+ documento.getNumFolio() +".pdf");
					Desktop.getDesktop().open(archivo);
					logger.debug("Servlet.doPost():PDF generado exitosamente");
					}else{
						logger.debug("Servlet.doPost():No fue posible generar pdf. Cierre antiguo PDF");
						out.println("No fue posible generar pdf. Cierre antiguo PDF !");
					}
					
										
				} else {
					logger.debug("Servlet.doPost(): No fue posible obtencion de datos. No se exporta PDF");
					out.println("Error en la obtencion de los datos. No fue posible generar pdf.");
				}

			} catch (NamingException ne) {
				logger.debug("Error en la obtencion del documento. Verificar conexion a Datasource.");
				logger.debug("Srvlt:ExceptionNaming." + ne.getMessage());
				out.println("Error en la obtencion del documento. Verificar conexion a Datasource.");
//				ne.printStackTrace();
			}catch (IOException ex) {
			     ex.printStackTrace();
			}
		}
		
		logger.debug("Servlet:doPost():helperSh.getDocumento():end");
		time_end = System.currentTimeMillis();
		System.out.println("Se demoro : " + (time_end - time_start)
				+ " milisegundos");
	}

}
