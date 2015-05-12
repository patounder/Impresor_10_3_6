package com.tmmas.cl.scl.impresordoc.ejb.session;

import javax.ejb.Remote;

import com.tmmas.cl.scl.impresordoc.common.dto.ImpresorDocDTO;

@Remote
public interface ImpresorDocEJBRemote {
	public ImpresorDocDTO getDocumento(int numVenta);
	public boolean crearPDF(ImpresorDocDTO documento);
}
