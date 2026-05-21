docker run --rm \
  --user "$(id -u):0" \
  -v .:/data \
  ghcr.io/itmo-fse-ld-2026/4-xetex-compiler:$1 \
  make pdf