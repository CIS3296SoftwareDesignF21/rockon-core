package com.cis.rockon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import com.cis.rockon.util.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString
@RequiredArgsConstructor
@Getter @Setter
@Accessors(chain = true)
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date birthday;

    @Embedded
    private Location lastSeenLocation;

    @Column(columnDefinition = "integer default 25")
    private Integer searchRadius;

    @NotNull
    @Column(nullable = false)
    @Length(min=100, max=500)
    private String biography;

    @NotNull
    @Column(nullable = false)
    @Range(min=0, max=99)
    private Integer yearsOfExperience;

    /* types of climbing, stored individually to make filtering easier */
    private Boolean typeSportClimbing;
    private Boolean typeTradClimbing;
    private Boolean typeTopRope;
    private Boolean typeFreeSolo;
    private Boolean typeBouldering;

    /* set of users that this user has swiped on */
    @JsonBackReference(value="swipes")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<User> swipes = new HashSet<>();

    /* set of users that this user has matched with */
    @JsonBackReference(value="connections")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<User> connections = new HashSet<>();

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
