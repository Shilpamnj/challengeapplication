# Account Details 


## This application is used for creating accounts with ID and amount , and transfer amount from one account to other 


### Start application  it runs on 18080, below are the example

To create account :-

curl --location 'http://localhost:18080/challengeApplication/v1/accounts' \
--header 'Content-Type: application/json' \
--data '{
"accountId":"9",
"balance":"50000"
}'

To transfer amount from one account to other


curl --location --request POST 'http://localhost:18080/challengeApplication/v1/accounts/transfer-amount1?fromAccountID=2&toAccountId=3&amount=10000'
