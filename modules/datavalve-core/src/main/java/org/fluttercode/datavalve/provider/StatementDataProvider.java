/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.provider;



/**
 * Interface for datasets that use parameterized SQL to get their results from
 * the database. This interface extends the {@link ParameterizedDataProvider}
 * interface and adds methods for setting and getting count and select SQL
 * statements.<br/>
 * This is different to a {@link QueryDataProvider} in that the dataset doesn't
 * handle the ordering or restrictions. The query is a fixed SQL statement that
 * is parameterized.
 * 
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 */
public interface StatementDataProvider<T> extends ParameterizedDataProvider<T> {

	public String getCountStatement();

	public void setCountStatement(String countStatement);

	public String getSelectStatement();

	public void setSelectStatement(String selectStatement);
	
	public void init(Class<? extends Object> clazz,String prefix);

}
