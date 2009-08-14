package org.jdataset.db;

import java.io.Serializable;

import org.jdataset.ObjectDataset;
import org.jdataset.Parameter;
import org.jdataset.ParameterResolver;

public class TestingParameterResolver implements ParameterResolver,Serializable {

	public boolean resolveParameter(ObjectDataset dataset,Parameter parameter) {

		if (parameter.getName().equals("id")) {
			parameter.setValue("value_id");
			return true;
		}

		if (parameter.getName().equals("person.firstName")) {
			parameter.setValue("value_firstName");
			return true;
		}

		return false;

	}

	public boolean acceptParameter(String parameter) {
		return true;
	}

}
