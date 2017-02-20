#!/bin/bash

pwd

tablepath="/test/rich/aerogel"

echo "$tablepath"
ccdb mktbl $tablepath -r 3 "name"=string "topology"=string "average density(g/cm3)"=double "average ref. index"=double "side 1-2(mm)"=double "side 2-3(mm)"=double "side 3-4(mm)"=double "side 4-1(mm)"=double "thickness 1(mm)"=double "thickness 2(mm)"=double "thickness 3"=double "thickness 4"=double "tranparency coefficient"=double "scattering length(mm)"=double "planarity"=double "comments=string" 

