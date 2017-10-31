package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;


/**
 * GKislin
 * 31.05.2015.
 */
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
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        ArrayList<UserMeal> mealListTemp = new ArrayList<>();
        mealListTemp.addAll(mealList);
        mealListTemp.sort(Comparator.comparing(UserMeal::getDateTime));
        int indexOfCurrentDay;
        int caloriesPerDayTemp = 0;

        for (int i = 0; i < mealListTemp.size(); i++)
        {
            LocalDate day = mealListTemp.get(i).getDateTime().toLocalDate();
            indexOfCurrentDay = i;

            while ((indexOfCurrentDay < mealListTemp.size()) && day.equals(mealListTemp.get(indexOfCurrentDay).getDateTime().toLocalDate()))
            {
                caloriesPerDayTemp = caloriesPerDayTemp + mealListTemp.get(indexOfCurrentDay).getCalories();
                userMealWithExceedList.add(new UserMealWithExceed(mealListTemp.get(indexOfCurrentDay), true));
                indexOfCurrentDay++;
            }

            if (caloriesPerDayTemp <= caloriesPerDay)
            {
                while ((indexOfCurrentDay--) > i)
                {
                    mealListTemp.remove(indexOfCurrentDay);
                    userMealWithExceedList.remove(indexOfCurrentDay);
                }
                i = indexOfCurrentDay;
            } else
            {
                i = indexOfCurrentDay - 1;
            }

            caloriesPerDayTemp = 0;
        }
        userMealWithExceedList.removeIf(userMealWithExceed -> (!isBetween(userMealWithExceed.getDateTime().toLocalTime(), startTime, endTime)));
        /*for (UserMealWithExceed x : userMealWithExceedList)
        {
            System.out.println(x.toString());
        }*/
        return userMealWithExceedList;
    }
}
