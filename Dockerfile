FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

# Installiere die benötigten Abhängigkeiten
RUN apt-get update && apt-get install -y \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    libfontconfig1 \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Setze das Arbeitsverzeichnis
WORKDIR /othello

ENV DISPLAY=host.docker.internal:0.0

# Kopiere das Projekt in den Container
COPY . /othello

# Starte die Anwendung mit sbt run
CMD ["sbt", "run"]
