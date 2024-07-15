package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * As an implementation of the IStocks interface, Stocks provides a representation of any stock
 * given a name and quantity.
 */

public class Stock implements IStock {

  private final String name;
  private final double quantity;
  private final LocalDate date;

  /**
   * Creates a new instance of Stocks with the provided name and quantity.
   *
   * @param name     the name of the stock
   * @param quantity the quantity of the stock
   */

  public Stock(String name, double quantity, LocalDate date) {
    this.name = name;
    this.quantity = quantity;
    this.date = date;
  }

  /**
   * Gets the ticker symbol of the stock.
   *
   * @return the name of the stock
   */

  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Gets the quantity of the stock.
   *
   * @return the quantity of the stock
   */

  @Override
  public double getQuantity() {
    return this.quantity;
  }

  @Override
  public LocalDate getDate() {
    return this.date;

  }

  /**
   * Determines if two stocks are identical.
   * @param o some other object.
   * @return True if they are both IStock and share the same date, ticker, and quantity.
   *         False otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IStock)) {
      return false;
    }
    IStock stock = (Stock) o;
    return Math.abs(getQuantity() - stock.getQuantity()) < 0.01
      && getName().equals(stock.getName())
      && this.getDate().isEqual(stock.getDate());
  }

  @Override
  public String toString() {
    return "Stock{" + "name='" + name + '\'' + ", quantity=" + quantity + ", date=" + date + '}';
  }

  /**
   * Get the hashcode of the Stock using its fields.
   * @return The Stock's hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, quantity, date);
  }
}