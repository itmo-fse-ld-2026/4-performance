docker run --rm \
  --user "$(id -u):0" \
  -v .:/project \
  -v .ant:/.ant \
  -v ../remote:/project/sandbox/remote \
  -v ~/.ssh/helios_ant_key:/root/.ssh/key \
  --device /dev/snd \
  ghcr.io/itmo-fse-ld-2026/3-ant-environment:dev \
  ant $1