package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("----------------------");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("+++++++++++++++++++++");
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(1007, ADMIN_ID);
        assertMatch(meal, meal_7);
    }
    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1007, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(1007, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), meal_8);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1007,USER_ID);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, meal_1, meal_2, meal_3, meal_4, meal_5, meal_6);
    }

    @Test
    public void update() throws Exception {

        Meal updated = new Meal(meal_1);
        updated.setDescription("UpdatedName");
        updated.setCalories(22222);
        service.update(updated, USER_ID);
        assertMatch(service.get(meal_1.getId(), USER_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "ЗавтракЗэБэст", 5000);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal,  meal_1, meal_2, meal_3, meal_4, meal_5, meal_6);
    }
        //    meal_1, meal_2, meal_3, meal_4, meal_5, meal_6, meal_7, meal_8
}