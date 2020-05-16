package com.ljy.bs;

import com.ljy.bs.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(SpringUtil.class)
@SpringBootApplication
public class BsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsApplication.class, args);
    }

}
