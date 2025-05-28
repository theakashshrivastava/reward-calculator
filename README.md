# reward-calculator

# Reward Calculation API
 
This Spring Boot application calculates reward points for customers based on their transaction history. Reward points are computed according to a tiered structure, and results are grouped by customer and by month (limited to the past 3 months).

# Project Structure

<img width="269" alt="final" src="https://github.com/user-attachments/assets/3b03cbc5-fdd8-4f7a-9c47-92bc82f17cdd" />

reward-calculator



## Features
 
- Accepts customer_id {cId}
- Filters transactions from the past 3 months.
- Calculates reward points using a tiered formula:
  - 2 points for every dollar spent over $100
  - 1 point for every dollar spent over $50 up to $100
- Aggregates monthly and total reward points per customer.
- Input validation and global exception handling.
- Includes unit and integration tests.
 
## Technologies Used
 
- Java 21
- Spring Boot
- Maven
- JUnit 5 & Mockito
 
---

## API Endpoint
 
### `GET /api/rewards/calculate/{cId}`

GET API to fetch reward points for provided customer-id.

### `GET /api/rewards/calculate`

Fallback method when cId is null or empty.

---
 
## How to Run
 
### Prerequisites
 
- Java 21
- Maven 3.6+
 
### Steps
 
1. Clone the repository:
 
```bash
git clone https://github.com/your-repo/reward-calculator.git
cd reward-calculator
```
 
2. Build and run:
 
```bash
mvn clean install
mvn spring-boot:run
```
 
3. Test the API with Postman:
 
- **GET** `http://localhost:8080/api/rewards/calculate/{cId}`
---
 
## Testing
 
### Unit Tests
 
```bash
mvn test
```
