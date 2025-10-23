# Stage 1: Build the native application
FROM ghcr.io/graalvm/native-image-community:22-muslib AS builder

# Install a recent version of Maven manually
ARG MAVEN_VERSION=3.9.6
ARG BASE_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries
RUN microdnf install -y curl tar gzip && \
    curl -fsSL -o /tmp/maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf /tmp/maven.tar.gz -C /opt && \
    rm /tmp/maven.tar.gz
ENV PATH="/opt/apache-maven-${MAVEN_VERSION}/bin:${PATH}"

# Copy the application source
WORKDIR /app
COPY . .

# Build the native executable
RUN mvn -Pnative -DskipTests clean package

# Stage 2: Create the final image
FROM gcr.io/distroless/base-debian11
WORKDIR /app
COPY --from=builder /app/target/workflow-processor .
EXPOSE 33504
ENTRYPOINT ["./workflow-processor"]
