package controllertest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import controller.IMainController;
import controller.OldMainMenu;
import view.text.GraphingTextView;
import view.text.IGraphingView;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the Load and Save Operations of the portfolio.
 */
public class LoadAndSavePortfolioTest {
  @Test
  public void validFileIMMutableFileLoadTestAndTestIMMutability() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("1\np1\n4\nIMutablePortfolio" +
            "\np1\n6\n2\ntest/testdata/IMutablePortfolio.csv\n4\nIMutablePortfolio" +
            "\n8\nIMutablePortfolio\np1\nNFLX\n10\n2024-03-22\n4\n" +
            "IMutablePortfolio\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewResult = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Portfolio created successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name\n" +
            "Enter the name of the portfolio\n" +
            "p1 composition:\n" +
            "This portfolio does not contain any stocks\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the type of portfolio that you want to Load\n" +
            "1. Mutable Portfolio\n" +
            "2. Immutable Portfolio\n" +
            "\n" +
            "Enter the path of the portfolio file: \n" +
            "Portfolio Loaded Successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewResult, viewOutput.toString());
  }

  @Test
  public void validFileIMMutableFileLoadTestAndTestIMMutabilityInvalidFilePath() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("1\np1\n4\nIMutablePortfolio" +
            "\np1\n6\n2\nInvalidFilePath.csv\n\n6\n2\ntest/testdata/IMutablePortfolio.csv\n4" +
            "\nIMutablePortfolio" +
            "\n8\nIMutablePortfolio\np1\nNFLX\n10\n2024-03-22\n4\nIMutablePortfolio\nq").
            getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewResult = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Portfolio created successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name\n" +
            "Enter the name of the portfolio\n" +
            "p1 composition:\n" +
            "This portfolio does not contain any stocks\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the type of portfolio that you want to Load\n" +
            "1. Mutable Portfolio\n" +
            "2. Immutable Portfolio\n" +
            "\n" +
            "Enter the path of the portfolio file: \n" +
            "Invalid file path. Please enter a valid file path.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Invalid choice\n" +
            "Choose an option:\n" +
            "Enter the type of portfolio that you want to Load\n" +
            "1. Mutable Portfolio\n" +
            "2. Immutable Portfolio\n" +
            "\n" +
            "Enter the path of the portfolio file: \n" +
            "Portfolio Loaded Successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewResult, viewOutput.toString());
  }


  @Test
  public void validFileIMMutableFileLoadTestAndTestIMMutabilityEmptyPath() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("1\np1\n4\nIMutablePortfolio" +
            "\np1\n6\n2\n \ntest/testdata/IMutablePortfolio.csv\n4\nIMutablePortfolio" +
            "\n8\nIMutablePortfolio\np1\nNFLX\n10\n2024-03-22\n4\n" +
            "IMutablePortfolio\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewResult = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Portfolio created successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name\n" +
            "Enter the name of the portfolio\n" +
            "p1 composition:\n" +
            "This portfolio does not contain any stocks\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the type of portfolio that you want to Load\n" +
            "1. Mutable Portfolio\n" +
            "2. Immutable Portfolio\n" +
            "\n" +
            "Enter the path of the portfolio file: \n" +
            "Path cannot be empty\n" +
            "Enter the path of the portfolio file: \n" +
            "Portfolio Loaded Successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "IMutablePortfolio composition:\n" +
            "2024 Mar 27: 10.00 shares bought of NFLX\n" +
            "\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewResult, viewOutput.toString());
  }


  @Test
  public void validSaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("7\np1\n/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    System.out.println(actualModelResult);
    System.out.println(actualViewResult);
    String expectedModelResult = "is portfolio present called with portfolioName:p1" +
            " save portfolio called with portfolioName:p1 path:/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Enter the path of the portfolio file:  Portfolio saved successfully! " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void InvalidPortfolioNameSaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p2");
    InputStream simulatedInput = new ByteArrayInputStream(("7\np1\np2\n/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    System.out.println(actualModelResult);
    System.out.println(actualViewResult);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 is" +
            " portfolio present called with portfolioName:p2 save portfolio called with" +
            " portfolioName:p2 path:/Users/ratnakumari/IdeaProjects/Assigment5/" +
            "CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio " +
            "Enter the path of the portfolio file:  Portfolio saved successfully! " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void InvalidPortfolioNameEmptySaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p2");
    InputStream simulatedInput = new ByteArrayInputStream(("7\n \np2\n/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    System.out.println(actualModelResult);
    System.out.println(actualViewResult);
    String expectedModelResult = "is portfolio present called with portfolioName:p2 save " +
            "portfolio called with portfolioName:p2 path:/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Portfolio name cannot be empty! Enter the name of the portfolio" +
            " Enter the path of the portfolio file:  Portfolio saved successfully! " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void InvalidPortfolioNameNullSaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p2");
    InputStream simulatedInput = new ByteArrayInputStream(("7\n\np2\n/Users/" +
            "ratnakumari/IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    System.out.println(actualModelResult);
    System.out.println(actualViewResult);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p2 save portfolio called with portfolioName:p2 path:/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Portfolio name cannot be empty! Enter the name of the " +
            "portfolio Enter the path of the portfolio file:  Portfolio saved " +
            "successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void InvalidPathEmptySaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p2");
    InputStream simulatedInput = new ByteArrayInputStream(("7\n\np2\n \n/Users/" +
            "ratnakumari/IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p2" +
            " save portfolio called with portfolioName:p2 path:/Users/ratnakumari/" +
            "IdeaProjects/Assigment5/CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Portfolio name cannot be empty! Enter the name of the portfolio" +
            " Enter the path of the portfolio file:  Path cannot be empty Enter the path" +
            " of the portfolio file:  Portfolio saved successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void InvalidPathNullSaveTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p2");
    InputStream simulatedInput = new ByteArrayInputStream(("7\n\np2\n\n/Users/" +
            "ratnakumari/IdeaProjects/Assigment5/CS5010Assignment5/test/savedData\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p2 save " +
            "portfolio called with portfolioName:p2 path:/Users/ratnakumari/IdeaProjects/" +
            "Assigment5/CS5010Assignment5/test/savedData";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the path of " +
            "the portfolio file:  Path cannot be empty Enter the path of the portfolio file:  " +
            "Portfolio saved successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void ValidFileMutableSaveTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("1\nMutablePortfolio\n8\n" +
            "MutablePortfolio\nNFLX\n10" +
            "\n2024-03-22\n8\nMutablePortfolio\nAAPL\n10\n2024-03-20\n7\nMutablePortfolio" +
            "\ntest/testdata\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewResult = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Portfolio created successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the path of the portfolio file: \n" +
            "Portfolio saved successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewResult, viewOutput.toString());
  }


  @Test
  public void ValidFileInvalidPathSaveTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("1\nf1\n8\nf1\nNFLX\n2" +
            "\n2024-03-22\n7\nf1\ntest/InvalidFolder\n7\nf1\ntest/testdata\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewResult = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Portfolio created successfully\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the quantity of stocks you want to buy\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Stock bought successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the path of the portfolio file: \n" +
            "Input Error: Invalid Path.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the name of the portfolio\n" +
            "Enter the path of the portfolio file: \n" +
            "Portfolio saved successfully!\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewResult, viewOutput.toString());
  }
}
