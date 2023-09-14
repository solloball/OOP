javadoc -d docs ./src/main/java/ru/nsu/romanov/heapsort/Sample.java
mkdir -p build
javac ./src/main/java/ru/nsu/romanov/heapsort/Sample.java -d build
cd build
java ru.nsu.romanov.heapsort.Sample
echo "completed successful"
