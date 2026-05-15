#!/bin/bash
docker run --rm \
  --platform linux/amd64 \
  -v $(pwd):/project \
  -w /project \
  -v $(pwd)/.ant:/.ant \
  -v $HOME/.ssh:/ssh \
  ghcr.io/itmo-fse-ld-2026/3-ant-environment:$1 \
  ant -lib /ant/lib $2