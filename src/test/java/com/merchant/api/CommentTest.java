package com.merchant.api;

import static io.restassured.RestAssured.given;

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
	
	private Comment comment;


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

	    comment =  DataCache.getComment(user, post);		
		ObjectCreatorService.createComment(user, post, comment);

	}

	@Test(dependsOnMethods = "createComment")
	public void getComment() {
		
		given().when().get("/" + comment.getId()).then().statusCode(200).spec(comment.getCommentSpec());

	}

	@Test(dependsOnMethods = "createComment")
	public void updateComment() {

		comment.setBody("This comment is udpated");
		
		given().accept(ContentType.JSON).contentType(ContentType.JSON).body(comment).when().put("/" + comment.getId())
				.then().statusCode(200).spec(comment.getCommentSpec()).extract().path("id").toString();

	}

}
