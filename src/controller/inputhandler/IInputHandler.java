package controller.inputhandler;

import java.time.LocalDate;
import java.util.function.Predicate;


/**
 * The IInputHandler interface represents the input handler for the program.
 */


public interface IInputHandler {

  /**
   * This method gets the input string from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input string.
   */
  public String getInputString(String prompt, Predicate<String> validator, String errorMessage);

  /**
   * This method gets the input integer from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input integer.
   */
  public int getInputInteger(String prompt, Predicate<Integer> validator, String errorMessage);

  /**
   * This method gets the input date from the user.
   *
   * @param prompt       the prompt to be displayed to the user.
   * @param validator    the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input date.
   */
  LocalDate getInputDate(String prompt, Predicate<LocalDate> validator, String errorMessage);

  /**
   * This method gets the input double from the user.
   * @param prompt the prompt to be displayed to the user.
   * @param validator the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input double.
   */

  double getInputDouble(String prompt, Predicate<Double> validator, String errorMessage);

  /**
   * This method gets the input boolean from the user.
   * @param prompt the prompt to be displayed to the user.
   * @param validator the validator to check if the input is valid.
   * @param errorMessage the error message to be displayed if the input is invalid.
   * @return the input boolean.
   */
  boolean getInputBoolean(String prompt, Predicate<Boolean> validator, String errorMessage);
}
