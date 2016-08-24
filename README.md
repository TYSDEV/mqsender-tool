# MQ Sender
JMS message sending tool (ActiveMQ / WebSphere MQ)

_Version: 2.0_

### Requirements
* JavaFX _(or JDK 1.8 and later)_

### Build
Change path to JDK in pom.xml:

    <jdk.home>/usr/lib/jvm/jdk1.7.0_51</jdk.home>
   
Assembly with dependencies:

    mvn -B clean release:prepare -P production -DreleaseVersion=<new-release-version>
