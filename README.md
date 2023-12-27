To create a program, estimate and sanction for testing use
curl -i  --location 'http://127.0.0.1:8080/line/program/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "function":"Livelihood", "administration":"HUDD", "location":"Odisha","program":"mukta","recipient_segment":"community","economic_segment":"labour","source_of_fund":"state","target":"women","amount":1000000,"name":"Mukta", "type":"program"}
}'


To retrieve message using Inbox API
curl -X POST "http://127.0.0.1:8080/exchange/v1/inbox" \
     -H "Content-Type: application/json" \
     -d '{"signature":"","header":{"receiver_id":"finance@http://127.0.0.1:8080","version":"1.0.0","message_id":"123","message_ts":"2011-12-03T10:15:30+01:00[Europe/Paris]","action":"create","sender_id":"finance@127.0.0.1:8080"}, "message":"{\"page\": 0,\"size\": 10}"}'