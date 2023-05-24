package com.course.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@ToString
@Table(name="tutors")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tutor_id",nullable = false)
    private Long tutorId;
    @NonNull
    @Basic
    @Column(name="f_name",nullable = false,length=30)
    private String fName;
    @NonNull
    @Basic
    @Column(name="l_name",nullable = false,length=30)
    private String lName;
    @NonNull
    @Basic
    @Column(name="is_active",nullable = false)
    private boolean isActive;
    @NonNull
    @Basic
    @Column(name="summary",nullable = false,length=70)
    private String summary;

    @ToString.Exclude
    //every tutor can teach several courses
    @OneToMany(mappedBy = "tutor",fetch = FetchType.LAZY)
    private Set<Course> courses=new HashSet<>();

    @ToString.Exclude
    @NonNull
    @OneToOne(cascade = CascadeType.REMOVE,fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id",nullable = false)
    private UserDetails userDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return isActive == tutor.isActive && tutorId.equals(tutor.tutorId) && fName.equals(tutor.fName) && lName.equals(tutor.lName) && Objects.equals(summary, tutor.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorId, fName, lName, isActive, summary);
    }


}
