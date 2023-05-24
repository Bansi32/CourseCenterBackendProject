package com.course.marketplace.utility;

import com.course.marketplace.dao.*;
import com.course.marketplace.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

//helper classes for testing functionality

@RequiredArgsConstructor
public class OperationUtility {
    public static void usersOperations(UserDetailsDao userDetailsDao)
    {
        createUsers(userDetailsDao);
        updateUsers(userDetailsDao);
        deleteUser(userDetailsDao);
        fetchUser(userDetailsDao);
    }
    private static void createUsers(UserDetailsDao userDetailsDao) {
        UserDetails user1=new UserDetails("bansi32","bansi@gmail.com","bansi123");
        userDetailsDao.save(user1);
        UserDetails user2=new UserDetails("vraj16","vraj@gmail.com","vraj1234");
        userDetailsDao.save(user2);
    }

    private static void updateUsers(UserDetailsDao userDetailsDao) {
        UserDetails userDetails=userDetailsDao.findById(2L).orElseThrow(()->new EntityNotFoundException(("User not Found")));
        userDetails.setEmail("bansi03@gmail.com");
        userDetailsDao.save(userDetails);
    }
    private static void deleteUser(UserDetailsDao userDetailsDao) {
        UserDetails userDetails=userDetailsDao.findById(1L).orElseThrow(()->new EntityNotFoundException(("User not Found")));
        userDetailsDao.delete(userDetails);
        //userDetailsDao.deleteById(1L);
    }
    private static void fetchUser(UserDetailsDao userDetailsDao) {
        userDetailsDao.findAll().forEach(user->System.out.println(user.toString()));
    }

    public static void rolesOperation(RoleDao roleDao)
    {
        createRoles(roleDao);
        updateRole(roleDao);
        //deleteRole(roleDao);
        fetchRole(roleDao);
    }

    private static void createRoles(RoleDao roleDao) {
        Role role1=new Role("NewAdmin");
        roleDao.save(role1);
        Role role2=new Role("Tutor");
        roleDao.save(role2);
        Role role3=new Role("Student");
        roleDao.save(role3);
    }
    private static void updateRole(RoleDao roleDao) {
        Role role=roleDao.findById(1L).orElseThrow(()->new EntityNotFoundException("Role Not Found!"));
        role.setName("Admin");
        roleDao.save(role);
    }
    private static void fetchRole(RoleDao roleDao) {
        roleDao.findAll().forEach(role->System.out.println(role.toString()));
    }
    private static void deleteRole(RoleDao roleDao) {
        roleDao.deleteById(2L);
    }
    public static void assignRoleToUser(UserDetailsDao userDetailsDao,RoleDao roleDao)
    {
        Role role=roleDao.findByName("Admin");
        if(role==null) throw new EntityNotFoundException("Role Not Found!");
        List<UserDetails> users=userDetailsDao.findAll();
        users.forEach(user->{user.assignRoleToUser(role);
            userDetailsDao.save(user);
        });
    }
    public static void tutorsOperations(UserDetailsDao userDetailsDao, TutorDao tutorDao,RoleDao roleDao)
    {
        //createTutor(userDetailsDao,tutorDao,roleDao);
        //updateTutor(tutorDao);
        //deleteTutor(tutorDao);
       // fetchTutor(tutorDao);
        fetchInActiveTutor(tutorDao);
    }

    private static void fetchInActiveTutor(TutorDao tutorDao) {
        tutorDao.getAllInActiveTutor().forEach(tutor->System.out.println(tutor.toString()));

    }

    private static void createTutor(UserDetailsDao userDetailsDao, TutorDao tutorDao, RoleDao roleDao) {
        Role role=roleDao.findByName("Tutor");
        if(role==null) throw new EntityNotFoundException(("Role not found!"));

        UserDetails userDetails1=new UserDetails("jitu24","jitu@gmail.com","jitu1234");
        userDetails1.assignRoleToUser(role);
        userDetailsDao.save(userDetails1);

        Tutor tutor1 =new Tutor("Jitu","Patel",false,"I am Full Stack Developer, full time employee at Hitachi Vantara.",userDetails1);
        tutorDao.save(tutor1);

        UserDetails userDetails2=new UserDetails("jasmine14","jasmine@gmail.com","jasmine1234");
        userDetails2.assignRoleToUser(role);
        userDetailsDao.save(userDetails2);

        Tutor tutor2 =new Tutor("Jasmine","Patel",true,"I am Full Stack Developer, full time employee at Hitachi Vantara.",userDetails2);
        tutorDao.save(tutor2);
    }
    private static void updateTutor(TutorDao tutorDao) {
        Tutor tutor=tutorDao.findById(1L).orElseThrow(()->new EntityNotFoundException("Tutor not found!"));
        tutor.setSummary("Certified AWS");
        tutorDao.save(tutor);
    }

