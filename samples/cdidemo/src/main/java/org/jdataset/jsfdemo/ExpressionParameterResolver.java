package org.jdataset.jsfdemo;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;
import org.jdataset.provider.ParameterizedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionParameterResolver implements ParameterResolver {

	private static ExpressionFactory ef = ExpressionFactory.newInstance();
	private static Logger log = LoggerFactory.getLogger(ExpressionParameterResolver.class);
	
	public boolean acceptParameter(String name) {		
		return name.startsWith("#{") && name.endsWith("}"); 
	}

	public boolean resolveParameter(
			ParameterizedDataProvider<? extends Object> dataset,
			Parameter parameter) {
		 ELContext elc = FacesContext.getCurrentInstance().getELContext();
		 ValueExpression exp = ef.createValueExpression(elc,parameter.getName(),Object.class);
		 
		 Object v= exp.getValue(elc);
		 log.debug("Resolving {} to {}",parameter.getName(),v);
		 if (v != null) {
			 parameter.setValue(v);
			 return true;
		 }
		 return false;
	}

}
