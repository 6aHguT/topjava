package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DBInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet
{
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("redirect to meals");
        //List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(2000);
        //List<Meal> meals = dbInMemory.getMeals();
        int calorrr = Integer.parseInt(request.getParameter("calories") == null ? "0" : request.getParameter("calories"));
        System.out.println(request.getParameter("calories"));
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(DBInMemory.getDbInMemory().getMeals(), calorrr);
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
    }
}
