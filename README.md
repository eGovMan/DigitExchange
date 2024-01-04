
1. Generate a secret key (e.g. openssl rand -base64 32 )
2. Set it as environment variable export JWT_SECRET_KEY=your_secret_key

To create a program, estimate and sanction for testing use
curl -i  --location 'http://127.0.0.1:8080/line/program' \
--header 'Content-Type: application/json' \
--data-raw '{
    "function":"Livelihood", "administration":"HUDD", "location":"Odisha","program":"mukta","recipient_segment":"community","economic_segment":"labour","source_of_fund":"state","target":"women","amount":1000000,"name":"Mukta", "type":"program"
}'


To retrieve message using Inbox API
curl -X POST "http://127.0.0.1:8080/exchange/v1/inbox" \
     -H "Content-Type: application/json" \
     -d '{
    "receiver_id": "line@http://127.0.0.1:8080",
    "page": 0,
    "size": 10
}'