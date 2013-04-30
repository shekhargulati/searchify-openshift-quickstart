package com.openshift.searchifydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.flaptor.indextank.apiclient.IndexTankClient;

@Configuration
@ComponentScan(basePackages = "com.openshift.searchifydemo")
@EnableWebMvc
public class ApplicationConfig {

	private static final String SEARCHIFY_API_URL = "http://:pBt1pstGOpxEd3@dxm6q.api.searchify.com";
	
	@Bean
	public IndexTankClient indexTankClient(){
		IndexTankClient indexTankClient = new IndexTankClient(SEARCHIFY_API_URL);
		return indexTankClient;
	}
	
	@Bean
	public MappingJacksonJsonView jsonView() {
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		jsonView.setPrefixJson(true);
		return jsonView;
	}
	
	
}
