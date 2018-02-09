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

## Introduction
Web application for frequent flyers.
For more information see [documentation](https://github.com/academy-poland/sabre_Zax37_sieka44/tree/Docs/docs) open index.html file in browser.

Authors: Kamil Sięka, Zachariasz Zacharski. 
 
## Setup
Step 1: To get started you need to:
* [Install JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Install IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Install Git](https://git-scm.com/)
* [Download Gradle](https://gradle.org/)(or skip that and use the remote one)

Step 2: Run cmd and enter command:
```
git clone https://github.com/academy-poland/sabre_Zax37_sieka44.git
```
Step 3: Run IntelliJ IDEA, click "Import Project" and select recently downloaded project.

Step 4: Choose "Import project from external model" then set Gradle's location, choose JVM 8.0 and click "Finish".

## Run
1. Execute Gradle ```clean bootRun``` task.
2. Go on [FrequentFlyer](http://localhost:8080/) website.

## HotSwap support
1. Go to Intellij's Settings > Build, Execution, Deployment > Debugger > HotSwap.
2. In "Reload classes after compilation" section select "Always".
3. When project is running simply press Ctrl+F9 (Build > Build Project) to preview changes.

## Used Technologies
### Backend
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Gradle](https://gradle.org/)
* [Spring](https://projects.spring.io/spring-boot/) with [Spring-Loaded 1.2.8](https://github.com/spring-projects/spring-loaded)
* [Lombok](https://projectlombok.org/)
* [Unirest](http://unirest.io/java.html)
### Frontend
* [jQuery 3.1.1](http://jquery.com/) with [jQuery UI v1.12.1](https://jqueryui.com/)
* [jQuery Validation Plugin](https://jqueryvalidation.org/)
* [Materialize 1.0 alpha(v0.100.2)](http://materializecss.com/getting-started.html)
* [Vue.js v2.5.13](https://vuejs.org/v2/guide/installation.html)
* [vue-resource 1.3.5](https://github.com/pagekit/vue-resource)
* SASS with [GradleSassPlugin 1.2.3](https://github.com/kravemir/GradleSassPlugin)
* [Auth0 Lock v11 for Web](https://auth0.com/docs/libraries/lock/v11)
* [Moment.js 2.20.1](https://momentjs.com/)
### Testing
* [JUnit4](https://junit.org/junit4/)
* [Selenium](http://www.seleniumhq.org/)

## Functionality Description
1.Login
 * User can log in on account using Google+ or FrequentFlyer account.
 * If email or password would be wrong, suitable information will be provided.
 * If user does not have account on FrequentFlyer service it can be created, simply by filling up "sign up" form.

2.Homepage provides information like:
 * How many miles user has flown and how many left to next FrequentFlyer level. 
 * Last flights of current log in user.
 
3.Profile
* User can update profile with "Name" and "Address" fields.
* User can reset his password, if such a need occurs.

4.Add Ticket
* User can add ticket to his database by filling form. FrequentFlyer service will automatically update user profile with right amount of miles. 
* Last added ticket will occur in flight history on the homepage and user miles will be updated.

5.Logout
* User can logout by clicking on his name in the upper right corner. Service will automatically redirect user to log in screen.
