package com.merchant.api;

import static io.restassured.RestAssured.given;

import java.time.Instant;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.merchant.data.Comment;
import com.merchant.data.DataCache;
import com.merchant.data.Post;
import com.merchant.data.User;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CommentTest extends TestBase {
	
	private User user;
	private User user2;
	private Post post;


	@BeforeClass
	public void setUp() {
		RestAssured.basePath = "/comments";
		
		 user = DataCache.getUser();
		 ObjectCreatorService.createUser(user);
		 
		 user2 = DataCache.getUser();
		 ObjectCreatorService.createUser(user2);


		 post = DataCache.getPost(user);
		 ObjectCreatorService.createPost(user, post);
		 
		
	}

	@Test
	public void createComment() {

		Comment comment =  DataCache.getComment(user, post);		
		ObjectCreatorService.createComment(user, post, comment);

	}

	@Test
	public void getComment() {
		
		Comment comment =  DataCache.getComment(user, post);
		ObjectCreatorService.createComment(user, post, comment);
		
		given().when().get("/" + comment.getId()).then().statusCode(200).spec(comment.getCommentSpec());

	}

	@Test
	public void updateComment() {
		
		Comment comment =  DataCache.getComment(user, post);		
		ObjectCreatorService.createComment(user, post, comment);

		comment.setBody("This comment is udpated");
		
		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(comment).when().put("/" + comment.getId())
				.then().statusCode(200).spec(comment.getCommentSpec()).extract().path("id").toString();

	}

}
