1. Töltsétek le innen: http://archive.apache.org/dist/maven/maven-3/3.1.1/binaries/

2. Itt vannak a futtathatók: apache-maven-3.1.1\bin; Windows esetén ezt tegyétek a PATH-ba.

3. Az apache-maven-3.1.1\conf-ba lévő settings.xml-t Windows esetén a \Users\xyz\.m2\ mappába tegyétek bele.

4. Menjetek a repo-ban lévő projekt mappába és futtassatok egy 'mvn clean package'-t. Ha success, akkor minden jó, ha nem, szóljatok. A target mappába ment a jar.