# RockOn Core

This repository hosts the backend application for RockOn. See [here](https://github.com/CIS3296SoftwareDesignF21/rockon-mobile) for the client application

## About

RockOn can be thought of as a ”tinder for rock climbers”. This cross-platform application will
allow rock climbers of varying levels of experience and climbing preferences to find climbing partners in their
geographical region (≤ 25 miles), by displaying a queue of profiles. Users will be given the option to connect
with others if they find their experience and preferences to be a match. Connected users will then be able
to use the app to communicate with matched users.

## Technical Proposal

The application will rely on a REST API, built in Java using SpringBoot. The API will allow for CRUD
user functionality, as well as managing the profile queue and connections between users. This will rely on
MySQL for storage, and will be hosted on Google App Engine.

The user will interact with a mobile application that communicates with the REST API. The frontend is a Expo with React client and
will allow users to register, create and update a profile, swipe through potential climbing partners, and view the contact card
of matched partners.

## Vision Statement

For rock climbers who want to climb with others of a similar skill and ability, RockOn is a mobile app for iOS or Android that lets you create and sort through profiles of other climbers to finds those who are most compatible as climbing partners.

Unlike searching online in local Facebook groups or asking around at your local gym, the focus of RockOn if efficiency and breadth. With RockOn you can filter through many descriptive profiles of different climbers and get in contact with them right away.

# How to Run

See the [mobile repository](https://github.com/CIS3296SoftwareDesignF21/rockon-mobile) to use the client application!

# How to Install

## Easy way
1. Download the latest release (RockOn.jar from releases)
2. Run `java -jar RockOn.jar` (note: Java 11 is required)
3. Visit localhost:8080/swagger-ui/#/ to see API endpoints and models

## More difficult way
1. Clone this repository
2. Install [Maven](https://maven.apache.org/install.html)
3. Install the dependancies specified in `pom.xml` by running `mvn install`
4. Run the SpringBoot application by running `mvn spring-boot:run` (note: Java 11 is required)
5. Visit localhost:8080/swagger-ui/#/ to see API endpoints and models

# Weekly READMEs
- [Week 1](/Week1.md)
- [Week 2](/Week2.md)
- [Week 3](/Week3.md)