    private static void deleteTutor(TutorDao tutorDao) {
        tutorDao.deleteById(1L);
    }

    private static void fetchTutor(TutorDao tutorDao) {
        tutorDao.findAll().forEach(tutor->System.out.println(tutor.toString()));

    }

    public static void studentsOperations(UserDetailsDao userDetailsDao, StudentDao studentDao, RoleDao roleDao)
    {
        createStudent(userDetailsDao,studentDao,roleDao);
        updateStudent(studentDao);
        //deleteStudent(studentDao);
        //fetchStudent(studentDao);
    }

    private static void createStudent(UserDetailsDao userDetailsDao, StudentDao studentDao, RoleDao roleDao) {
        Role role=roleDao.findByName("Student");
        if(role==null) throw new EntityNotFoundException(("Role not found!"));

        UserDetails userDetails=new UserDetails("prisha11","prisha@gmail.com","prisha1234");
        userDetails.assignRoleToUser(role);
        userDetailsDao.save(userDetails);

        Student student1 =new Student("Prisha","Patel","BSC",userDetails);
        studentDao.save(student1);

        UserDetails userDetails2=new UserDetails("yug3","yug@gmail.com","yug123456");
        userDetails2.assignRoleToUser(role);
        userDetailsDao.save(userDetails2);

        Student student2 =new Student("Yug","Patel","MBA",userDetails2);
        studentDao.save(student2);
    }

    private static void updateStudent(StudentDao studentDao) {
        Student student=studentDao.findById(1L).orElseThrow(()->new EntityNotFoundException("Student not found!"));
        student.setLevel("B.Tech CSE");
        studentDao.save(student);
    }

    private static void deleteStudent(StudentDao studentDao) {
        studentDao.deleteById(1L);
    }

    private static void fetchStudent(StudentDao studentDao) {
        studentDao.findAll().forEach(student->System.out.println(student.toString()));
    }

    public static void coursesOperations(CourseDao courseDao,TutorDao tutorDao,StudentDao studentDao)
    {
        //createCourses(courseDao,tutorDao);
        //updateCourse(courseDao);
        //deleteCourse(courseDao);
        //fetchCourse(courseDao);
        assignStudentsToCourse(courseDao,studentDao);
       // fetchCoursesOfStudent(courseDao);
    }
       private static void createCourses(CourseDao courseDao, TutorDao tutorDao) {
        Tutor tutor=tutorDao.findById(2L).orElseThrow(()->new EntityNotFoundException("Tutor not found!"));

        Course course1=new Course("Full Stack Java","10 hrs","Introduction to Java FullStack Course",tutor);
        courseDao.save(course1);

        Course course2=new Course("Web Development","5 hrs","Development Course",tutor);
        courseDao.save(course2);

    }

    private static void updateCourse(CourseDao courseDao) {
        Course course=courseDao.findById(1L).orElseThrow(()->new EntityNotFoundException("Course not found!"));
        course.setCourseDuration("20 hrs");
        courseDao.save(course);
    }

    private static void deleteCourse(CourseDao courseDao) {
        courseDao.deleteById(2L);
    }

    private static void fetchCourse(CourseDao courseDao) {
        courseDao.findAll().forEach(courses->System.out.println(courses.toString()));

    }
    private static void assignStudentsToCourse(CourseDao courseDao, StudentDao studentDao) {
        Optional<Student>student1=studentDao.findById(1L);
        Optional<Student>student2=studentDao.findById(2L);
        Course course=courseDao.findById(1L).orElseThrow(()->new EntityNotFoundException("Course not found!"));

        student1.ifPresent(course::assignStudentToCourse);
        student2.ifPresent(course::assignStudentToCourse);
        courseDao.save(course);
    }

    private static void fetchCoursesOfStudent(CourseDao courseDao) {
        //courseDao.getCoursesByStudentId(1L).forEach(course -> System.out.println(course));
    }

}
