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

package org.fluttercode.spigot.dataset;

import org.fluttercode.spigot.DataProvider;

/**
 * Concrete instance of the {@link Dataset} that fixes the
 * generic provider type to a simple {@link DataProvider} so it can be used with
 * any class that implements the {@link DataProvider} interface.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            type of object returned from the provider
 */
public class DataProviderDataset<T> extends
		Dataset<T, DataProvider<T>> {

	private static final long serialVersionUID = 1L;

	public DataProviderDataset(DataProvider<T> provider) {
		super(provider);
	}

}
