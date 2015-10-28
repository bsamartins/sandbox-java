package org.bmartins.springdata.pageable;

import org.bmartins.springdata.pageable.config.Config;
import org.bmartins.springdata.pageable.config.DataSourceConfig;
import org.bmartins.springdata.pageable.config.HibernateConfig;
import org.bmartins.springdata.pageable.entities.User;
import org.bmartins.springdata.pageable.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ Config.class, DataSourceConfig.class, HibernateConfig.class })
@Transactional
public class PageableIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		for(int i = 0; i < 63; i++) {
			User user = new User();
			user.setUsername("username_" + i);
		
			userRepository.save(user);
		}
	}
	
	@Test
	public void test() {
		Page<User> pageResult = userRepository.findAll(new PageRequest(0, 10));
		printPage(pageResult);
		while(pageResult.hasNext()) {			
			pageResult = userRepository.findAll(pageResult.nextPageable());
			printPage(pageResult);
		}
	}
	
	
	@After
	public void tearDown() {
		System.out.println("tearDown");
	}
		
	
	private static void printPage(Page<User> page) {
		System.out.println("====================================");
		System.out.printf("Page: %d of %d\n", page.getNumber()+1, page.getTotalPages());
		page.forEach(u -> System.out.printf("\t%s\n", u.toString()));
		System.out.printf("Total[%d], Showing[%d]\n", page.getTotalElements(), page.getNumberOfElements());
		System.out.println("====================================");
	}
}
