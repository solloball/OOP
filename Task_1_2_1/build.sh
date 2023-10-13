javadoc -d docs ./src/main/java/ru/nsu/romanov/tree/Tree.java
mkdir -p build
javac -classpath /var/lib/flatpak/app/com.jetbrains.IntelliJ-IDEA-Community/x86_64/stable/a452749bc1fbba62f8ac245888cd85a41c0c13c13e9ecae637fd6c7170bd4fbb/files/IDEA-C/plugins/java/lib/resources/jdkAnnotations.jar ./src/main/java/ru/nsu/romanov/tree/Tree.java -d build
cd build
java ru.nsu.romanov.tree.Tree
echo "completed successful"

