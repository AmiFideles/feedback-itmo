#!/bin/bash

API_ENDPOINT="http://localhost:8080/api/feedback/add"

for i in {1..1000}; do
  FIRST_NAME="FirstName$i"
  LAST_NAME="LastName$i"
  GRADUATION_YEAR=$((1990 + RANDOM % 10))
  FACULTY="Faculty$i"
  MENTOR_NAME="MentorName$i"
  MESSAGE_TEXT="MessageText$i"
  MENTOR_EMAIL="email$i@example.com"
  COLOR_ID=$((1 + RANDOM % 4))  # Генерация случайного значения colorId от 1 до 4

  # Создание JSON-строки с данными, включая значение colorId
  DATA="{\"firstName\":\"$FIRST_NAME\",\"lastName\":\"$LAST_NAME\",\"graduationYear\":$GRADUATION_YEAR,\"faculty\":\"$FACULTY\",\"mentorName\":\"$MENTOR_NAME\",\"messageText\":\"$MESSAGE_TEXT\",\"mentorEmail\":\"$MENTOR_EMAIL\",\"color\":$COLOR_ID}"

  # Отправка запроса с использованием cURL
  curl -s -X POST -H "Content-Type: application/json" -d "$DATA" "$API_ENDPOINT"
done
