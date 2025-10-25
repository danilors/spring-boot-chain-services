# Using Oracle GraalVM for JDK 17
FROM container-registry.oracle.com/graalvm/native-image:21-ol8 AS builder

# Install dependencies and set locale
RUN microdnf install -y gzip glibc-langpack-en && microdnf clean all
ENV LANG=en_US.UTF-8

# Set the working directory to /home/app
WORKDIR /build

# Copy the source code into the image for building
COPY . /build

# Build
RUN ./mvnw --no-transfer-progress native:compile -Pnative

# The deployment Image
FROM container-registry.oracle.com/os/oraclelinux:8-slim

EXPOSE 33504

# Copy the native executable into the containers
COPY --from=builder /build/target/workflow-processor app
ENTRYPOINT ["/app"]
