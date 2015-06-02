package com.sijiang.addressbook.webservice_config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		
		return new ServletRegistrationBean(servlet, "/addressbook/*");
	}

	@Bean(name = "addressbook")
	public DefaultWsdl11Definition defaultWsdl11Definition() {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		
		wsdl11Definition.setPortTypeName("AddressBookPort");
		wsdl11Definition.setLocationUri("/addressbook");
		wsdl11Definition.setTargetNamespace("http://addressbook.com");
		
		CommonsXsdSchemaCollection schemaCollection = new CommonsXsdSchemaCollection();
		
        Resource[] xsdResources = {
                new ClassPathResource("OpenAccountRequest.xsd")  ,
                new ClassPathResource("OpenAccountResponse.xsd")
        };

		schemaCollection.setXsds(xsdResources);
		wsdl11Definition.setSchemaCollection(schemaCollection);
		
		return wsdl11Definition;
	}
}