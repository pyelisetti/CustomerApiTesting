package com.merchant.data;

import java.time.Instant;

public class DataCache {

	public static User getUser() {
		String id = getId();
		User user = new User("user" + id, "first" + id, "last" + id);
		return user;
	}

	public static Post getPost(User user) {
		String id = getId();
		Post post = new Post(user.getId(), "title " + id, "body " + id);
		return post;
	}

	public static Comment getComment(User user, Post post) {
		String id = getId();
		Comment comment = new Comment(post.getId(), user.getId(), "body " + id);
		return comment;
	}

	private static String getId() {
		return String.valueOf(Instant.now().toEpochMilli());
	}
}
