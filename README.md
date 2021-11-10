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

### Ingrid the Office Worker

Ingrid, age 46, is your normal day-to-day office worker. One very interesting thing is that she is very into rock climbing
and she has mostly done it solo since her significant other is not into rock climbing. Throughout, her time rock climbing Ingrid has managed
to go solo. However, due to recent events and of course because of her age, Ingrid is looking to find partners at her skill level or
even new rock climbers to teach. She is very open to different types of people and likes to rock climb either in indoor gyms or outdoors.

Ingrid is well versed with technology. She is great with social media apps such as Facebook and Instagram. Aside from those two a new app
like RockOn might be somewhat interesting for her because it would be something different from simply looking through Facebook groups.
Ingrid is interested in using RockOn because it would allow her to join the rock climbing community that she had missed out on while she was climbing solo.

### Filip the Avid Rock Climber

Filip, age 25, is a graduate student who loves rock climbing. He climbs as many times as he can a week. He is very passionate about climbing and is looking for like-minded people. He is specifically looking for people around his skill level who will motivate him to do better in his competitions. Since Filip is reaching the end of his graduate school career, Filip has decided to focus more on school but still putting time into rock climbing. He is also putting into consideration that he would be looking for a partner who would be okay with him having to cancel last minute.
Filip is a graduate student who understands various technologies well and is proficient at learning how to use new apps fairly quickly. He is very interested in finding people who have a similar passion to rock climbing as he does and thinks RockOn could potentially find people who are willing to understand that he is a busy student but also is passionate about rock climbing. He will be more than likely be very picky about his partner.

### Tyler, New Rock Climber

Tyler, age 21, is an adrenaline junkie who is just trying to find his next fix. So far he has attempted skydiving, scuba diving, surfing, and riding rollercoasters. He is ready to try out rock climbing. However, he has no idea how to start nor does he know anyone interested in rock climbing. He lives in the Shore area of New Jersey so not a lot of natural rock climbing locations. It is also difficult to find rock climbing gyms around his area but he is more than willing to travel to be able to try something new.

Although Tyler is young, he is like an old person when it comes to technology. He is behind with technology and sometimes needs help with printing out documents. Although he is a complete novice when it comes to technology, he is still able to use social media apps with ease because of their simplistic user interface. He is very well aware that learning using a new app will take a bit to get used to but he does want to find a way of finding a way to get into rock climbing and hopefully not by himself.

# How to Run


# How to Install
1. Clone this repository
2. Install [Maven](https://maven.apache.org/install.html)
3. Install the dependancies specified in `pom.xml` by running `mvn install`
4. Run the SpringBoot application by running `mvn spring-boot:run` (note: Java 11 is required)

# Week 2 Readme Link

https://github.com/CIS3296SoftwareDesignF21/rockon-mobile/blob/master/Week2Readm.md
