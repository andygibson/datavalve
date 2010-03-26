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

package org.jdataset.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The lazy list class implements the generic list interface by storing proxy keys which can then
 * be used to locate the actual object of type T when it is requested. These objects are fetched
 * on demand as needed.<br/>
 * <br/>
 * Only two methods need to be implemented, the fetchKeyForValue and fetchValueForKey. The list
 * is built by storing the key values in the list using the addKey method. When the keys are added
 * the associated item in the lis is available.<br/>
 * <br/>
 * This list lets you handle massive lists of data using only a small set of in-memory data. This 
 * makes it memory efficient and also fast. 
 *
 *
 * @author Andy Gibson
 * @param <K> Key type that will act as a proxy lookup value
 * @param <T> Type of the object this list holds
 */
public abstract class LazyList<K, T> implements List<T> {

    private Object NULL_HOLDER = new Object();
    private List<K> keys = new ArrayList<K>();
    private List<T> values = new ArrayList<T>();

    protected abstract K fetchKeyForValue(T value);

    protected abstract T fetchValueForKey(K key);

    public boolean add(T element) {
        K key = fetchKeyForValue(element);
        keys.add(key);
        values.add(element);
        return true;
    }

    public void add(int index, T element) {
        K key = fetchKeyForValue(element);
        keys.add(index, key);
        values.add(index, element);
    }

    public boolean addAll(Collection<? extends T> c) {
        List<K> tempKeys = new ArrayList<K>();

        for (T item : c) {
            tempKeys.add(fetchKeyForValue(item));
        }
        keys.addAll(tempKeys);
        return values.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        List<K> tempKeys = new ArrayList<K>();

        for (T item : c) {
            tempKeys.add(fetchKeyForValue(item));
        }
        keys.addAll(index, tempKeys);
        return values.addAll(index, c);
    }

    public void clear() {
        keys.clear();
        values.clear();
    }

    private final void fetchList() {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null) {
                loadValue(i);
            }
        }
    }

    protected final Object loadValue(int index) {
        T result = fetchValueForKey(keys.get(index));
        values.set(index, result);
        return result;
    }

    public boolean contains(Object o) {
        fetchList();
        return values.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        fetchList();
        return values.containsAll(c);
    }

    public T get(int index) {
        Object value = values.get(index);
        if (value == null) {
            value = loadValue(index);
        }
        if (value == NULL_HOLDER) {
            return null;
        }
        return values.get(index);
    }

    public int indexOf(Object o) {
        fetchList();
        return values.indexOf(o);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Iterator<T> iterator() {
        return values.iterator();
    }

    public int lastIndexOf(Object o) {
        fetchList();
        return values.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return values.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return values.listIterator(index);
    }

    public boolean remove(Object o) {
        int i = values.indexOf(o);
        if (i > -1) {
            keys.remove(i);
            values.remove(i);
            keys.remove(o);
        }
        return i > -1;
    }

    public T remove(int index) {
        T obj = values.get(index);
        values.remove(index);
        keys.remove(index);
        return obj;
    }

    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        fetchList();
        for (int i = values.size() - 1; i >= 0; i--) {
            T object = values.get(i);
            if (c.contains(object)) {
                remove(i);
                modified = true;
            }
        }
        return modified;
    }

    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        fetchList();
        for (int i = values.size() - 1; i >= 0; i--) {
            T object = values.get(i);
            if (!c.contains(object)) {
                remove(i);
                modified = true;
            }
        }
        return modified;
    }

    public T set(int index, T element) {
        K key = fetchKeyForValue(element);
        T old = values.get(index);
        keys.set(index, key);
        values.set(index, element);
        return old;
    }

    public int size() {
        return values.size();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        for (int i = fromIndex; i < toIndex; i++) {
            if (values.get(i) == null) {
                loadValue(i);
            }
        }
        return values.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        fetchList();
        return values.toArray();
    }

    public <Tl> Tl[] toArray(Tl[] a) {
        fetchList();
        return values.toArray(a);
    }

    public void addKey(K key) {
        keys.add(key);
        values.add(null);
    }

    public void removeKey(K key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            values.remove(index);
        }
    }

    public void setKeys(List<K> keys) {
        keys.clear();
        values.clear();
        this.keys.addAll(keys);
    }

    public int fillCount() {
        int result = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) != null) {
                result++;
            }
        }
        return result;
    }
}
