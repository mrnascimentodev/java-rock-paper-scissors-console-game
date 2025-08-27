# Base oficial com Java 21
FROM mcr.microsoft.com/devcontainers/java:21

# Instala Maven, curl e Git LFS
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get install -y maven && \
    curl -s https://packagecloud.io/install/repositories/github/git-lfs/script.deb.sh | bash && \
    apt-get install -y git-lfs && \
    rm -rf /var/lib/apt/lists/*

# Garante que git-lfs está no path
RUN git lfs install --system
