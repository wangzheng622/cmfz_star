package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.hibernate.validator.constraints.EAN;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CmfzStarApplicationTests {

    @Autowired
    private AdminDao adminDaO;

    @Test
    void contextLoads() {
        List<Admin> list = adminDaO.selectAll();
        for(Admin admin:list){
            System.out.println(admin);
        }


    }

}
