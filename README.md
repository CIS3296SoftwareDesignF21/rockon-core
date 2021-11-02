# Project

See [here](https://github.com/CIS3296SoftwareDesignF21/rockon-mobile) for the client application

## About
RockOn can be thought of as a ”tinder for rock climbers”. This Android application will
allow rock climbers of varying levels of experience and climbing preferences to find climbing partners in their
geographical region (≤ 25 miles), by displaying a queue of profiles. Users will be given the option to connect
with others if they find their experience and preferences to be a match. Connected users will then be able
to use the app to communicate with matched users.

## Technical Proposal
The application will rely on a REST API, built in Java using SpringBoot. The API will allow for CRUD
user functionality, as well as managing the profile queue and connections between users. This will rely on
MariaDB for storage. User security will be managed with OAuth2.

The user will interact with a mobile application that communicates with the REST API. The frontend is a ReactNative client and
will allow users to register, create and update a profile, swipe through potential climbing partners, and view the contact card 
of matched partners.

## Vision Statement
For rock climbers who want to climb with others of a similar skill and ability, RockOn is a mobile app for iOS or Android that lets you create and sort through profiles of other climbers to finds those who are most compatible as climbing partners.

Unlike searching online in local Facebook groups or asking around at your local gym, the focus of RockOn if efficiency and breadth. With RockOn you can filter through many descriptive profiles of different climbers and get in contact with them right away.

## Personas
### Ronnie the Gym Owner
Ronnie, age 43, is a gym owner in Chicago, Illinois. His location has weights and gym machines, but also a portion of the building dedicated to bouldering (indoor rock climbing without a rope). Patrons to his gym generally use the weight training equipment, but the climbing wall only on occasion. Ronnie wants to make his gym known to local climbers in Chicago and anyone looking to get into the hobby.

Ronnie knows how to use basic technology like payroll and timecard management software. He is primarily interested in creating a Location Profile in RockOn so that climbers can list his gym as their “main gym” in their profiles, and new users to the app see his gym as a potential place to climb indoors.


# How to Run

1. Clone the repository
2. Install [Maven](https://maven.apache.org/install.html)
3. Install the dependancies specified in `pom.xml` by running `mvn install`
4. Run the SpringBoot application by running `mvn spring-boot:run`

# How to Install


