package com.cis.rockon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;



@Node
@ToString
@RequiredArgsConstructor
@Getter @Setter
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
//    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
//    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NotNull
//    @Column(nullable = false, updatable = false)
    private LocalDate birthday;

    private Location lastSeenLocation;

//    @Column(columnDefinition = "integer default 25")
    private Integer searchRadius;

    @NotNull
//    @Column(nullable = false)
    @Length(min=100, max=500)
    private String biography;

    @NotNull
//    @Column(nullable = false)
    @Range(min=0, max=99)
    private Integer yearsOfExperience;

    /* types of climbing, stored individually to make filtering easier */
    private Boolean typeSportClimbing;
    private Boolean typeTradClimbing;
    private Boolean typeTopRope;
    private Boolean typeFreeSolo;
    private Boolean typeBouldering;

    @Relationship(type = "SWIPED-ON", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    private List<User> swipedOn = new ArrayList<>();

    @Relationship(type = "SWIPED-ON-BY", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude
    private List<User> swipedOnBy = new ArrayList<>();

    // technically a bidirectional relationship, but we specify this at query time
    @Relationship(type = "CONNECTIONS", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude
    private List<User> connections = new ArrayList<>();

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

    public boolean preferenceMatch(User other) {

        // if the users have conflicting preferences OR out of location range
        return this.typeSportClimbing == other.typeSportClimbing &&
                this.typeTradClimbing == other.typeTradClimbing &&
                this.typeTopRope == other.typeTopRope &&
                this.typeFreeSolo == other.typeFreeSolo &&
                this.typeBouldering == other.typeBouldering &&
                !(this.lastSeenLocation.distance(other.lastSeenLocation) > this.searchRadius);

    }
}
