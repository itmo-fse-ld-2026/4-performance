#!/bin/bash
docker run --rm \
  --user "$(id -u):0" \
  -v .:/project \
  -v .ant:/.ant \
  -v ../remote:/project/sandbox/remote \
  ghcr.io/itmo-fse-ld-2026/4-ant-environment:$1 \
  ant $2