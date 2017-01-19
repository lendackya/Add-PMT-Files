#!/bin/bash
cd ccdb
source environment.csh
cd ..

  filename in *.txt; do

  ccdb mktbl /test/rich/$filename -r 768 ver=double pmt=double  val=double
  ccdb add /test/rich/mapmts -v default $filename
done
