/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.provider;



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
