package com.course.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class MarketplaceApplication {

	//@Autowired
	//private UserDetailsDao userDetailsDao;
	//@Autowired
	//private CourseDao courseDao;
	//@Autowired
	//private TutorDao tutorDao;
	//@Autowired
	//private StudentDao studentDao;
//	@Autowired
//	private RoleDao roleDao;


	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}


	//@Override
	//public void run(String... args) throws Exception {
		//OperationUtility.usersOperations(userDetailsDao);
		//OperationUtility.rolesOperation(roleDao);
		//OperationUtility.assignRoleToUser(userDetailsDao,roleDao);
		//OperationUtility.tutorsOperations(userDetailsDao,tutorDao,roleDao);
		//OperationUtility.studentsOperations(userDetailsDao,studentDao,roleDao);
		//OperationUtility.coursesOperations(courseDao,tutorDao,studentDao);
	//}
	//@Bean
	//public PasswordEncoder passwordEncoder()
	//{
	//	return new BCryptPasswordEncoder();
	//}
}
