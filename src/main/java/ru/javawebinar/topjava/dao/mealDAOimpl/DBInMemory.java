package ru.javawebinar.topjava.dao.mealDAOimpl;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class DBInMemory
{
    private static DBInMemory dbInMemory = new DBInMemory();
    private List<Meal> meals;

    public static DBInMemory getDbInMemory()
    {
        if (dbInMemory == null)
        {
            return new DBInMemory();
        } else

            return dbInMemory;
    }

    private DBInMemory()
    {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
    }

    public List<Meal> getMeals()
    {
        return meals;
    }

    public void addMeals(Meal meal)
    {
        this.meals.add(meal);
    }
}
