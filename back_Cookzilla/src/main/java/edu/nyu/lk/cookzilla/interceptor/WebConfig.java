package edu.nyu.lk.cookzilla.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/upload")
                .excludePathPatterns("/register")
                .excludePathPatterns("/retrieve")
                .excludePathPatterns("/isIn")
                .excludePathPatterns("/foundPassword")
                .excludePathPatterns("/resetPassword");
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 设置允许跨域的路径
//        final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };
//        registry.addMapping("/**")
//                // 设置允许跨域请求的域名
//                .allowedOrigins("http://127.0.0.1:8080");
//                // 是否允许证书
////                .allowCredentials(true)
////                .allowedMethods(ORIGINS)
////                .maxAge(3600);
//    }
}
