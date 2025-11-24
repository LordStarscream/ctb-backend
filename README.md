# Crypto Trading Book Backend

A Spring Boot backend application for tracking cryptocurrency trades and calculating German tax reports.

## Features

- **Trade Tracking**: Record and manage cryptocurrency buy/sell trades
- **Price History**: Store and retrieve cryptocurrency price data at specific dates/times
- **German Tax Reports**: Calculate tax reports using FIFO (First In First Out) method
- **Portfolio Valuation**: Calculate portfolio value at any point in time
- **REST API**: Comprehensive REST API for all operations

## Technologies

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Lombok
- JUnit 5 & Mockito

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/LordStarscream/ctb-backend.git
cd ctb-backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests

```bash
mvn test
```

## API Documentation

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for detailed API documentation.

### Quick API Examples

**Create a trade:**
```bash
curl -X POST http://localhost:8080/api/trades \
  -H "Content-Type: application/json" \
  -d '{
    "cryptoSymbol": "BTC",
    "tradeType": "BUY",
    "amount": 0.5,
    "pricePerUnit": 50000,
    "tradeDate": "2024-01-15T10:30:00",
    "notes": "Purchase from exchange"
  }'
```

**Record a price:**
```bash
curl -X POST http://localhost:8080/api/prices \
  -H "Content-Type: application/json" \
  -d '{
    "symbol": "BTC",
    "price": 50000,
    "priceDate": "2024-01-15T10:00:00",
    "source": "CoinGecko"
  }'
```

**Get tax report:**
```bash
curl http://localhost:8080/api/tax-reports/german/2024
```

## Database

The application uses H2 in-memory database for development. The H2 console is accessible at:
```
http://localhost:8080/h2-console
```

Connection settings:
- JDBC URL: `jdbc:h2:mem:ctbdb`
- Username: `sa`
- Password: (empty)

## German Tax Calculation

The system implements the FIFO (First In First Out) method for calculating cryptocurrency gains and losses, which is commonly used for German tax reporting:

1. When selling cryptocurrency, the oldest purchases are matched first
2. Gains/losses are calculated as: (Sell Price - Buy Price) × Amount
3. Reports are generated per calendar year
4. Portfolio valuation can be calculated at any specific date

## Project Structure

```
src/main/java/com/ctb/backend/
├── controller/          # REST API controllers
├── dto/                # Data Transfer Objects
├── model/              # JPA entities
├── repository/         # Spring Data repositories
└── service/            # Business logic services
```

## License

This project is licensed under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
