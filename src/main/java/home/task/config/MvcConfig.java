package home.task.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;
import java.util.ArrayList;


@Configuration
public class MvcConfig implements WebMvcConfigurer, TomcatContextCustomizer {
    @Value("${upload.path}")
    private String uploadPath;

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver
                = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void customize(Context context) {
        JspPropertyGroup pg = new JspPropertyGroup();
        pg.addUrlPattern("/*");
        pg.setPageEncoding("UTF-8");
        pg.setTrimWhitespace("true"); // optional, but nice to have
        ArrayList<JspPropertyGroupDescriptor> pgs = new ArrayList<>();
        pgs.add(new JspPropertyGroupDescriptorImpl(pg));
        context.setJspConfigDescriptor(new JspConfigDescriptorImpl(pgs, new ArrayList<TaglibDescriptor>()));
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}