package me.piccardi.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterableList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 6583620427581246417L;

	private Map<String, Set<T>> map = new HashMap<>();

	public interface Filter<T> {
		public String getKey(T a);
	}

	private Filter<T> filter;

	public FilterableList(Filter<T> filter) {
		this.filter = filter;
	}

	@Override
	public boolean add(T e) {
		return super.add(e) & mapAdd(e);
	}

	private boolean mapAdd(T e) {
		Set<T> subSet = map.get(filter.getKey(e));
		if (subSet == null) {
			subSet = new HashSet<>();
			map.put(filter.getKey(e), subSet);
		}
		return subSet.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = true;
		for (T e : c)
			result &= add(e);
		return result;
	}

	public Set<T> get(String key) {
		Set<T> subSet = map.get(key);
		if (subSet == null)
			return null;
		return subSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		Set<T> subSet = map.get(filter.getKey((T) o));
		return super.remove(o) && subSet.remove(o);
	}

	@Override
	public T remove(int index) {
		T o = get(index);
		Set<T> subSet = get(filter.getKey(o));
		subSet.remove(o);
		return super.remove(index);
	}

	@Override
	public T set(int index, T element) {
		T o = get(index);
		Set<T> subSet = get(filter.getKey(o));
		subSet.remove(o);
		subSet.add(element);
		return super.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		T o = get(index);
		Set<T> subSet = get(filter.getKey(o));
		subSet.add(element);
		super.add(index, element);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = true;
		for (Object e : c)
			result &= remove(e);
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T e : c) {
			add(index, e);
			index++;
		}
		return true;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		for (int i = fromIndex; i < toIndex; i++) {
			remove(i);
		}
	}
}
