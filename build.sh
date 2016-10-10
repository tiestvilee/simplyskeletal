#!/bin/sh
set -e

bin/sm.sh lib lib
buck run //proto:proto-app