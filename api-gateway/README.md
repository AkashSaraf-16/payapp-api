for ($i=1; $i -le 50; $i++) {
$code = curl.exe -s -o $null -w "%{http_code}" `
  --location "http://localhost:8080/api/transaction/create" `
--header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInVzZXJJZCI6MSwic3ViIjoiYWthc2hAZ21haWwuY29tIiwiaWF0IjoxNzcwNDUyODY3LCJleHAiOjE3NzA1MzkyNjd9.YJYXbsJz7MTo40QxtqfQXOdvPiaJRktHVMQVRZCu9d4" `
  --header "Content-Type: application/json" `
--data "{ `"senderId`":3, `"receiverId`":2, `"amount`":5000.0 }"

Write-Host "Request $i -> $code"
}

use this in powershell to check if rate limiting is working fine or not.

To start redis:
1. docker run -d -p 6379:6379 --name redis redis:alpine (for first time) or docker start redis (else)
2. docker exec -it redis redis-cli ping
3. Start the gateway application 


To see the h2 daba open http://localhost:{application_port}/h2-console/
and also do : requestMatchers("/h2-console/**").permitAll() in SecucrityConfig of that app.