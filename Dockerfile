FROM eclipse-temurin:21-jre-jammy

# Install X11/GTK dependencies required by JavaFX (headless rendering via Monocle)
RUN apt-get update && apt-get -y install --no-install-recommends \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libxcursor1 \
    libxcomposite1 \
    libxdamage1 \
    libxfixes3 \
    libxcb1 \
    libgtk2.0-0 \
    libasound2 \
    libnss3 \
    fontconfig \
    fonts-dejavu-core && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

ENV JAVA_HOME=/opt/java/openjdk
ENV JPRO_HOME=/app/jproserver
ENV JPRO_PORT=8080

EXPOSE 8080

COPY ./engine-jpro/ /app

RUN chmod +x bin/*.sh

CMD ["bin/restart.sh"]
