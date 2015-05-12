package com.tmmas.cl.scl.impresordoc.common.exception;

import com.tmmas.cl.framework.exception.GeneralException;

public class ImpresorDocException extends GeneralException {

	private static final long serialVersionUID = 1L;

	public ImpresorDocException() {
		super();
	}

	public ImpresorDocException(String codigo, long codigoEvento,
			String descripcionEvento) {
		super(codigo, codigoEvento, descripcionEvento);
	}

	public ImpresorDocException(String message, String codigo,
			long codigoEvento, String descripcionEvento) {
		super(message, codigo, codigoEvento, descripcionEvento);
	}

	public ImpresorDocException(String message, Throwable cause, String codigo,
			long codigoEvento, String descripcionEvento) {
		super(message, cause, codigo, codigoEvento, descripcionEvento);
	}

	public ImpresorDocException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImpresorDocException(String message) {
		super(message);
	}

	public ImpresorDocException(Throwable cause) {
		super(cause);
	}

}
