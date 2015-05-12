package com.tmmas.cl.scl.impresordoc.common.dto;

import java.io.Serializable;

public class DetalleDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String desConcepto;
	private int numUnidades;
	private String numSerie;
	private int numAbonado;
	private String numTerminal;
	private float precioUni;
	private float subTotal;
	
	public String getDesConcepto() {
		return desConcepto;
	}
	public void setDesConcepto(String desConcepto) {
		this.desConcepto = desConcepto;
	}
	public int getNumUnidades() {
		return numUnidades;
	}
	public void setNumUnidades(int numUnidades) {
		this.numUnidades = numUnidades;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public float getPrecioUni() {
		return precioUni;
	}
	public void setPrecioUni(float precioUni) {
		this.precioUni = precioUni;
	}
	public float getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(float valDto) {
		this.subTotal = valDto;
	}
	public int getNumAbonado() {
		return numAbonado;
	}
	public void setNumAbonado(int numAbonado) {
		this.numAbonado = numAbonado;
	}
	public String getNumTerminal() {
		return numTerminal;
	}
	public void setNumTerminal(String numTerminal) {
		this.numTerminal = numTerminal;
	}
}
