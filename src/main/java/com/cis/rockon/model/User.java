package com.cis.rockon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Node
@ToString
@RequiredArgsConstructor
@Getter @Setter
@Accessors(chain = true)
public class User {

    /*
    CREATE CONSTRAINT fname_exists IF NOT EXISTS ON (u:User) ASSERT u.firstName IS NOT NULL;
    CREATE CONSTRAINT lname_exists IF NOT EXISTS ON (u:User) ASSERT u.lastName IS NOT NULL;
    CREATE CONSTRAINT phone_exists IF NOT EXISTS ON (u:User) ASSERT u.phoneNumber IS NOT NULL;
    CREATE CONSTRAINT email_exists IF NOT EXISTS ON (u:User) ASSERT u.email IS NOT NULL;
    CREATE CONSTRAINT birthday_exists IF NOT EXISTS ON (u:User) ASSERT u.birthday IS NOT NULL;
    CREATE CONSTRAINT location_exists IF NOT EXISTS ON (u:User) ASSERT u.lastSeenLocation IS NOT NULL;
    CREATE CONSTRAINT search_exists IF NOT EXISTS ON (u:User) ASSERT u.searchRadius IS NOT NULL;
    CREATE CONSTRAINT bio_exists IF NOT EXISTS ON (u:User) ASSERT u.biography IS NOT NULL;
    CREATE CONSTRAINT exp_exists IF NOT EXISTS ON (u:User) ASSERT u.yearsOfExperience IS NOT NULL;
    */

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Email
    private String email;

    private LocalDate birthday;

    private double[] lastSeenLocation = new double[2]; // {lat, long}

    @Range(min=1, max=25)
    private Integer searchRadius = 25;

    @Length(max=500)
    private String biography;

    @Range(min=0, max=99)
    private Integer yearsOfExperience;

    /* types of climbing, stored individually to make filtering easier */
    private Boolean typeSportClimbing;
    private Boolean typeTradClimbing;
    private Boolean typeTopRope;
    private Boolean typeFreeSolo;
    private Boolean typeBouldering;

    // technically a bidirectional relationship, but we specify this at query time
    // user.connections.add(other) if user swipes on other
    @Relationship(type = "CONNECTIONS", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    @JsonIgnore
    List<User> connections = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private double distance(User other) {
        double p = Math.PI/180;
        double a = 0.5 - Math.cos(p * (lastSeenLocation[0] - other.lastSeenLocation[0])) * 0.5 +
                Math.cos(p * lastSeenLocation[0]) + Math.cos(p * other.lastSeenLocation[0]) +
                (1 - Math.cos(p * (lastSeenLocation[1] - other.lastSeenLocation[1]))) * 0.5;

        return Math.asin(Math.sqrt(a)) * 12742; // KM
    }

    public boolean preferenceMatch(User other) {

        // if the users have conflicting preferences OR out of location range
        return this.typeSportClimbing == other.typeSportClimbing &&
                this.typeTradClimbing == other.typeTradClimbing &&
                this.typeTopRope == other.typeTopRope &&
                this.typeFreeSolo == other.typeFreeSolo &&
                this.typeBouldering == other.typeBouldering &&
                this.distance(other) <= 25;
    }
}
