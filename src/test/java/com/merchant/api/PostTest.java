package com.merchant.api;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.merchant.data.DataCache;
import com.merchant.data.Post;
import com.merchant.data.User;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import java.time.Instant;

public class PostTest extends TestBase {
	
	private User user = null;
	private Post post = null;

	@BeforeClass
	public void setUp() {
		
	   RestAssured.basePath = "/posts";
		
	   user = DataCache.getUser();
	   ObjectCreatorService.createUser(user);
	}

	@Test
	public void createPost() {

		post = DataCache.getPost(user);
		ObjectCreatorService.createPost(user, post);
	}

	@Test(dependsOnMethods = "createPost")
	public void getPost() {
		given().when().get("/" + post.getId()).then().statusCode(200).spec(post.getPostSpec());
	}

	@Test(dependsOnMethods = "createPost")
	public void updatePost() {
		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(post).when().put("/" + post.getId()).then()
				.statusCode(200).spec(post.getPostSpec()).extract().path("id").toString();
	}

}
