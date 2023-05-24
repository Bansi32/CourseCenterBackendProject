package com.course.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
//constructors with attributes that are necessary
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id",nullable = false)
    private Long courseId;
    //lazy loading data
    @NonNull
    @Basic
    @Column(name="course_name",nullable = false,length=50)
    private String courseName;
    @NonNull
    @Basic
    @Column(name="course_duration",nullable = false,length=50)
    private String courseDuration;
@NonNull
    @Basic
    @Column(name="course_description",nullable = false,length=70)
    private String courseDescription;

    //image string
    @Basic
    @Column(name="course_thumbnail")
    private String courseThumbnail;

    //every course can be taught by a single tutor
    @NonNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tutor_id",referencedColumnName = "tutor_id")
    private Tutor tutor;

    //every course will have group of students
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="enrolled_in",
    joinColumns = {@JoinColumn(name="course_id")},
            inverseJoinColumns ={@JoinColumn(name="student_id")} )
    private Set<Student> students=new HashSet<>();

    public void assignStudentToCourse(Student student)
    {
        this.students.add(student);
        student.getCourses().add(this);

    }
    public void removeStudentFromCourse(Student student)
    {
        this.students.remove(student);
        student.getCourses().remove(this);
    }

    //to compare course class instance and other class instance
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId.equals(course.courseId) && courseName.equals(course.courseName) && courseDuration.equals(course.courseDuration) && courseDescription.equals(course.courseDescription) && courseThumbnail.equals(course.courseThumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseDuration, courseDescription, courseThumbnail);
    }
}
