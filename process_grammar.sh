echo "deleting ANTLR parser .java and .class files"
rm -f ./src/main/scala/org/apache/spark/sql/catalyst/parser/*.class
rm -f ./src/main/scala/org/apache/spark/sql/catalyst/parser/*.java
echo "generating parser files from SqlBase.g4"
java -jar ./ANTLR/antlr-4.5.3-complete.jar -visitor ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBase.g4
echo "adding package org.apache.spark.sql.catalyst.parser; at the beginning of parser generated files"
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseBaseListener.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseBaseListener.java
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseBaseVisitor.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseBaseVisitor.java
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseLexer.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseLexer.java
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseListener.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseListener.java
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseParser.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseParser.java
echo 'package org.apache.spark.sql.catalyst.parser;' | cat - ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseVisitor.java > temp && mv temp ./src/main/scala/org/apache/spark/sql/catalyst/parser/SqlBaseVisitor.java
echo "compiling generated parser java files"
javac ./src/main/scala/org/apache/spark/sql/catalyst/parser/*.java
