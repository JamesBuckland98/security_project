package com.asgdrones.drones.controllers;

import com.asgdrones.drones.enums.Templates;
import org.apache.logging.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
public class ErrorController extends AbstractErrorController {
    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ErrorController.class);
    private Templates page;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request, Model model){
        final UUID uuid = UUID.randomUUID();
        final HttpStatus httpStatus = getStatus(request);
        final Map<String , Object> errorMap = getErrorAttributes(request,true);
        final String uri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        model.addAttribute("errorUUiD",uuid);

        for (Map.Entry<String, Object> error : errorMap.entrySet()){
            log.error("[ERROR:{}] [{}] -> [{}]", uuid, error.getKey(), error.getValue());
        }
        if (httpStatus == HttpStatus.FORBIDDEN){
            page = Templates.ACCESS_DENIED;
            return new ModelAndView(page.toString(),model.asMap());
        } else {
            page = Templates.ERROR;
            return new ModelAndView(page.toString(),model.asMap());
        }
    }
}
