package com.f.pro;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *@Author: FangYN
 *@Date: 2020/4/3
 *@Description: 
 */
@EnableAsync
@SpringBootApplication
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ProjectApplication {
    public static void main(String[] args){
        SpringApplication.run(ProjectApplication.class,args);
    }
}
