javadoc -d docs ./src/main/java/ru/nsu/romanov/heapsort/Heapsort.java
mkdir -p build
javac ./src/main/java/ru/nsu/romanov/heapsort/Heapsort.java -d build
cd build
java ru.nsu.romanov.heapsort.Heapsort
echo "completed successful"
