package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO
{
    public void createMeal(Meal meal);
    public void updateMeal(Meal meal);
    public void deleteMeal(int id);
    public List<Meal> getAllMeals();
    public Meal getMealById(int id);
    public List<Meal> getAllMeals(String mealName);

}
