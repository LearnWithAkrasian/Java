package com.imran.web;

import com.google.gson.Gson;
import com.imran.dto.JsonDTO;
import com.imran.dto.RestApiDTO;
import com.imran.repository.JDBCNumberListRepository;
import com.imran.service.RESTApiService;
import com.imran.service.RESTApiServiceImpl;
import com.imran.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RESTAPI", value = "/api-rest")
public class RestApi extends HttpServlet {

    private RESTApiService restApiService
            = new RESTApiServiceImpl(new JDBCNumberListRepository());
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("dateTime", "yyyy-MM-dd HH:mm:ss");
        req.getRequestDispatcher("/WEB-INF/rest-api.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        var restApiDTO = copy(req);
        var errors = ValidationUtil.getInstance().validate(restApiDTO);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("restApiDTO", restApiDTO);
            req.getRequestDispatcher("/WEB-INF/rest-api.jsp")
                    .forward(req, resp);
        } else if (!restApiService.isCorrectFormate(restApiDTO.getStartTime())) {
            errors.put("startTime", "Date and Time is not in correct Format");
            req.setAttribute("errors", errors);
            req.setAttribute("restApiDTO", restApiDTO);
            req.getRequestDispatcher("/WEB-INF/rest-api.jsp")
                    .forward(req, resp);
        }
        else if (!restApiService.isCorrectFormate(restApiDTO.getEndingTime())) {
            errors.put("endingTime", "Date and Time is not in correct Format");
            req.setAttribute("errors", errors);
            req.setAttribute("restApiDTO", restApiDTO);
            req.getRequestDispatcher("/WEB-INF/rest-api.jsp")
                    .forward(req, resp);
        } else {
            // After the Validation complete save and search operation is performed
            // by invoking search() method.
            restApiService.search(restApiDTO);

            // creating jsonDTO object and setting up the properties of jsonDTO
            JsonDTO jsonDTO = new JsonDTO();
            jsonDTO.setPayload(restApiDTO.getPayloadDTOS());
            jsonDTO.setStatus(restApiDTO.isStatus());
            jsonDTO.setUser_id(restApiDTO.getUserId());

            // creating Json object to send response
            String resApiDTOJson = new Gson().toJson(jsonDTO);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(resApiDTOJson);
            out.flush();
        }
    }
    private RestApiDTO copy(HttpServletRequest req) {
        var restApiDTO = new RestApiDTO();
        restApiDTO.setUserId(req.getParameter("userId"));
        restApiDTO.setStartTime(req.getParameter("startTime"));
        restApiDTO.setEndingTime(req.getParameter("endingTime"));

        return restApiDTO;
    }
}
