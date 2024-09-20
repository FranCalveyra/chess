FROM openjdk:21-jdk-slim

# Update package list and install necessary packages
RUN apt-get update && apt-get -y install \
    xorg \
    gtk2-engines \
    libasound2 \
    libgtk2.0-0 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxi6 \
    libxtst6 \
    libxrandr2 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app
# Set the JAVA_HOME environment variable
ENV JAVA_HOME /usr/local/openjdk-21

# Set the JPRO_HOME environment variable
ENV JPRO_HOME /app/jproserver

# Set the JPRO_PORT environment variable
ENV JPRO_PORT 8080

EXPOSE 8080


# Copy the project files into the container
COPY ./ /app

# Ensure the restart script is executable and exists


# Run the restart script
CMD ["bin/restart.sh"]
#CMD ["ls"]