Design Changes:

Model:
  - Model was extended using the Square pattern to accommodate repeated stock transactions,
    even those without end or those that occur in the future
  - The model's internal historian was extended using the square pattern to accommodate
    getting the earliest stock date and price on or after a certain date.
  - A new class IRepeatingStockTransaction was created to represent repeat stock transactions with
    the capability to be represented as real stock transactions for any past statistical analysis.
    This makes the representation flexible if future stock forecasting is to be introduced.
Controller:
  - Created new commands for adding multiple stocks by weight,
    as well as creating a portfolio with dollar cost averaging.
  - Created a new interface and class for features to create and execute an appropriate command.
  - Moved input handling from the constructor of the commands
    to be part of the parsing for the Text-Based Controller.
    Commands now take in the native data type in their constructor to streamline reuse.
  - The command for portfolio creation was split into flexible and inflexible portfolio creation
  - The command for inflexible portfolio creation kept the original input handler constructor.
  View:
  - Wrote a new JFrame based view that is also an object adapter of the previous graphingtext view.
    This allows all of our previous commands to function as needed, and for the JFrame View to
    simply take the output of the object adaptee and place it in the right GUI element.
    The JFrame view is broken up by a home tab and a tab for each portfolio.
  - Created a library of reusable UI elements, such as specialized text fields.


This program is broken down using the Model/View/Controller paradigm
such that each component has the following responsibilities:

## The Model:
- Managing the composition of each portfolio
  - Stocks are represented as unique combinations of ticker, date, and quantity.
  There can only be one instance of a unique stock symbol on a given date.
- Managing the historical values of stocks in the portfolio(s)
  The Model has a Historian that keeps stock prices in memory.
   If it doesn't have what the user is requesting,
    it gets parsed inputs from the controller by a CallBack Function using the AlphaVantageReader
- Providing a representation of the composition of each portfolio
- Representing the value of the portfolio at a given date or over a specified period of time.
- Providing the cost basis of a portfolio on a given date
- Providing any necessary stock prices on a given date or over a specified period of time.

## The Controller:
- Reading portfolio configurations from a source.
- Saving portfolio configurations to a destination.
- Handling requests from a user input (i.e. the console) for all commands.
- Performs input validation to make sure inputs are not empty, null, or malformed.
- Performs unique statistical analysis using stock and portfolio performance
  by requesting data from the model.
- API calls are made by the controller in the AlphaVantageReader
  as a result of a callback function in the model.

## The View
- Display the configuration of the portfolio.
- Display the value of the portfolio at a given date.
- Display any stock statistics as requested.
- Display any status logs or error messages as applicable.
- Plotting the performance of a portfolio or stock as requested.
  The View performs all necessary scaling and raw data conversion internally.