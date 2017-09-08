package com.cisco.cmad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cisco.cmad.api.Blog;
import com.cisco.cmad.api.BlogComment;
import com.cisco.cmad.api.BlogNotFoundException;
import com.cisco.cmad.api.Blogger;
import com.cisco.cmad.api.BloggerException;
import com.cisco.cmad.api.InvalidUserDetailsException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.service.SaviSaviNenapuBlogger;



public class SaviSaviNenapuBloggerTest {

	private static Blogger blogger;
	private User user = null;
	private Blog blog = null;
	private BlogComment comment = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		blogger = new SaviSaviNenapuBlogger();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		blogger = null;
	}

	@Before
	public void setUp() throws Exception {

		// Set up user
		user = new User();
		user.setUserId("ninut");
		user.setPassword("ninut");
		user.setFirstName("ninut");

		//Set up blog
		blog = new Blog();
		blog.setTitle("HELLO WORLD!(UT)");
		blog.setDescription("HELLO ...WORLD IS NICE(UT)");

		//Set up comment
		comment = new BlogComment();
		comment.setComment("nice write (UT)");


	}

	@After
	public void tearDown() throws Exception {
		user = null;
		blog = null;
		comment = null;
	}

	@Test
	public void signupNewUserTest() {
		try {
			blogger.signupNewUser(user);
			//clean up
			blogger.deleteUser("ninut");
		} catch (InvalidUserDetailsException iude) {
			fail();
		} catch (UserAlreadyExistsException uaee) {
			fail();
		} catch (BloggerException be) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void signupNewUserTest_N01() {
		User invalidUser = new User();
		invalidUser.setUserId("invalidUser");
		try {
			blogger.signupNewUser(invalidUser);
			fail("Expecting InvalidUserDetailsException");
		} catch (InvalidUserDetailsException iude) {

		} catch (UserAlreadyExistsException uaee) {
			fail();
		} catch (BloggerException be) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void loginUserTest(){
		try {
			blogger.signupNewUser(user);
			blogger.loginUser("ninut","ninut");
			//cleanup
			blogger.deleteUser("ninut");
		} catch (SecurityException iude) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getUserDetailsTest(){
		try {
			blogger.signupNewUser(user);
			blogger.getUserDetails("ninut");
			//cleanup
			blogger.deleteUser("ninut");
		} catch (UserNotFoundException unfe) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void addAndReadBlogTest(){

		String blogId;
		try {
			System.out.println("########B4 ADD");
			blogger.addBlog(blog);
			System.out.println("########after ADD");
			List<Blog> blogList = blogger.getAllBlogs(0,2,null,null);
			blogId = blogList.get(0).getBlogId();
			System.out.println("***blogId STR"+blogId);
			System.out.println("***blogId "+blogList.get(0).getBlogId());
			//clean up
			blogger.deleteBlog(blogId);
		} catch (BlogNotFoundException bnfe) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void addAndReadCommentTest(){
		String blogId , blogCommentId;
		try {
			System.out.println("addAndReadCommentTest");
			blogger.addBlog(blog);
			System.out.println("addAndReadCommentTest added blog");
			List<Blog> blogList = blogger.getAllBlogs(0,2,null,null);
			blogId = blogList.get(0).getBlogId();
			System.out.println("addAndReadCommentTest blogId:"+blogId);
			comment.setBlogId(blogId);
			blogger.addComment(comment);
			System.out.println("addAndReadCommentTest added comment");
			List<BlogComment> blogCommentList = blogger.getAllComments(blogId,0, 2, null);
			blogCommentId = blogCommentList.get(0).getCommentId();
			System.out.println("Comment id :"+blogCommentId);
			// clean up
			blogger.deleteBlog(blogId);
		} catch (BlogNotFoundException bnfe) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getBlogCountTest(){
		String blogId;
		try {
			//get initial count
			long initialCount = blogger.getBlogCount(null, null);

			blogger.addBlog(blog);
			long count = blogger.getBlogCount(null, null);
			assertEquals((initialCount+1), count);
			// clean up
			List<Blog> blogList = blogger.getAllBlogs(0,2,null,null);
			blogId = blogList.get(0).getBlogId();
			System.out.println("getBlogCountTest blogId :"+blogId);
			blogger.deleteBlog(blogId);
		} catch (BlogNotFoundException bnfe) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getCommentCountTest(){
		String blogId;
		try {

			blogger.addBlog(blog);
			List<Blog> blogList = blogger.getAllBlogs(0,2,null,null);
			blogId = blogList.get(0).getBlogId();

			//get initial count
			long initialCount = blogger.getCommentCount(blogId);
			comment.setBlogId(blogId);
			blogger.addComment(comment);


			long count = blogger.getCommentCount(blogId);
			assertEquals((initialCount+1), count);
			// clean up
			blogger.deleteBlog(blogId);
		} catch (BlogNotFoundException bnfe) {
			fail();
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
}
