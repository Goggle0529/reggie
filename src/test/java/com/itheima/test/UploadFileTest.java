package com.itheima.test;

import org.junit.Test;

public class UploadFileTest {

    @Test
    public void test1(){
        String filesName = "ererwe.jpg";
        String suffix = filesName.substring(filesName.lastIndexOf("."));
        System.out.println(suffix);
    }

}
