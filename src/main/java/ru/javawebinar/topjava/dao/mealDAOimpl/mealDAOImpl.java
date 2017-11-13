package ru.javawebinar.topjava.dao.mealDAOimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class mealDAOImpl implements MealDAO
{
    private static final Logger log = getLogger(mealDAOImpl.class);
    private DBInMemory dbInMemory = DBInMemory.getDbInMemory();

    @Override
    public void createMeal(Meal meal)
    {
        log.debug("create meal");
        dbInMemory.addMeals(meal);
    }

    @Override
    public void updateMeal(Meal meal)
    {
        log.debug("update meal");
        if (dbInMemory.getMeals().contains(meal))
        {
        }
    }

    @Override
    public void deleteMeal(int id)
    {

    }

    @Override
    public List<Meal> getAllMeals()
    {
        return null;
    }

    @Override
    public Meal getMealById(int id)
    {
        return null;
    }

    @Override
    public List<Meal> getAllMeals(String mealName)
    {
        return null;
    }
}
