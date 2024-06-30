package com.poscodx.jblog.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscodx.jblog.dto.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, Exception.class})
    public void handler(HttpServletRequest request,
                        HttpServletResponse response,
                        Exception e,
                        Model model) throws Exception {

        // 1. 로깅(logging)
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());

        // 2. 요청구분
        // json 요청 : request header에 application/json (O)
        // html 요청 : request header에 application/json (X)
        String accept = request.getHeader("accept");

        if(accept.matches(".*application/json.*")) {
            // 3. json 응답
            JsonResult jsonResult = JsonResult.fail(errors.toString());
            String jsonString = new ObjectMapper().writeValueAsString(jsonResult);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            OutputStream os = response.getOutputStream();
            os.write("{}".getBytes("utf-8"));
            os.close();
        } else {
            // 4. 사과 페이지(정상 종료)
            if(e instanceof NoHandlerFoundException) {
                request
                        .getRequestDispatcher("/error/404")
                        .forward(request, response);

            } else {
                request.setAttribute("error", errors.toString());
                request.getRequestDispatcher("/error/500")
                        .forward(request, response);
            }
        }
    }
}
