package FYP;

import java.nio.file.AccessDeniedException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler implements ErrorController {

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/403");
        return mav;
    }

    @RequestMapping("/error")
    public String handleError() {
        // Default error view
        return "error/403";
    }
}
