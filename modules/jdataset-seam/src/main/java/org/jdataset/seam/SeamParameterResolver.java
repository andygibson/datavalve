package org.jdataset.seam;

import org.jboss.seam.core.Expressions;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeamParameterResolver implements ParameterResolver {
	
	private static Logger log = LoggerFactory
			.getLogger(SeamParameterResolver.class);

	public boolean resolveParameter(Parameter parameter) {
		log.debug("Resolving Seam Parameter : {}", parameter);
		String elName = "#{" + parameter.getName() + "}";
		Object result = Expressions.instance().createValueExpression(elName)
				.getValue();
		log.debug("Expression {} evaluated to {}", parameter.getName(), result);		

		if (result != null) {
			log.debug("Result type is {}", result.getClass().getName());
			parameter.setValue(result);
			return true;
		}
		return false;
	}

}
