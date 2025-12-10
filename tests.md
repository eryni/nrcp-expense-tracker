# BASIC functions
backend-expense-api-layer 
after ExpenseController

curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 52.50,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 52.50
  }'

  {"baseCurrencyAmount":52.50,"category":"Food","currencySpent":"PHP","dateLogged":"2025-12-10T07:58:20.692431","datePurchased":"2025-12-09","exchangeRate":1.0,"expenseId":1,"isDeleted":false,"loggedBy":"test@example.com","pricePer":10.50,"purposeItem":"Groceries","quantity":5,"totalSpent":52.50}%                                                    

curl http://localhost:8080/api/expenses

[{"baseCurrencyAmount":52.50,"category":"Food","currencySpent":"PHP","dateLogged":"2025-12-10T07:58:21","datePurchased":"2025-12-09","exchangeRate":1.0000,"expenseId":1,"isDeleted":false,"loggedBy":"test@example.com","pricePer":10.50,"purposeItem":"Groceries","quantity":5,"totalSpent":52.50}]%   


curl http://localhost:8080/api/expenses/1
{"baseCurrencyAmount":52.50,"category":"Food","currencySpent":"PHP","dateLogged":"2025-12-10T07:58:21","datePurchased":"2025-12-09","exchangeRate":1.0000,"expenseId":1,"isDeleted":false,"loggedBy":"test@example.com","pricePer":10.50,"purposeItem":"Groceries","quantity":5,"totalSpent":52.50}% 

 curl http://localhost:8080/api/expenses/category/Food
[{"baseCurrencyAmount":52.50,"category":"Food","currencySpent":"PHP","dateLogged":"2025-12-10T07:58:21","datePurchased":"2025-12-09","exchangeRate":1.0000,"expenseId":1,"isDeleted":false,"loggedBy":"test@example.com","pricePer":10.50,"purposeItem":"Groceries","quantity":5,"totalSpent":52.50}]% 

08:00:07 in·Downloads/nrcp-expense-tracker/backend on  backend-expense-api-layer [⇡!?] curl "http://localhost:8080/api/expenses/date-range?startDate=2025-12-01&endDate=2025-12-31"
[{"baseCurrencyAmount":52.50,"category":"Food","currencySpent":"PHP","dateLogged":"2025-12-10T07:58:21","datePurchased":"2025-12-09","exchangeRate":1.0000,"expenseId":1,"isDeleted":false,"loggedBy":"test@example.com","pricePer":10.50,"purposeItem":"Groceries","quantity":5,"totalSpent":52.50}]%   

 curl http://localhost:8080/api/expenses/summary
{"total":52.5,"week":52.5,"month":52.5,"year":52.5,"day":0.0}%                         

curl -X PUT http://localhost:8080/api/expenses/1 \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Updated Groceries",
    "quantity": 6,
    "pricePer": 11.00,
    "datePurchased": "2025-12-09",
    "totalSpent": 66.00,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 66.00
  }'

  curl -X DELETE "http://localhost:8080/api/expenses/1?userEmail=test@example.com"



# TEST VALIDATION
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "category": "Food",
    "purposeItem": "Groceries"
  }'
{"fieldErrors":{"quantity":"Quantity is required","totalSpent":"Total spent is required","datePurchased":"Purchase date is required","loggedBy":"Logged by email is required","pricePer":"Price per unit is required","currencySpent":"Currency code is required"},"error":"Validation Failed","message":"Invalid input data","timestamp":"2025-12-10T08:21:44.654189","status":400}%  

 curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "invalid-email",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 52.50,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 52.50
  }'
{"fieldErrors":{"loggedBy":"Invalid email format"},"error":"Validation Failed","message":"Invalid input data","timestamp":"2025-12-10T08:22:40.556965","status":400}%  

 curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 0,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 52.50,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 52.50
  }'
{"fieldErrors":{"quantity":"Quantity must be at least 1"},"error":"Validation Failed","message":"Invalid input data","timestamp":"2025-12-10T08:22:48.716373","status":400}% 

 curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 52.50,
    "currencySpent": "XYZ",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 52.50
  }'
{"error":"Resource Not Found","message":"Validation failed: Currency 'XYZ' is not supported. Supported: PHP, USD, CAD, EUR, GBP, JPY, AUD","timestamp":"2025-12-10T08:22:56.662289","status":404}%             

 curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2026-12-31",
    "totalSpent": 52.50,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 52.50
  }'
{"fieldErrors":{"datePurchased":"Purchase date cannot be in the future"},"error":"Validation Failed","message":"Invalid input data","timestamp":"2025-12-10T08:23:17.046861","status":400}%    

 curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 100.00,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 100.00
  }'
{"error":"Resource Not Found","message":"Validation failed: Total spent (100.00) mismatch: expected 52.50","timestamp":"2025-12-10T08:23:29.580094","status":404}%    

## Fixed globalexceptionhandler to properly handle err 400 instead of wrong 404 status
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "loggedBy": "test@example.com",
    "category": "Food",
    "purposeItem": "Groceries",
    "quantity": 5,
    "pricePer": 10.50,
    "datePurchased": "2025-12-09",
    "totalSpent": 100.00,
    "currencySpent": "PHP",
    "exchangeRate": 1.0,
    "baseCurrencyAmount": 100.00
  }'
{"error":"Business Validation Failed","message":"Validation failed: Total spent (100.00) mismatch: expected 52.50","timestamp":"2025-12-10T08:27:21.330524","status":400}%    

