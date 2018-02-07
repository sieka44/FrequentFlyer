```
  ______                               _     ______ _
 |  ____|                             | |   |  ____| |
 | |__ _ __ ___  __ _ _   _  ___ _ __ | |_  | |__  | |_   _  ___ _ __
 |  __| '__/ _ \/ _` | | | |/ _ \ '_ \| __| |  __| | | | | |/ _ \ '__|
 | |  | | |  __/ (_| | |_| |  __/ | | | |_  | |    | | |_| |  __/ |
 |_|  |_|  \___|\__, |\__,_|\___|_| |_|\__| |_|    |_|\__, |\___|_|
                   | |                                 __/ |
                   |_|                                |___/
```

##Introduction
Web application for frequent flyers.
For more information see [documentation](https://github.com/academy-poland/sabre_Zax37_sieka44/docs/index.html) 

Authors: Kamil Sięka, Zachariasz Zacharski. 
 
##Setup
Step 1: To get started you need to:
* [install JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Install IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Install Git](https://git-scm.com/)
* [Download Gradle](https://gradle.org/)(or skip that and use the remote one)

Step 2: Run cmd and enter command:
```
git clone https://github.com/academy-poland/sabre_Zax37_sieka44.git
```
Step 3: Run IntelliJ IDEA, click "Import Project" and select recently downloaded project.

Step 4: Choose "Import project from external model" then set Gradle's location, choose JVM 8.0 and click "Finish".

##Run
1. Execute Gradle ```clean bootRun``` task.
2. Go on [FrequentFlyer](http://localhost:8080/) website.

##Used Technologies
* Frontend?
* [Gradle](https://gradle.org/)
* [Spring](https://projects.spring.io/spring-boot/)
* [Lombok](https://projectlombok.org/)
* [Unirest](http://unirest.io/java.html)

####Testing
* [JUnit4](https://junit.org/junit4/)
* [Selenium](http://www.seleniumhq.org/)