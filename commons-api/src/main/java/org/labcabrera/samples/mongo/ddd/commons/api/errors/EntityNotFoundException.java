package org.labcabrera.samples.mongo.ddd.commons.api.errors;

/**
 * Entidad equivalente a <code>javax.persistence.EntityNotFoundException</code> comun para aquellos proyectos que no
 * tienen las dependencias de JPA.
 * 
 * @author Arquitectura
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String message) {
		super(message);
	}

}
