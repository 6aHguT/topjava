package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal meal_1 = new Meal(1001, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal_2 = new Meal(1002, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal_3 = new Meal(1003, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal_4 = new Meal(1004, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal_5 = new Meal(1005, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal meal_6 = new Meal(1006, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal meal_7 = new Meal(1007, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal meal_8 = new Meal(1008, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected)
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime)
                        .reversed())
                .collect(Collectors.toList()));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }


}
