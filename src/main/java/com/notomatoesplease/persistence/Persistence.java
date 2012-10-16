package com.notomatoesplease.persistence;

import java.util.List;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;

public interface Persistence {

    List<Size> getSizes();

    List<Dough> getDoughs();

    List<Sauce> getSauces();

    List<Topping> getToppings();

    void saveToppings(List<Topping> toppings);

    void saveDoughs(List<Dough> doughs);

    void saveSauces(List<Sauce> sauces);

}
