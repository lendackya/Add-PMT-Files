#!/bin/bash
cd ccdb
source environment.csh
cd ..

for filename in *.txt; do
  ccdb add /test/rich/mapmts -v default $filename
done
