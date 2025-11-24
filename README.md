# template-message-telegram-bot
A bot that helps send messages in a chosen format to a Telegram chat.

```
docker build --platform linux/amd64 -t dianapure/template-message-telegram-bot:latest .

docker pull dianapure/template-message-telegram-bot
docker run -d -e TELEGRAM_AUTH_TOKEN=<token> dianapure/template-message-telegram-bot

docker ps
docker logs -f <container_id>

docker stop <container_id>
docker start <container_id>
```