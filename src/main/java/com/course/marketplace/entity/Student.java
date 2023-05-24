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
@ToString
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id",nullable = false)
    private Long studentId;
    @NonNull
    @Basic()
    @Column(name="f_name",nullable = false,length=30)
    private String fName;
    @NonNull
    @Basic()
    @Column(name="l_name",nullable = false,length=30)
    private String lName;
    @NonNull
    @Basic()
    @Column(name="level",nullable = false,length=30)
    private String level;

    //every student can enrol in more than one course
    @ManyToMany(mappedBy = "students",fetch = FetchType.LAZY)
    private Set<Course> courses=new HashSet<>();

    @NonNull
    @OneToOne(cascade = CascadeType.REMOVE,fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id",nullable = false)
    private UserDetails userDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId.equals(student.studentId) && fName.equals(student.fName) && lName.equals(student.lName) && Objects.equals(level, student.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, fName, lName, level);
    }
}
