javadoc -d docs ./src/main/java/ru/nsu/romanov/tree/Tree.java
mkdir -p build
javac ./src/main/java/ru/nsu/romanov/tree/Tree.java -d build
cd build
java ru.nsu.romanov.tree.Tree
echo "completed successful"

