# Getting Started

# DOCKER(local)

1. mvn package
2. docker build -t app_name ./
3. docker run -p 5000:5000 -e PORT=5000 app_name

docker run have to use '-p' to publish ports. Without it application will be isolated from a world.

I don't know how publish argument gets along with PORT env. It works when PORT and first part of publish has the same value, or when there is no PORT defined


# HEROKU(global)

1. mvn package
2. heroku container:push web
3. heroku container:release web

do this inside Dockerfile (by this you connect environment PORT to tomcat port)
```
CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /app.jar" ]
```