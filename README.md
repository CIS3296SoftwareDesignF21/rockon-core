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

## Personas



# How to Run

1. Clone the repository
2. Install [Maven](https://maven.apache.org/install.html)
3. Install the dependancies specified in `pom.xml` by running `mvn install`
4. Run the SpringBoot application by running `mvn spring-boot:run`

# How to Install


