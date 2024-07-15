package controller.inputhandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Predicate;

import view.text.IView;

/**
 * The InputHandler class represents the input handler for the program.
 */


public class InputHandler implements IInputHandler {
  private final Scanner s;
  private final IView view;


  /**
   * Creates an InputHandler object with the given scanner and view.
   *
   * @param scanner the scanner to be used.
   * @param view    the view to be used.
   */

  public InputHandler(Scanner scanner, IView view) {
    this.s = scanner;
    this.view = view;
  }

  /**
   * This method gets the input string from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input string.
   */

  @Override
  public String getInputString(String prompt, Predicate<String> validator, String errorMessage) {
    String input = null;
    do {
      view.displayMessage(prompt);
      input = s.nextLine();
      if (!validator.test(input)) {
        view.displayMessage(errorMessage);
        input = null;
      }
    }
    while (input == null);
    return input;
  }


  /**
   * This method gets the input integer from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input integer.
   */


  @Override
  public int getInputInteger(String prompt, Predicate<Integer> validator, String errorMessage) {
    Integer input = null;
    do {
      view.displayMessage(prompt);
      try {
        input = Integer.parseInt(s.nextLine());
        if (!validator.test(input)) {
          view.displayMessage(errorMessage);
          input = null;
        }
      } catch (NumberFormatException e) {
        view.displayMessage(errorMessage);
      }
    }
    while (input == null);
    return input;
  }


  /**
   * This method gets the input date from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input date.
   */

  @Override
  public LocalDate getInputDate(String prompt, Predicate<LocalDate> validator,
                                String errorMessage) {
    LocalDate ip;
    do {
      view.displayMessage(prompt);
      try {
        ip = LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (validator != null) {
          if (validator.test(ip)) {
            view.displayMessage(errorMessage);
            ip = null;
            continue;
          }
        }
        break;
      } catch (Exception e) {
        view.displayMessage(errorMessage);
        ip = null;
      }
    }
    while (ip == null);
    return ip;
  }

  @Override
  public double getInputDouble(String prompt, Predicate<Double> validator, String errorMessage) {
    Double input = null;
    do {
      view.displayMessage(prompt);
      try {
        input = Double.parseDouble(s.nextLine());
        if (!validator.test(input)) {
          view.displayMessage(errorMessage);
          input = null;
        }
      } catch (NumberFormatException e) {
        view.displayMessage(errorMessage);
      }
    }
    while (input == null);
    return input;
  }

  @Override
  public boolean getInputBoolean(String prompt, Predicate<Boolean> validator, String errorMessage) {
    Boolean input = null;
    do {
      view.displayMessage(prompt);
      try {
        input = Boolean.parseBoolean(s.nextLine());
        if (!validator.test(input)) {
          view.displayMessage(errorMessage);
          input = null;
        }
      } catch (NumberFormatException e) {
        view.displayMessage(errorMessage);
      }
    }
    while (input == null);
    return input;
  }
}
