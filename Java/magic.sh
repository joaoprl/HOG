
for THREADS in 2 4 8 16 32; do for FILE in input/* ; do java Main ${FILE##*/} $THREADS ; done; done
