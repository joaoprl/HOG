
javac Main.java

for i in {0..4}
do
    echo $i
    java Main $1 >> timer.dat
done
