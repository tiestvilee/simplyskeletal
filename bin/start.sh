#!/bin/sh
set -e

readonly BASE_DIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )"/.. && pwd)"

$BASE_DIR/bin/build.sh
buck run //proto:proto-app