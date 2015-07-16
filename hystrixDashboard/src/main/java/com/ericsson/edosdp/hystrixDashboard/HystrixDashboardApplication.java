package com.ericsson.edosdp.hystrixDashboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;

//import hystrixdashboard.stream.MockStreamServlet;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	new SpringApplicationBuilder(HystrixDashboardApplication.class).web(true).run(args);
    }

	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HystrixDashboardApplication.class).web(true);
    }

}