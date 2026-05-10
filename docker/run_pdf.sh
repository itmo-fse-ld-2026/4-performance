docker run --rm \
  --user "$(id -u):0" \
  -v .:/data \
  ghcr.io/itmo-fse-ld-2026/3-xetex-compiler:$1 \
  make pdf