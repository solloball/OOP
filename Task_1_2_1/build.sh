javadoc -d docs ./src/main/java/ru/nsu/romanov/polynomial/Polynomial.java
mkdir -p build
javac ./src/main/java/ru/nsu/romanov/polynomial/Polynomial.java -d build
cd build
java ru.nsu.romanov.polynomial.Polynomial
echo "completed successful"

