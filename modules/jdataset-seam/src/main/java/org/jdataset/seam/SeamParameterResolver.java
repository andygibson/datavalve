package org.jdataset.seam;

import java.io.Serializable;

import org.jboss.seam.core.Expressions;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JDataset parameter resolver that determines the parameter value by treating
 * the parameter as an EL expression. It uses Seams Expression instance to
 * evaluate the expression.
 * 
 * @author Andy Gibson
 * 
 */
public class SeamParameterResolver implements ParameterResolver, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory
			.getLogger(SeamParameterResolver.class);

	public boolean resolveParameter(
			ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter) {
		// log.debug("Resolving Seam Parameter : {}", parameter);
		Object result = Expressions.instance().createValueExpression(
				parameter.getName()).getValue();
		log.debug("Expression {} evaluated to {}", parameter.getName(), result);

		if (result != null) {
			// log.debug("Result type is {}", result.getClass().getName());
			parameter.setValue(result);
			return true;
		}
		return false;
	}

	public boolean acceptParameter(String parameter) {
		return parameter.startsWith("#{") && parameter.endsWith("}");
	}

}
