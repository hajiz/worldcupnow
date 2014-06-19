package com.hajix.wcn.server;

import java.util.EnumSet;
import java.util.Map;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.hajix.wcn.rest.MatchResource;
import com.hajix.wcn.rest.PostResource;
import com.hajix.wcn.rest.UserResource;
import com.hajix.wcn.services.HealthChecker;
import com.hajix.wcn.services.MemoryPostStorage;
import com.hajix.wcn.services.PostStorage;
import com.hajix.wcn.services.ResultFetcher;
import com.hajix.wcn.services.ResultLookup;
import com.hajix.wcn.services.ResultLookupImpl;
import com.hajix.wcn.services.UserLookup;
import com.hajix.wcn.services.UserLookupImpl;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SimpleServer {
    
    private static final Logger log = Logger.getLogger(SimpleServer.class);

    private SimpleServer() {
    }
    
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(
                Stage.PRODUCTION,
                new TestModule()
        );
        
        int port = 9999;
        try {
            port = Integer.valueOf(System.getenv("PORT"));
        } catch (Exception e1) {
            log.info("Failed to get port number");
        }
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType>of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
        context.addServlet(DefaultServlet.class, "/*");
        String staticsDir = SimpleServer.class.getClassLoader().getResource("statics/").toExternalForm();
        context.setBaseResource(Resource.newResource(staticsDir));
        
        log.info("Starting result fetcher");
        injector.getInstance(ResultFetcher.class).start();
        log.info("Starting health checker");
        injector.getInstance(HealthChecker.class).start();
        try {
            log.info("Starting the server");
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Failed to start the server", e);
            server.stop();
            System.exit(1);
        }
    }

}


class TestModule extends ServletModule {
    
    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(Singleton.class);
        bind(IndexFileServlet.class).in(Singleton.class);
        bind(ForwardToIndexServlet.class).in(Singleton.class);
        bind(PostResource.class).in(Singleton.class);
        bind(UserResource.class).in(Singleton.class);
        bind(MatchResource.class).in(Singleton.class);
        
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        
        bind(PostStorage.class).to(MemoryPostStorage.class).in(Singleton.class);
        bind(UserLookup.class).to(UserLookupImpl.class).in(Singleton.class);
        bind(ResultLookup.class).to(ResultLookupImpl.class).in(Singleton.class);
        
        serve("/").with(ForwardToIndexServlet.class);
        serve("*.html", "*.js", "*.css", "*.png", "*.ico").with(IndexFileServlet.class);
        serve("/*").with(GuiceContainer.class, getJerseyOptions(true));
    }
    

    private Map<String, String> getJerseyOptions(boolean logRequests) {
        Map<String, String> options;
        if(logRequests) {
            options = ImmutableMap.of(
                "com.sun.jersey.api.json.POJOMappingFeature", "true",
                "com.sun.jersey.spi.container.ContainerRequestFilters", "com.hajix.wcn.server.HeaderLogger",
                "com.sun.jersey.spi.container.ContainerResponseFilters", "com.hajix.wcn.server.HeaderLogger"
            );
        } else {
            options = ImmutableMap.of("com.sun.jersey.api.json.POJOMappingFeature", "true");
        }
        return options;
    }
}