package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        saveRecipes();
    }

    private void saveRecipes() {

        Recipe perfectGuacamole = getPerfectGuacamoleRecipe();
        Recipe spicyGrilledChicken = getGrilledChickenRecipe();

        recipeRepository.save(perfectGuacamole);
        recipeRepository.save(spicyGrilledChicken);

    }

    private Recipe getGrilledChickenRecipe() {

        Recipe grilledChickenRecipe = new Recipe();

        grilledChickenRecipe.setDescription("Spicy Grilled Chicken Tacos");
        grilledChickenRecipe.setPrepTime(20);
        grilledChickenRecipe.setDifficulty(Difficulty.MODERATE);

        Notes notes = new Notes();
        notes.setRecipe(grilledChickenRecipe);
        notes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

        grilledChickenRecipe.setNotes(notes);

        grilledChickenRecipe.setDirections("1. Prepare a gas or charcoal grill for medium-high, direct heat\n" +
                "2. Make the marinade and coat the chicken:\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.");

        grilledChickenRecipe.getIngredients().add(new Ingredient("Ancho chili powder", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Tablespoon").get(), grilledChickenRecipe));
        grilledChickenRecipe.getIngredients().add(new Ingredient("Dried oregano", new BigDecimal("1"), unitOfMeasureRepository.findByDescription("Teaspoon").get(), grilledChickenRecipe));

        Category americanCategory = categoryRepository.findByDescription("American").get();

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(americanCategory);

        grilledChickenRecipe.setCategories(categorySet);

        return grilledChickenRecipe;

    }

    private Recipe getPerfectGuacamoleRecipe() {

        Recipe perfectGuacamoleRecipe = new Recipe();

        perfectGuacamoleRecipe.setDescription("Perfect Guacamole");
        perfectGuacamoleRecipe.setPrepTime(10);
        perfectGuacamoleRecipe.setDifficulty(Difficulty.EASY);

        Notes notes = new Notes();
        notes.setRecipe(perfectGuacamoleRecipe);
        notes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");

        perfectGuacamoleRecipe.setNotes(notes);

        perfectGuacamoleRecipe.setDirections("1. Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "2. Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)");

        perfectGuacamoleRecipe.getIngredients().add(new Ingredient("Ripe Avocados", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Each").get(), perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal("0.25"), unitOfMeasureRepository.findByDescription("Teaspoon").get(), perfectGuacamoleRecipe));

        Category mexicanCategory = categoryRepository.findByDescription("Mexican").get();
        Category fastFoodCategory = categoryRepository.findByDescription("Fast Food").get();

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(mexicanCategory);
        categorySet.add(fastFoodCategory);

        perfectGuacamoleRecipe.setCategories(categorySet);

        return perfectGuacamoleRecipe;

    }
}
