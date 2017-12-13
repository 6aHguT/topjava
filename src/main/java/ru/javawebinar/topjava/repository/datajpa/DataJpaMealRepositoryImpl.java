package ru.javawebinar.topjava.repository.datajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(DataJpaMealRepositoryImpl.class);

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;


    @Override
    public Meal save(Meal meal, int userId) {
        log.info("DATAJPA: save");
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("DATAJPA: delete");
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("DATAJPA: get");
        Meal meal = crudMealRepository.findById(id).orElse(null);
        return meal == null ? null : meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("DATAJPA: getAll");
        return crudMealRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        log.info("DATAJPA: getBetween");
        return crudMealRepository.getMealsByDateTimeBetweenOrderByDateTimeDesc(startDate, endDate)
                .stream()
                .filter(meal -> meal.getUser().getId() == userId)
                .collect(Collectors.toList());
    }
}
