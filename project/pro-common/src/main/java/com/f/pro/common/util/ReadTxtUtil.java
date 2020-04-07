package com.f.pro.common.util;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Description 读取txt文件工具类
 * @Author FangYN
 * @Date 2020/4/2
 **/
public class ReadTxtUtil {

    /*
    public static void main(String[] args) throws IOException {
        File testFile = new File("F:\\test.txt");// 注：文本内容构造： [{key:value,key:value},{key:value,key:value}]
        FileInputStream fileInputStream = new FileInputStream(testFile);
        MultipartFile multipartFile = new MockMultipartFile(testFile.getName(), testFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        ReadTxtUtil.readJSONListTxt(multipartFile, T.class);

        File objectFile = new File("F:\\test2.txt");// 注：文本内容构造： [{key:value,key:value},{key:value,key:value}]
        FileInputStream fileInputStream2 = new FileInputStream(objectFile);
        MultipartFile multipartFile2 = new MockMultipartFile(objectFile.getName(), objectFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream2);
        ReadTxtUtil.readJSONObjectTxt(multipartFile2, T.class);
    }
    */

    /**
     * 读取txt文件(文件内容由JSON字符串构成）并写入到list实体类中
     * 注：文本内容构造： [{key:value, key:value}, {key:value, key:value}]
     *
     * @param file
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readJSONListTxt(MultipartFile file, Class<T> cls) throws IOException {
        if (file.isEmpty()) return null;
        String jsonData = textParsingJsonDate(file);
        if (jsonData.isEmpty()) return null;
        List<T> entityList = JSONArray.parseArray(jsonData, cls);// json字符串转实体类
        System.out.println("读取的list的长度" + entityList.size());
        return entityList;
    }

    /**
     * 读取txt文件(文件内容由JSON字符串构成）并写入到实体类中
     * 注：文本内容构造： {key:value, key:value}
     *
     * @param file
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readJSONObjectTxt(MultipartFile file, Class<T> cls) throws IOException {
        if (file.isEmpty()) return null;
        String jsonData = textParsingJsonDate(file);
        if (jsonData.isEmpty()) return null;
        T entity = JSONArray.parseObject(jsonData, cls);// json字符串转实体类
        System.out.println(entity);
        return entity;
    }

    private static String textParsingJsonDate(MultipartFile file) throws IOException {
        BufferedReader br = null;
        InputStream inputStream = null;
        InputStreamReader isr = null;
        String jsonData = "";
        try {
            inputStream = file.getInputStream();
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null)
                buffer.append(line);
            jsonData = buffer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("文件流读取失败");
            throw new IOException(ex);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (isr != null) isr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("I/O流关闭失败");
            }
        }
        return jsonData;
    }

}
