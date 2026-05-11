AUDIO_GID=$(getent group audio | cut -d: -f3)

docker run --rm \
  --user "$(id -u):0" \
  --group-add "$AUDIO_GID" \
  -v .:/project \
  -v .ant:/.ant \
  -v ../remote:/project/sandbox/remote \
  -v ~/.ssh/helios_ant_key:/ssh/.ssh/key \
  --device /dev/snd \
  ghcr.io/itmo-fse-ld-2026/3-ant-environment:$1 \
  ant $2