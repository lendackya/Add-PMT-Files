#!/bin/bash
cd ccdb
source environment.csh
cd ..

ccdb add /test/rich/mapmts -v default data/pmt_ccdb.txt
