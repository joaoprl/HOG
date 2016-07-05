
all:
	javac Main.java
run:
	for FILE in input/* ; do echo ${FILE##*/}; java Main ${FILE##*/} >> output/output.dat; done

clean:
	rm *.class
	rm output/*


