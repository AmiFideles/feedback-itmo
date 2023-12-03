#!/bin/bash

# Определяем URL для авторизации и получения токена доступа
auth_url="http://localhost:8080/api/auth/login"
# Определяем URL для отправки запросов на эндпоинт
color_url="http://localhost:8080/api/color"

# Определяем учетные данные для авторизации
username="string"
password="string"

# Отправляем POST-запрос на авторизацию и получаем токен доступа
response=$(curl -s -X POST -H "Content-Type: application/json" -d "{\"username\":\"$username\",\"password\":\"$password\"}" "$auth_url")
access_token=$(echo "$response" | jq -r '.accessToken')

# Проверяем, получен ли токен доступа
if [ "$access_token" != "null" ]; then
    # Определяем массив цветов
    colors=(
        '{"color": "#D3FF5C", "light": "#ECFFB7"}'
        '{"color": "#A1CEF8", "light": "#D2F6FF"}'
        '{"color": "#FFAAE6", "light": "#FFE7F8"}'
        '{"color": "#DCA4FF", "light": "#EED8FF"}'
    )

    # Отправляем POST-запросы на эндпоинт с использованием токена доступа
    for color in "${colors[@]}"; do
        curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $access_token" -d "$color" "$color_url"
    done
else
    echo "Failed to obtain access token."
fi
