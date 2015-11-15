0. Kell hogy legyen egy JAVA_HOME nevű környezeti változótok

1. Töltsétek le innen: http://archive.apache.org/dist/maven/maven-3/3.1.1/binaries/

2. Itt vannak a futtathatók: apache-maven-3.1.1\bin. Ezt tegyétek a PATH-ba.

3. (Csak Windows esetén!) Az apache-maven-3.1.1\conf-ba lévő settings.xml-t a \Users\xyz\.m2\ mappába tegyétek bele. 

4. Menjetek a repo-ban lévő Project mappába és futtassatok egy 'mvn clean package'-t. Ha success, akkor minden jó, ha nem, szóljatok. A target mappába ment a jar.


IDEA integráció:

5. Telepítsétek az IDEA-t: https://www.jetbrains.com/idea/download/

6. Indísátok el, majd 'Create new project / Empty project'-el csináljatok egy új projektet. Az elkészült projektet ne töltsétek fel a repoba.

7. 'File / Project Structure'-ben a 'Project'-en belül állítsátok be az JDK-t, meg a language lvl-t 8-ra.

8. Ugyanitt 'Modules / + / Import module', itt Maven és válasszátok ki a pom.xml-ünket. Majd OK. 
(Nekem linuxon még az utolsó screen-en az 'Environment settings'-ben a maven verzióját át kellett állítanom a letöltött 3.1.1-re. De ilyenre windowson nem emlékszem. Lehet hogy akkor azért találta meg rögtön a letöltöttet, mert volt egy M2_HOME env var-om).

9. Innen már fodíthattok és futtathattok is command line-ból. De egyszerűbb beállítani egy run configuration-t IDEA-ban: 'Run / Edit configurations... / + / Application'. Itt válasszátok ki a main class-t, az argumentum legyen 'http://javachallenge.loxon.hu:8443/engine/CentralControl?wsdl utinni QKNJ4138' és alul a 'Before launch'-nál adjatok hozzá egy 'clean package' maven goal-t.


Bean generálás:

Ha újra kéne generálni a beaneket (mondjuk változik valami a szerveren), akkor így:
zsmester@zsmester-ThinkPad:~/Dropbox/Dev/Java/BankTech/Repo/Project/src/main/java$ wsimport -keep -verbose -Xauthfile ../../../auth http://javachallenge.loxon.hu:8443/engine/CentralControl?wsdl

Légyszi a beanekbe ne írjatok bele!