#!/bin/bash
cd "$(dirname "$0")"
java -cp "app/build/classes/java/main" com.filefilter.Main "$@"
