#!/bin/tcsh

source /group/clas12/environment.csh

setenv CCDB_CONNECTION mysql://clas12writer:geom3try@clasdb.jlab.org/clas12
setenv CCDB_USER lendacka

clas12-ccdb -i

