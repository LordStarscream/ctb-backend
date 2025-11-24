# Crypto Trading Book Backend - API Documentation

## Overview

This backend application provides REST APIs for tracking cryptocurrency trades, recording price history, and generating German tax reports based on the FIFO (First In First Out) method.

## Base URL

```
http://localhost:8080/api
```

## Endpoints

### Trade Management

#### Create Trade
- **POST** `/trades`
- **Description**: Create a new trade entry
- **Request Body**:
```json
{
  "cryptoSymbol": "BTC",
  "tradeType": "BUY",
  "amount": 0.5,
  "pricePerUnit": 50000,
  "tradeDate": "2024-01-15T10:30:00",
  "notes": "Purchase from exchange"
}
```
- **Response**: Trade object with generated ID

#### Get All Trades
- **GET** `/trades`
- **Query Parameters**:
  - `symbol` (optional): Filter by cryptocurrency symbol
  - `startDate` (optional): Filter by start date (ISO 8601)
  - `endDate` (optional): Filter by end date (ISO 8601)
- **Response**: List of trades

#### Get Trade by ID
- **GET** `/trades/{id}`
- **Response**: Trade object

#### Update Trade
- **PUT** `/trades/{id}`
- **Request Body**: Same as Create Trade
- **Response**: Updated trade object

#### Delete Trade
- **DELETE** `/trades/{id}`
- **Response**: 204 No Content

### Price Management

#### Record Price
- **POST** `/prices`
- **Description**: Record a cryptocurrency price at a specific date/time
- **Request Body**:
```json
{
  "symbol": "BTC",
  "price": 50000,
  "priceDate": "2024-01-15T10:00:00",
  "source": "CoinGecko"
}
```
- **Response**: Price object with generated ID

#### Get All Prices
- **GET** `/prices`
- **Query Parameters**:
  - `symbol` (optional): Filter by cryptocurrency symbol
  - `startDate` (optional): Filter by start date (ISO 8601)
  - `endDate` (optional): Filter by end date (ISO 8601)
- **Response**: List of prices

#### Get Price by ID
- **GET** `/prices/{id}`
- **Response**: Price object

#### Get Latest Price
- **GET** `/prices/latest/{symbol}`
- **Response**: Most recent price for the symbol

#### Get Price at Date
- **GET** `/prices/at-date`
- **Query Parameters**:
  - `symbol` (required): Cryptocurrency symbol
  - `date` (required): Date/time (ISO 8601)
- **Response**: Closest price before or at the specified date

### Tax Reports

#### Generate German Tax Report
- **GET** `/tax-reports/german/{year}`
- **Description**: Generate tax report for a specific year using FIFO method
- **Response**:
```json
{
  "reportDate": "2024-11-24T10:00:00",
  "taxYear": 2024,
  "totalGains": 5000.00,
  "totalLosses": -1000.00,
  "netGainLoss": 4000.00,
  "taxableEvents": [
    {
      "tradeId": 5,
      "cryptoSymbol": "BTC",
      "tradeDate": "2024-03-15T14:30:00",
      "amount": 0.5,
      "buyPrice": 48000,
      "sellPrice": 52000,
      "gainLoss": 2000.00,
      "description": "Sold 0.5 BTC (bought at 48000, sold at 52000)"
    }
  ]
}
```

#### Calculate Portfolio Value
- **GET** `/tax-reports/portfolio-value`
- **Query Parameters**:
  - `date` (required): Date/time (ISO 8601)
- **Description**: Calculate portfolio value at a specific date
- **Response**:
```json
{
  "BTC": 25000.00,
  "ETH": 15000.00
}
```

## Data Models

### Trade
- `id`: Long (auto-generated)
- `cryptoSymbol`: String (required, uppercase)
- `tradeType`: Enum (BUY, SELL)
- `amount`: BigDecimal (required, positive)
- `pricePerUnit`: BigDecimal (required, positive)
- `tradeDate`: LocalDateTime (required)
- `totalValue`: BigDecimal (calculated)
- `notes`: String (optional, max 500 chars)
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime

### CryptoPrice
- `id`: Long (auto-generated)
- `symbol`: String (required, uppercase)
- `price`: BigDecimal (required, positive)
- `priceDate`: LocalDateTime (required)
- `source`: String (optional, max 50 chars)
- `createdAt`: LocalDateTime

## German Tax Calculation

The system implements FIFO (First In First Out) method for calculating cryptocurrency gains/losses:

1. **FIFO Method**: When selling crypto, the oldest purchases are matched first
2. **Gain/Loss Calculation**: (Sell Price - Buy Price) × Amount
3. **Annual Reports**: Reports are generated per calendar year
4. **Portfolio Valuation**: Calculate the current value of holdings at any date

### Example

1. Buy 1 BTC at €40,000 on Jan 1
2. Buy 1 BTC at €45,000 on Feb 1
3. Sell 1.5 BTC at €50,000 on Mar 1

**Calculation**:
- First 1 BTC matched with Jan 1 purchase: Gain = (50,000 - 40,000) × 1 = €10,000
- Next 0.5 BTC matched with Feb 1 purchase: Gain = (50,000 - 45,000) × 0.5 = €2,500
- **Total Gain**: €12,500

## Error Responses

All errors return appropriate HTTP status codes with error messages:

```json
{
  "timestamp": "2024-11-24T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Trade not found with id: 123"
}
```

## Development

### H2 Console

The H2 database console is available at:
```
http://localhost:8080/h2-console
```

**Connection Settings**:
- JDBC URL: `jdbc:h2:mem:ctbdb`
- Username: `sa`
- Password: (empty)

### Running the Application

```bash
mvn spring-boot:run
```

### Running Tests

```bash
mvn test
```

## Notes

- All cryptocurrency symbols are automatically converted to uppercase
- Dates should be in ISO 8601 format: `YYYY-MM-DDTHH:MM:SS`
- All monetary values use BigDecimal for precision
- The application uses an in-memory H2 database for development
