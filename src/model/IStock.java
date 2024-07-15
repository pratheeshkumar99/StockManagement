package model;

import java.time.LocalDate;

/**
 * Represents a transaction where a number of shares of a single stock are bought or sold.
 */

public interface IStock {

  /**
   * Gets the name of the stock.
   *
   * @return the name of the stock
   */
  String getName();

  /**
   * Gets the quantity of shares of the single stock. A negative value indicates that the stocks
   * were sold. A positive value indicates that the stocks were bought.
   *
   * @return the quantity of shares.
   */
  double getQuantity();

  /**
   * Get the date this stock was bought or sold.
   *
   * @return the date of the transaction for this stock.
   */
  LocalDate getDate();

}
