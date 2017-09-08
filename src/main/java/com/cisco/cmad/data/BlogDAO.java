package com.cisco.cmad.data;

import java.util.List;

import com.cisco.cmad.api.Blog;

public interface BlogDAO {
	String createBlog(Blog blog);
	Blog readBlog(String blogId);
	List<Blog> getAllBlogs(int offset,int pageSize,String searchStr,String userFilter);
	long getBlogCount(String searchStr,String userFilter);
	void deleteBlog(String blogId);
}
