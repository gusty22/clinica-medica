#!/usr/bin/env bash
host="$1"
shift
port="$1"
shift

cmd="$@"

until nc -z "$host" "$port"; do
  echo "Aguardando $host:$port..."
  sleep 2
done

echo "$host:$port está pronto. Iniciando o serviço..."
exec $cmd
