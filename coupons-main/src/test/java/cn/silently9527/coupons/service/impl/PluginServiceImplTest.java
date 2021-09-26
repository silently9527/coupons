package cn.silently9527.coupons.service.impl;

import cn.hutool.core.util.ZipUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class PluginServiceImplTest {


    @Test
    public void installZip() throws FileNotFoundException {
        ZipUtil.unzip(new FileInputStream("/Users/herman/Desktop/classify.zip"), new File("/Users/herman/Desktop/classify3"), StandardCharsets.UTF_8);

    }
}