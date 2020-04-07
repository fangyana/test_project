package com.f.pro.common.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.MoveMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /*String uploadPath = "http://172.24.192.86/uploads/files/";//服务器内网
    //String uploadPath = "http://39.104.93.32/uploads/files/";//服务器外网
    String getPath = "http://39.104.93.32/uploads/files/";*/
    public static String uploadPath = "http://192.168.31.220:90/uploads/";
    public static String getPath = "http://192.168.31.220:90/uploads/";
    public static String davusername = "fileadmin";
    public static String davpassword = "fileadmin";

    /**
     * @param file     上传的文件
     * @param filename 保存的文件名称
     * @return filepath 返回文件保存路径
     */
    public static String httpFileUpload(MultipartFile file, String filename) {
        if (filename == null || filename.equals("")) {
            return "";
        }
        HttpClient client = null;
        PutMethod pm = null;
        String filepath = uploadPath + filename;
        String filegetpath = getPath + filename;
        try {
            client = new HttpClient();
            Credentials crd = new UsernamePasswordCredentials(davusername, davpassword);
            client.getState().setCredentials(AuthScope.ANY, crd);
            pm = new PutMethod(filepath);

            RequestEntity requestEntity = new InputStreamRequestEntity(file.getInputStream());
            pm.setRequestEntity(requestEntity);
            client.executeMethod(pm);
            if (pm.getStatusCode() == 201) {
                logger.info("文件上传成功 ！ path:" + uploadPath + filename + " Name:" + pm.getName() + " result:" + pm.getStatusCode() + " msg:" + pm.getStatusText());
            } else {
                logger.info("文件上传出现错误 ！ path:" + uploadPath + filename + " Name:" + pm.getName() + " result:" + pm.getStatusCode() + " msg:" + pm.getStatusText());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("文件上传出现错误！");
        } finally {
            file = null;
            client = null;
            pm = null;
        }
        return filegetpath;
    }

    /**
     * @param ins      文件流方式上传文件
     * @param filename 保存的文件名称
     * @return filepath 返回文件保存路径
     */
    public static String httpFileUpload(InputStream ins, String filename) {
        if (filename == null || filename.equals("")) {
            return "";
        }
        HttpClient client = null;
        PutMethod pm = null;
        String filepath = uploadPath + filename;
        String filegetpath = getPath + filename;
        try {
            client = new HttpClient();
            Credentials crd = new UsernamePasswordCredentials(davusername, davpassword);
            client.getState().setCredentials(AuthScope.ANY, crd);
            pm = new PutMethod(filepath);

            RequestEntity requestEntity = new InputStreamRequestEntity(ins);
            pm.setRequestEntity(requestEntity);
            client.executeMethod(pm);
            if (pm.getStatusCode() == 201) {
                logger.info("文件上传成功 ！ path:" + uploadPath + filename + " Name:" + pm.getName() + " result:" + pm.getStatusCode() + " msg:" + pm.getStatusText());
            } else {
                logger.info("文件上传出现错误 ！ path:" + uploadPath + filename + " Name:" + pm.getName() + " result:" + pm.getStatusCode() + " msg:" + pm.getStatusText());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("文件上传出现错误！");
        } finally {
            ins = null;
            client = null;
            pm = null;
        }
        return filegetpath;
    }

    /**
     * @param filename 需要删除的文件名称
     * @return 是否删除成功
     */
    public static boolean httpFileDelete(String filename) {
        if (filename == null || filename.equals("")) {
            return false;
        }
        HttpClient client = null;
        DavMethod delete = null;
        boolean deletetag = false;
        try {
            client = new HttpClient();
            Credentials crd = new UsernamePasswordCredentials(davusername, davpassword);
            client.getState().setCredentials(AuthScope.ANY, crd);
            delete = new DeleteMethod(uploadPath + filename);

            client.executeMethod(delete);
            if (delete.getStatusCode() == 204) {
                logger.info("文件删除成功 ！ path:" + uploadPath + filename + " result:" + delete.getStatusCode() + " msg:" + delete.getStatusText());
            } else {
                logger.info("文件删除出现错误 ！ path:" + uploadPath + filename + " result:" + delete.getStatusCode() + " msg:" + delete.getStatusText());
            }
            deletetag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("文件删除出现错误！");
        } finally {
            client = null;
            delete = null;
        }

        return deletetag;
    }

    /**
     * @param oldfilename 原始文件名称
     * @param newfilename 新的文件名称
     * @return 返回新的文件路劲
     */

    public static String httpFileRename(String oldfilename, String newfilename) {
        if (newfilename == null || oldfilename == null || newfilename.equals("") || oldfilename.equals("")) {
            return "";
        }
        HttpClient client = null;
        DavMethod rename = null;
        String filepathname = uploadPath + oldfilename;
        String filegetpath = getPath + oldfilename;
        try {
            client = new HttpClient();
            Credentials crd = new UsernamePasswordCredentials(davusername, davpassword);
            client.getState().setCredentials(AuthScope.ANY, crd);
            rename = new MoveMethod(filepathname, uploadPath + newfilename, true);

            client.executeMethod(rename);
            if (rename.getStatusCode() == 201) {
                logger.info("文件重命名成功 ！ path:" + uploadPath + newfilename + " result:" + rename.getStatusCode() + " msg:" + rename.getStatusText());
            } else {
                logger.info("文件重命名出现错误 ！ path:" + uploadPath + newfilename + " result:" + rename.getStatusCode() + " msg:" + rename.getStatusText());
            }
            filepathname = uploadPath + newfilename;
            filegetpath = getPath + newfilename;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("文件重命名出现错误！");
        } finally {
            client = null;
            rename = null;
        }
        return filegetpath;
    }

    /**
     * 处理文本编辑器上传的图片，将其从临时文件夹转移到图片服务器上
     *
     * @param params
     */
    public static void dealContent(Map<String, Object> params) {
        String ueditPicPath = MapUtils.getString(params, "ueditPicPath");
        String content = MapUtils.getString(params, "content");
        String regex;
        FileUtil fileUtil = new FileUtil();
        List<String> list = new ArrayList<>();
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(content);
        while (ma.find()) {
            String temp = ma.group();
            int index = temp.lastIndexOf("/");
            list.add(temp.substring(index + 1, temp.length() - 1));
        }
        if (ueditPicPath != null && !ueditPicPath.equals("")) {
            String[] ueditPicPathArr = ueditPicPath.split(",");
            for (String path : ueditPicPathArr) {
                if (!list.contains(path)) {
                    httpFileDelete(path.substring(path.lastIndexOf("/") + 1));
                }
            }
        }
    }


    public static String saveExcelToLocal(String LOCAL_PATH, MultipartFile excel) {
        //临时文件名
        String fileName = UUIDUtils.getUUID() + "." + excel.getOriginalFilename().split("\\.")[1];
        File localFile = new File(LOCAL_PATH);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        String fullName = localFile.getPath() + File.separator + fileName;
        File file = new File(fullName);
        try {
            excel.transferTo(file);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void downloadFile(OutputStream outOs, String fileUrl) {
        InputStream in = null;
        HttpURLConnection conn = null;
        String fileName = null;
        try {
            //初始化连接
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] buffer = new byte[2048];
                in = conn.getInputStream();
                int count = 0;
                while ((count = in.read(buffer)) != -1) {
                    if (count != 0) {
                        outOs.write(buffer, 0, count);
                    } else {
                        break;
                    }
                }
                in.close();
                outOs.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * LOCAL_PATH 文件存储的位置
     * fileUrl 待下载文件地址
     *
     * @return 文件存储位置
     */
    public static String downloadFromUrl(String LOCAL_PATH, String fileUrl) {

        InputStream in = null;
        OutputStream out = null;
        HttpURLConnection conn = null;
        String fileName = null;
        try {
            //初始化连接
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //临时文件名
            fileName = UUIDUtils.getUUID() + fileUrl.substring(fileUrl.lastIndexOf("."));

            File localFile = new File(LOCAL_PATH);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            //读取数据
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] buffer = new byte[2048];
                in = conn.getInputStream();
                out = new FileOutputStream(new File(localFile, fileName));
                int count = 0;
                while ((count = in.read(buffer)) != -1) {
                    if (count != 0) {
                        out.write(buffer, 0, count);
                        // System.out.printf("---->%1$.2f%%\n",(double)finished/size*100);
                    } else {
                        break;
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return LOCAL_PATH + File.separator + fileName;
    }

    /*public static void main(String[] args) {
        FileUtil fu = new FileUtil();
        fu.httpFileDelete("");
    }*/
}
