package com.martiiin.hv;

import com.martiiin.hv.config.FileJSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.io.File;

@EnableCaching
@SpringBootApplication
public class HvApplication {

    @Value("${filePath}")
    String filePath;

    @Bean
    public FileJSON getFileJSON(){
       return new FileJSON(filePath);
    }


    @Bean
    public File[] getDiskList(){
        return File.listRoots();
    }

    public static void main(String[] args) {
        SpringApplication.run(HvApplication.class, args);
    }

}
