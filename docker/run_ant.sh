mkdir -p ./.ant/lib
docker create --name temp_ant ghcr.io/itmo-fse-ld-2026/4-ant-environment:$1
docker cp temp_ant:/ant/lib ./.ant
docker rm temp_ant

docker run --rm \
  --user "$(id -u):0" \
  -v .:/project \
  -v .ant:/.ant \
  ghcr.io/itmo-fse-ld-2026/4-ant-environment:$1 \
  ant $2