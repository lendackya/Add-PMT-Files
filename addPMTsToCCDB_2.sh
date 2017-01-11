#!/bin/bash

source /group/clas12/environment.csh

export CCDB_CONNECTION=mysql://clas12writer:geom3try@clasdb.jlab.org/clas12
export CCDB_USER=lendacka

clas12-ccdb -i

