#!/bin/bash
# pls run from /src: ./test/run_all_test.sh
outd="$(dirname ${0})/out/$(date +%Y-%m-%d_%H-%M-%S)"
mkdir -p "${outd}"
javac *.java
for f in $(cat "$(dirname ${0})/test_names.txt")
do
	echo "RUN TEST: ${f}"
	java Proto < "$(dirname ${0})/in/${f}.in.txt" > "${outd}/${f}.out.txt"
done
