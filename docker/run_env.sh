PROJECT_PATH=$(pwd)
DOCKER_GID=$(getent group docker | cut -d: -f3)

docker run --rm \
  -v "$PROJECT_PATH:$PROJECT_PATH" \
  -w "$PROJECT_PATH" \
  --user "$(id -u):$(id -g)" \
  --group-add "$DOCKER_GID" \
  -v "$PROJECT_PATH/.ant:/.ant" \
  -v /var/run/docker.sock:/var/run/docker.sock \
  ghcr.io/itmo-fse-ld-2026/3-ant-environment:$1 \
  ant env