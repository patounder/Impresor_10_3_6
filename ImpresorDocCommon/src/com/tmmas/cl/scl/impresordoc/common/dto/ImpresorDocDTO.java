package com.tmmas.cl.scl.impresordoc.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;

public class ImpresorDocDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String nombreCliente;
	private String calleCliente;
	private String comunaZip;//Contendra la comuna y ZIP del cliente 
	private String provincia;
	private String prefPlaza;
	private int numFolio;
	private Date fecExp;
	private Date fecVen;
	private int codCliente;
	private int codVendedor;
	private String nomVendedor;
	private String codOficina;
	private String desOficina;
	private float totalGra;
	private float iva;
	private float total;
	private String desTotal;
	private int codError;
	private String tipDocu;
	private int numArticulos;
	
	
	private ArrayList<DetalleDTO> detalle = new ArrayList<DetalleDTO>();
			
	public String getPrefPlaza() {
		return prefPlaza;
	}
	public void setPrefPlaza(String idCliente) {
		this.prefPlaza = idCliente;
	}
	public int getNumFolio() {
		return numFolio;
	}
	public void setNumFolio(int numProceso) {
		this.numFolio = numProceso;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombre) {
		this.nombreCliente = nombre;
	}
	public String getCalleCliente() {
		return calleCliente;
	}
	public void setCalleCliente(String calleCliente) {
		this.calleCliente = calleCliente;
	}
	public ArrayList<DetalleDTO> getDetalle() {
		return detalle;
	}
	public void setDetalle(ArrayList<DetalleDTO> detalle) {
		this.detalle = detalle;
	}
	public String getComunaZip() {
		return comunaZip;
	}
	public void setComunaZip(String comunaZip) {
		this.comunaZip = comunaZip;
	}
	public Date getFecExp() {
		return fecExp;
	}
	public void setFecExp(Date fecExp) {
		this.fecExp = fecExp;
	}
	public Date getFecVen() {
		return fecVen;
	}
	public void setFecVen(Date fecVen) {
		this.fecVen = fecVen;
	}
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public int getCodVendedor() {
		return codVendedor;
	}
	public void setCodVendedor(int codVendedor) {
		this.codVendedor = codVendedor;
	}
	public String getNomVendedor() {
		return nomVendedor;
	}
	public void setNomVendedor(String nomVendedor) {
		this.nomVendedor = nomVendedor;
	}
	public String getCodOficina() {
		return codOficina;
	}
	public void setCodOficina(String codOficina) {
		this.codOficina = codOficina;
	}
	public String getDesOficina() {
		return desOficina;
	}
	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}
	public float getTotalGra() {
		return totalGra;
	}
	public void setTotalGra(float totalGra) {
		this.totalGra = totalGra;
	}
	public float getIva() {
		return iva;
	}
	public void setIva(float iva) {
		this.iva = iva;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getDesTotal() {
		return desTotal;
	}
	public void setDesTotal(String desTotal) {
		this.desTotal = desTotal;
	}
	public int getCodError() {
		return codError;
	}
	public void setCodError(int codError) {
		this.codError = codError;
	}
	public String getTipDocu() {
		return tipDocu;
	}
	public void setTipDocu(String tipDocu) {
		this.tipDocu = tipDocu;
	}
	public int getNumArticulos() {
		return numArticulos;
	}
	public void setNumArticulos(int numArticulos) {
		this.numArticulos = numArticulos;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
}
