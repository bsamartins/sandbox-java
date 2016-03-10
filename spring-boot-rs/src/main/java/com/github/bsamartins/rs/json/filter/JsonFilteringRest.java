package com.github.bsamartins.rs.json.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/json/filter")
public class JsonFilteringRest {
	
	@Autowired
	private ObjectMapper mapper;
	
	@JsonFilter("dynamicFilter")
	@SuppressWarnings("unused")
	private static class User {

		private long id;		
		private String firstName;
		private String lastName;

		public User(long id, String firstName, String lastName) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}		
	}
	
	@RequestMapping
	public String filter(@RequestParam String[] fields) {
		System.out.println(fields.length);
		List<User> users = new ArrayList<>();
		users.add(new User(0, "Akira", "Kurosawa"));
		users.add(new User(1, "Takeshi", "Kitano"));
		
		Set<String> filterProperties = new HashSet<>(Arrays.asList(fields));
		FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filterProperties));
		
		try {
			mapper.setFilterProvider(filters);
			 String json = mapper.writeValueAsString(users);
			 return json;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
}
