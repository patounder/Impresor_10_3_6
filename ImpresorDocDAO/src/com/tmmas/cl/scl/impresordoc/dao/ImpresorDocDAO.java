package com.tmmas.cl.scl.impresordoc.dao;

import com.tmmas.cl.scl.impresordoc.common.dto.DetalleDTO;
import com.tmmas.cl.scl.impresordoc.common.dto.ImpresorDocDTO;
import com.tmmas.cl.scl.impresordoc.common.exception.ImpresorDocException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;

import com.tmmas.cl.framework.base.ConnectionDAO;
import com.tmmas.cl.framework20.util.JndiProperties;
import com.tmmas.cl.framework20.util.ServiceLocator;
import com.tmmas.cl.framework20.util.UtilLog;
import com.tmmas.cl.framework20.util.UtilProperty;

public class ImpresorDocDAO extends ConnectionDAO {

	private ServiceLocator serviceLocator = ServiceLocator.getInstance();
	private final Logger logger = Logger.getLogger(ImpresorDocDAO.class);
	private CompositeConfiguration config;
	private JndiProperties jndiDataSource = null;
	private Connection conexion = null;
	
	public ImpresorDocDAO() {
		config = UtilProperty
				.getConfiguration("ImpresorDoc.properties",
						"com/tmmas/cl/scl/impresordoc/dao/properties/ImpresorDocDAO.properties");
	}

