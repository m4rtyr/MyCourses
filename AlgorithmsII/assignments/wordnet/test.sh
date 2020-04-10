#!/usr/bin/env bash

for i in $(ls project/outcast*)
do
	java-algs4 Outcast synsets.txt hypernyms.txt $i
done
