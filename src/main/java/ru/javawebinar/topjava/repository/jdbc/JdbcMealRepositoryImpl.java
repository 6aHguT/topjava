package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository("jdbcMealRepository")
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    public JdbcMealRepositoryImpl() {
    }

    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("user_meals")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", AuthorizedUser.id())
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE user_meals SET dateTime=:dateTime, description=:description, " +
                            "calories=:calories WHERE user_id=:user_id AND id=:id", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM user_meals WHERE user_id=? AND id=?", userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {

        List<Meal> meals = jdbcTemplate.query("SELECT * FROM user_meals WHERE user_id=? AND id=?", ROW_MAPPER, userId, id);
        return DataAccessUtils.singleResult(meals);

    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM user_meals WHERE user_id=? ORDER BY datetime DESC ", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAll(userId).stream().filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
