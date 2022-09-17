FROM eclipse-temurin:17-alpine

RUN apk add entr

WORKDIR /app
ADD build/jar/BOOT-INF/lib /app/lib
ADD build/jar/META-INF /app/META-INF
ADD build/jar/BOOT-INF/classes /app

ENTRYPOINT java -cp .:./lib/* com.github.roni1993.transportservice.TransportServiceApplicationKt