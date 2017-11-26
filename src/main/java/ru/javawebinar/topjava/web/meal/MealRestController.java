package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll for userId={}", AuthorizedUser.id());
        getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()).forEach(System.out::println);
        return getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get meal {} for userId={}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete meal {} for userId={}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {} for userId={}", meal, AuthorizedUser.id());
        checkNew(meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} for userId={}", meal, id, AuthorizedUser.id());
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

}