javadoc -d docs ./src/main/java/ru/nsu/romanov/polynomial/Main.java
mkdir -p build
javac ./src/main/java/ru/nsu/romanov/polynomial/Main.java -d build
cd build
java ru.nsu.romanov.polynomial.Main
echo "completed successful"
