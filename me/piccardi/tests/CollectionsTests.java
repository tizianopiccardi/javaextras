package me.piccardi.tests;

import java.util.ArrayList;
import java.util.HashMap;

import me.piccardi.collections.FilterableList;
import me.piccardi.collections.FilterableList.Filter;

public class CollectionsTests {
public static void main(String[] args) {
	
	Filter<Object> f = (Object a) -> a.getClass().getName();
	
	FilterableList<Object> ls = new FilterableList<>(f);
	
	ls.add("ciao");
	ls.add(1);
	ls.add(new ArrayList<Integer>());
	ls.add(new int[]{1,2,3});
	ls.add("hello");
	ls.add(new HashMap<>());
	ls.add("pippo");
	ls.remove("hello");
	
	System.out.println(ls.get(String.class.getName()));
}
}
