docker run --rm \
  --user "$(id -u):0" \
  -v .:/project \
  ghcr.io/itmo-fse-ld-2026/3-ant-environment:dev \
  make sandbox