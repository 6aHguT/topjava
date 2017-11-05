package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class UserMealsUtil
{

    public static void main(String[] args)
    {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        mealList.sort(Comparator.comparing(UserMeal::getDateTime));

        for (int i = 0; i < mealList.size(); i++)
        {
            int caloriesPerDayTemp = 0;
            LocalDate day = mealList.get(i).getDateTime().toLocalDate();
            int indexOfCurrentDay = i;

            List<UserMealWithExceed> mealsPerDayWithExceed = new ArrayList<>();
            while ((indexOfCurrentDay < mealList.size()) && day.equals(mealList.get(indexOfCurrentDay).getDateTime().toLocalDate()))
            {
                caloriesPerDayTemp = caloriesPerDayTemp + mealList.get(indexOfCurrentDay).getCalories();
                if (isBetween(mealList.get(indexOfCurrentDay).getDateTime().toLocalTime(), startTime, endTime))
                {
                    mealsPerDayWithExceed.add(new UserMealWithExceed(mealList.get(indexOfCurrentDay), true));
                }
                indexOfCurrentDay++;
            }

            if (caloriesPerDayTemp > caloriesPerDay)
            {
                userMealWithExceedList.addAll(mealsPerDayWithExceed);
            }
            i = indexOfCurrentDay - 1;
        }
        return userMealWithExceedList;
    }
}
