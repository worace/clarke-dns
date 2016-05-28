FROM clojure
MAINTAINER "Horace Williams"
ENV PORT 80
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY project.clj /usr/src/app/
RUN lein deps
COPY . /usr/src/app
RUN lein uberjar
RUN mv /usr/src/app/target/server.jar .
# RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar
EXPOSE 80
CMD ["java", "-jar", "server.jar"]
