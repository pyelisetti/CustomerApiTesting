package com.merchant.api;

import static io.restassured.RestAssured.given;

import java.time.Instant;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.merchant.data.DataCache;
import com.merchant.data.User;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;




public class UserTest extends TestBase {
	
	private User user;
	
	@BeforeClass
	public void beforeClass() {
        RestAssured.basePath = "/users";

        user = DataCache.getUser();
	}

	@Test
	public void createUser() {
		/*
		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(user).
				when().post().then().
				statusCode(201).
				spec(user.getUserSpec());*/
		
		ObjectCreatorService.createUser(user);

		Reporter.log("user id = " + true);
	}
	
	@Test
	public void getUser() {
		
		given().
				when().get("/"+user.getId()).then().
				statusCode(200).
				spec(user.getUserSpec());

		Reporter.log("user id = " + true);
	}
	
	
	@Test
	public void updateUser() {
		
		user.setFirstName("NewFirstName");
		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(user).
				when().put("/"+user.getId()).then().
				statusCode(200).
				spec(user.getUserSpec()).extract().
                path("id").toString();

		Reporter.log("user id = " + true);
	}
	

}
