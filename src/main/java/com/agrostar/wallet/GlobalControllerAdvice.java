package com.agrostar.wallet;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;

@ControllerAdvice(basePackages = {"com.agrostar.wallet"})
public class GlobalControllerAdvice {

  @ModelAttribute
  public void addAttributes(Model model) {
    model.addAttribute("message", "Welcome to My World!");
  }

  @ExceptionHandler(FileNotFoundException.class)
  public ModelAndView myError(Exception exception) {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.setViewName("error");
    return mav;
  }
}
