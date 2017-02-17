#!/bin/bash

pwd
cd data

for filename in *.txt; do

	name=$(echo $filename | cut -f 1 -d '.')
	echo "$filename"
	echo "$name"

	tablepath="/test/rich/exp/"$name
	echo "$tablepath"	
	ccdb mktbl $tablepath -r 768 ver=double pmt=double ntube=double runset=double rpos=double runt=double hv=double cls=double als=double adc1000=double adc1100=double cbsi=double gain=double pix=double yeild=double chi2=double nu2=double nu3=double a1=double a3=double nuav=double sc=double ssc=double sigma=double ssigma=double mu=double smu=double nu1=double snu1=double a2=double sa2=double pnu2=double spnu2=double pa3=double sa3=double pnu3=double snu3=double xi=double sxi=double beta=double sbeta=double tau=double stau=double eff20=double eff25=double
  	ccdb add $tablepath -v default $filename
	
done

