package com.cisco.cmad.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class BloggerApplication extends ResourceConfig {
	public BloggerApplication() {
		packages("com.cisco.cmad.rest");
	}
}
