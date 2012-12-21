package com.notomatoesplease.util;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Ingredient;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Topping;

public class PizzaUtil<T extends Ingredient> {

    public static final PizzaUtil<Sauce> SAUCE_UTIL = new PizzaUtil<Sauce>();
    public static final PizzaUtil<Topping> TOPPING_UTIL = new PizzaUtil<Topping>();
    public static final PizzaUtil<Dough> DOUGH_UTIL = new PizzaUtil<Dough>();

    private final Function<T, String> GET_NAME_FUNCTION = new Function<T, String>() {
        @Override
        public String apply(final T from) {
            return from.getName();
        }
    };

    private final Ordering<T> NAME_ORDERING = Ordering.natural().onResultOf(GET_NAME_FUNCTION);
    private final Predicate<T> predicate = new Predicate<T>() {

        @Override
        public boolean apply(final T item) {
            return item.isVisible();
        }
    };

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

    public List<T> getVisibleOnly(final List<T> items) {
        return Lists.newArrayList(Iterables.filter(items, predicate));
    }
}