	private String getSQLConsultaCabecera() {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("DAO.getSQLConsultaCliente():start");

		/**
		 * DEBE CAMBIAR LA LLAMADA AL PL CORRESPONDIENTE
		 */
		StringBuffer call = new StringBuffer();
		call.append(" BEGIN ");
		call.append(" FA_FACT_NO_CICLO.fa_obtiene_cabecera_pr ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		call.append(" END;");

		logger.debug("DAO.getSQLConsultaCliente():return call.toString()");
		return call.toString();
	}
	
	private String getSQLConsultaDetalle() {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		
		
		logger.debug("DAO.getSQLConsultaCliente():start");

		/**
		 * DEBE CAMBIAR LA LLAMADA AL PL CORRESPONDIENTE
		 */
		StringBuffer call = new StringBuffer();
		call.append(" BEGIN ");
		call.append(" FA_FACT_NO_CICLO.fa_obtiene_detalle_pr ( ?, ?);");
		call.append(" END;");

		logger.debug("DAO.getSQLConsultaCliente():return call.toString()");
		return call.toString();
	}

	public CallableStatement buscarPackageCabecera(int numVenta)
			throws ImpresorDocException {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		
		logger.debug("DAO.buscarPackageCabecera():start");
		CallableStatement cstmt = null;

		logger.debug("DAO.getSQLConsultaCliente():start");
		String consulta = getSQLConsultaCabecera();
		logger.debug("DAO.getSQLConsultaCliente():end");

		try {
			logger.debug("DAO: conn.prepareCall():start");
			cstmt = getConexion().prepareCall(consulta);
			logger.debug("DAO: conn.prepareCall():end");

			logger.debug("DAO: DATOS DE ENTRADA PARA EL PROCEDIMIENTO");
			// DATOS DE ENTRADA PARA EL PROCEDIMIENTO
			cstmt.setLong(1, numVenta);

			logger.debug("DAO: DATOS DE SALIDA DEL PROCEDIMIENTO");
			// DATOS DE SALIDA DEL PROCEDIMIENTO
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(7, java.sql.Types.DATE);
			cstmt.registerOutParameter(8, java.sql.Types.DATE);
			cstmt.registerOutParameter(9, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(10, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(11, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(12, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(13, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(14, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(15, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(16, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(17, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(18, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(19, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(20, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(21, java.sql.Types.VARCHAR);
			
			logger.debug("DAO: return cstmt");
			return cstmt;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			throw new ImpresorDocException("Ocurrio un error al consultar datos de cabecera. ",e);
		}
	}
	
	public CallableStatement buscarPackageDetalle(int numVenta)
			throws ImpresorDocException {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("DAO.buscarPackageDetalle():start");

		CallableStatement cstmt = null;

		logger.debug("DAO.getSQLConsultaCliente():start");
		String consulta = getSQLConsultaDetalle();
		logger.debug("DAO.getSQLConsultaCliente():end");

		try {
			logger.debug("DAO: conn.prepareCall():start");
			cstmt = getConexion().prepareCall(consulta);
			logger.debug("DAO: conn.prepareCall():end");

			logger.debug("DAO: DATOS DE ENTRADA PARA EL PROCEDIMIENTO");
			// DATOS DE ENTRADA PARA EL PROCEDIMIENTO
			cstmt.setLong(1, numVenta);

			logger.debug("DAO: DATOS DE SALIDA DEL PROCEDIMIENTO");
			// DATOS DE SALIDA DEL PROCEDIMIENTO
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			logger.debug("DAO: return cstmt");
			return cstmt;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			throw new ImpresorDocException("Ocurrio un error al consultar datos del detalle.",e);
		}

	}

	public ImpresorDocDTO buscarDocumento(int numVenta) throws ImpresorDocException {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("DAO.buscarDocumento():start");
		ImpresorDocDTO buscado = new ImpresorDocDTO();

		CallableStatement cstmtCab = null;// Callable de la cabecera
		CallableStatement cstmt = null;// Callable del detalle
		try {
			cstmtCab = buscarPackageCabecera(numVenta);
			cstmtCab.execute();

			logger.debug("DAO:setDatosDocumento:start");
			buscado.setNombreCliente(cstmtCab.getString(2));
			// buscado.setCorreoCliente("patricio.quiroz@tm-mas.com");
			buscado.setCalleCliente(cstmtCab.getString(3));
			buscado.setComunaZip(cstmtCab.getString(4));
			buscado.setNumFolio(cstmtCab.getInt(5));
			buscado.setPrefPlaza(cstmtCab.getString(6));
			buscado.setFecExp(cstmtCab.getDate(7));
			buscado.setFecVen(cstmtCab.getDate(8));
			buscado.setCodCliente(cstmtCab.getInt(9));
			buscado.setCodVendedor(cstmtCab.getInt(10));
			buscado.setNomVendedor(cstmtCab.getString(11));
			buscado.setCodOficina(cstmtCab.getString(12));
			buscado.setDesOficina(cstmtCab.getString(13));
			buscado.setTotalGra(cstmtCab.getFloat(14));
			buscado.setIva(cstmtCab.getFloat(15));
			buscado.setTotal(cstmtCab.getFloat(16));
			buscado.setDesTotal(cstmtCab.getString(17));
			buscado.setCodError(cstmtCab.getInt(18));
			buscado.setTipDocu(cstmtCab.getString(20));
			buscado.setProvincia(cstmtCab.getString(21));
			logger.debug("DAO.buscarDocumento(): Numero Error "+buscado.getCodError());
			
			if(buscado.getCodError() != 0){// El error solo se escribe en el LOG, en ninguna variable
				logger.debug("DAO.buscarDocumento(): Descripcion Error "+cstmtCab.getString(19));
				
			}
		/*	
			System.out.println("******** EN DAO ******* ");
			System.out.println("NOMBRE " + buscado.getNombreCliente());
			System.out.println("CALLE " + buscado.getCalleCliente());
			System.out.println("COMUNA Y ZIP " + buscado.getComunaZip());
			System.out.println("FOLIO " + buscado.getNumFolio());
			System.out.println("PREF PLAZA " + buscado.getPrefPlaza());
			System.out.println("FECH EXP " + buscado.getFecExp());
			System.out.println("FECH VEN " + buscado.getFecVen());
			System.out.println("COD CLIENTE " + buscado.getCodCliente());
			System.out.println("COD VENDEDOR " + buscado.getCodVendedor());
			System.out.println("NOM VENDEDOR " + buscado.getNomVendedor());
			System.out.println("COD OFICINA " + buscado.getCodOficina());
			System.out.println("DES OFICINA " + buscado.getDesOficina());
			System.out.println("TOTAL GRA " + buscado.getTotalGra());
			System.out.println("IVA " + buscado.getIva());
			System.out.println("TOTAL " + buscado.getTotal());
			System.out.println("DES TOTAL " + buscado.getDesTotal());
			System.out.println("Codigo de error " + buscado.getCodError());
			System.out.println("TIPO DOCUMENTO " + buscado.getTipDocu());
			System.out.println("Nombre de provincia " + buscado.getProvincia());

		} catch (SQLException e1) {
		
			logger.error(e1);
			throw new ImpresorDocException("Ocurrio un error al consultar cabecera de la venta. ",e1);
			
		}finally{
			try {
				
				cstmtCab.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				logger.error(e);
				throw new ImpresorDocException("No se pudo cerrar la conexion",e);
				
			}
			cstmtCab = null;
		}

		//LLENADO DEL DETALLE
		CallableStatement cstmt = null;// Callable del detalle

		try {*/
			cstmt = buscarPackageDetalle(numVenta);
			logger.debug("DAO:cstm.execute()");
			cstmt.execute();
			logger.debug("DAO: ResultSet = cstmt.getObject(2):start");
			ResultSet rs = (ResultSet) cstmt.getObject(2);
			logger.debug("DAO: ResultSet = cstmt.getObject(2):end");

			DetalleDTO auxDetalle;

			logger.debug("DAO:seteo del detalle:start");
			while (rs.next()) {

				auxDetalle = new DetalleDTO();

				logger.debug("DAO:setDesConcepto(DES_CONCEPTO):start");
				auxDetalle.setDesConcepto(rs.getString("DES_CONCEPTO"));
//				System.out.println("DES_CONCEPTO "
//						+ auxDetalle.getDesConcepto());

				logger.debug("DAO:setNumUnidades(NUM_UNIDADES):start");
				auxDetalle.setNumUnidades(rs.getInt("NUM_UNIDADES"));
//				System.out.println("NUM_UNIDADES "
//						+ auxDetalle.getNumUnidades());

				logger.debug("DAO:setNumSerie(NUM_SERIE):start");
				auxDetalle.setNumSerie(rs.getString("NUM_SERIE"));
//				System.out.println("NUM_SERIE " + auxDetalle.getNumSerie());

				logger.debug("DAO:setNumSerie(NUM_ABONADO):start");
				auxDetalle.setNumAbonado(rs.getInt("NUM_ABONADO"));
//				System.out.println("NUM_ABONADO " + auxDetalle.getNumAbonado());

				logger.debug("DAO:setNumSerie(NUM_TERMINAL):start");
				auxDetalle.setNumTerminal(rs.getString("NUM_TERMINAL"));
//				System.out.println("NUM_TERMINAL "
//						+ auxDetalle.getNumTerminal());

				logger.debug("DAO:setImpCargo(PRECIO_UNI):start");
				auxDetalle.setPrecioUni(rs.getFloat("PRECIO_UNI"));
//				System.out.println("IMP_CARGO " + auxDetalle.getPrecioUni());

				logger.debug("DAO:setValDto(SUB_TOTAL):start");
				auxDetalle.setSubTotal(rs.getFloat("SUB_TOTAL"));
//				System.out.println("VAL_DTO " + auxDetalle.getSubTotal());

				buscado.getDetalle().add(auxDetalle);
				// System.out.println(auxDetalle.getDesConcepto());

			}
			buscado.setNumArticulos(buscado.getDetalle().size());
//			System.out.println("NUMERO DE ARTICULOS  " + buscado.getNumArticulos());
			
			logger.debug("DAO:seteo del detalle:end");
			
		} catch (SQLException e1) {
		
			logger.error(e1);
			throw new ImpresorDocException("Ocurrio un error al consultar datos de la venta. ",e1);
			
		}finally{
			
			try {
				cstmtCab.close();
				cstmt.close();
				closeConexion();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				logger.error(e);
				throw new ImpresorDocException("No se pudo cerrar conexion",e);
			}
			cstmtCab = null;
			cstmt = null;
		}
		
		logger.debug("DAO:setDatosDocumento:end");
		logger.debug("DAO:return buscado");
		return buscado;
	}

	public JndiProperties getJndiForDataSource() {
		UtilLog.setLog(config.getString("ImpresorDocEJB.log"));
		logger.debug("DAO.getJndiForDataSource():start");
		if (jndiDataSource == null) {
			jndiDataSource = new JndiProperties();

			logger.debug("DAO.getJndiForDataSource():set paramtetros");			
			jndiDataSource.setJndi(config.getString("jndiDatasource"));
			jndiDataSource.setFactoryInitial(config.getString("initialContextFactory"));
			jndiDataSource.setFactoryInitial(config.getString("urlProvider"));
			jndiDataSource.setSecurityPrincipal(config.getString("securityPrincipal"));
			jndiDataSource.setSecurityCredentials(config.getString("securityCredentials"));
		}

		logger.debug("DAO.getJndiForDataSource():return jndiDataSource");
		return jndiDataSource;
	}

	private Connection getConexion() throws ImpresorDocException{
		
		try {
			
			logger.debug("DAO:serviceLocator.getDataSource():start");
			
			if(this.conexion == null){
				conexion = serviceLocator.getDataSource((getJndiForDataSource()))
						.getConnection();
			logger.debug("DAO:serviceLocator.getDataSource():end");
			}
			
		return conexion;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new ImpresorDocException("No se pudo obtener una conexion", e);
		}
		
	}
	
	private void closeConexion() throws ImpresorDocException{
		
		try {
			if (this.conexion != null) {
				if (!this.conexion.isClosed()) {
					this.conexion.close();
				}
				this.conexion = null;
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e);
				throw new ImpresorDocException("No se pudo obtener una conexion", e);
			} finally {
				this.conexion = null;
			}
	}
}
