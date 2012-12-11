package com.notomatoesplease.util;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.PizzaProperty;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;

public class PizzaUtil<T extends PizzaProperty> {

    public static final PizzaUtil<Sauce> SAUCE_UTIL = new PizzaUtil<Sauce>();
    public static final PizzaUtil<Topping> TOPPING_UTIL = new PizzaUtil<Topping>();
    public static final PizzaUtil<Dough> DOUGH_UTIL = new PizzaUtil<Dough>();
    public static final PizzaUtil<Size> SIZE_UTIL = new PizzaUtil<Size>();

    private final Function<T, String> GET_NAME_FUNCTION = new Function<T, String>() {
        public String apply(final T from) {
            return from.getName();
        }
    };

    private final Ordering<T> NAME_ORDERING = Ordering.natural().onResultOf(GET_NAME_FUNCTION);

    /**
     * Sorts given items by name and returns a sorted immutable set.
     * 
     * @param items
     *            - list of items that need to be sorted
     * @return immutable, sorted set of the given items
     */
    public ImmutableSortedSet<T> sortByName(final List<T> items) {
        return ImmutableSortedSet.orderedBy(NAME_ORDERING).addAll(items).build();
    }

    /**
     * Returns a list of item names.
     * 
     * @param items
     * @return list of item names
     */
    public List<String> getNames(final List<T> items) {
        return Lists.transform(items, GET_NAME_FUNCTION);
    }
}
