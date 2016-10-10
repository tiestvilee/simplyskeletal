#!/bin/sh
set -e

bin/sm.sh lib lib
buck test //proto:proto-app