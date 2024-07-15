package modeltest.stocktest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import model.IStock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of the IStock interface.
 */
public abstract class IStockTest {


  protected IStock stock;

  /**
   * Protected abstract class for test implementations to use and create stocks.
   * @param name the ticket symbol of the stock.
   * @param quantity the quantity of stocks created.
   * @param date  the date the stock was bought or sold.
   * @return the created IStock.
   */
  protected abstract IStock createIStock(String name, int quantity, LocalDate date);

  @Before
  public void setUp() {
    stock = createIStock("NFLX", 40, LocalDate.of(2024, 3, 20));
  }

  @Test
  public void testGetName() {
    assertEquals("NFLX", stock.getName());
  }

  @Test
  public void testGetQuantity() {
    assertEquals(40, stock.getQuantity(), 0.0001);
  }

  @Test
  public void testGetDate() {
    assertEquals(LocalDate.of(2024, 3, 20), stock.getDate());
  }

  @Test
  public void testEquals() {
    //Assert that if they share the same name, quantity, and date, they are equal
    IStock stock1 = createIStock("NFLX", 30, LocalDate.of(2023, 2, 2));
    IStock stock2 = createIStock("NFLX", 30, LocalDate.of(2023, 2, 2));
    assertEquals(stock1, stock2);
    //Assert reflexive property
    IStock stockIdentical = stock1;
    assertEquals(stock1, stockIdentical);
    //Assert that if the quantity, name, or date are different, they are not equal
    IStock stock3 = createIStock("NFLX", -30, LocalDate.of(2023, 2, 2));
    assertNotEquals(stock1, stock3);
    IStock stock4 = createIStock("NFLOX", 30, LocalDate.of(2023, 2, 2));
    assertNotEquals(stock1, stock4);
    IStock stock5 = createIStock("NFLX", 30, LocalDate.of(2023, 2, 3));
    assertNotEquals(stock1, stock5);
  }

  @Test
  public void testHashCode() {
    IStock stock1 = createIStock("NFLX", 30, LocalDate.of(2023, 2, 2));
    IStock stock2 = createIStock("NFLX", 30, LocalDate.of(2023, 2, 2));
    assertEquals(stock1.hashCode(), stock2.hashCode());
  }
}
