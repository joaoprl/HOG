
for FILE in input/* ; do echo ${FILE##*/}; java Main ${FILE##*/} >> output/output.dat; done
