#!/bin/sh

counter=1
for i in *.jpg; do
	mv $i "obama$counter.jpg"
	let counter=counter+1
done
