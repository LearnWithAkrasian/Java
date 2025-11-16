package com.imran.web;



import com.imran.dto.NumberListDTO;
import com.imran.repository.JDBCNumberListRepository;
import com.imran.service.NumberListService;
import com.imran.service.NumberListServiceImpl;
import com.imran.util.SecurityContext;
import com.imran.util.ValidationUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet("/home")
public class Home extends HttpServlet {

    private NumberListService numberListService
            = new NumberListServiceImpl(new JDBCNumberListRepository());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/home.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        NumberListDTO numberListDTO = copyParameters(req);
        var errors = ValidationUtil.getInstance().validate(numberListDTO);
        if (!errors.isEmpty()) {
            errorEncounter(req, resp, errors, numberListDTO);
        } else if (!isValidInput(numberListDTO.getInputValues())) {
            errors.put("inputValues", "Input is not accurate format, please check your input");
        } else if (!isValidInput(numberListDTO.getSearchValue())) {
            errors.put("searchValue", "Search value is not accurate format, please check your searchValue");
        } else if (!haveAnyNumber(numberListDTO.getInputValues())) {
            errors.put("inputValues", "Please give at least one Integer number");
        } else if (!haveAnyNumber(numberListDTO.getSearchValue())) {
            errors.put("searchValue", "Please give at least one Integer number");
        } else {
            boolean result = numberListService.searchTheNumber(numberListDTO);
            req.setAttribute("result", result);
            req.setAttribute("numberListDTO", numberListDTO);
            req.getRequestDispatcher("/WEB-INF/result.jsp")
                    .forward(req, resp);
        }

        if (!errors.isEmpty())
            errorEncounter(req, resp, errors, numberListDTO);
    }

    private void errorEncounter(HttpServletRequest req,
                                HttpServletResponse resp,
                                Map<String, String> errors,
                                NumberListDTO numberListDTO)
            throws ServletException, IOException{
        req.setAttribute("errors", errors);
        req.setAttribute("numberListDTO", numberListDTO);
        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, resp);
    }
    private boolean haveAnyNumber(String searchValue) {
        Predicate<Character> isDigit = Character::isDigit;

        return searchValue
                .chars()
                .mapToObj(c -> (char)c)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(isDigit);
    }

    private boolean isValidInput(String inputValues) {
        Predicate<Character> isDigit = Character::isDigit;
        Predicate<Character> isSpace = c -> c == ' ';
        Predicate<Character> isComma = c -> c == ',';

        // It returns true if all characters are either
        // digits or spaces or commas; otherwise, it returns false.
        return inputValues
                .chars()                              //Get an IntStream of characters
                .mapToObj(c -> (char)c)               //Convert to a Stream<Character>
                .collect(Collectors.toList())         //Collect to List<Character>
                .stream()
                .allMatch(isDigit.or(isSpace).or(isComma));
    }

    private NumberListDTO copyParameters(HttpServletRequest req) {
        NumberListDTO numberListDTO = new NumberListDTO();
        numberListDTO.setInputValues(req.getParameter("inputValues"));
        numberListDTO.setSearchValue(req.getParameter("searchValue"));
        numberListDTO.setUser(SecurityContext.getCurrentUser(req));
        return numberListDTO;
    }
}
