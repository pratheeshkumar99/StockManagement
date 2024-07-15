package modeltest.stocktest;

import java.time.LocalDate;
import model.IStock;
import model.Stock;

/**
 * Test the Stock class using the tests written in IStockTest.
 */
public class StockTest extends IStockTest {

  @Override
  protected IStock createIStock(String name, int quantity, LocalDate date) {
    return new Stock(name, quantity, date);
  }
}
