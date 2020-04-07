package com.f.pro.common.util;

import com.f.pro.common.enums.FileTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description ftp文件上传工具类
 * @Author FangYN
 * @Date 2019/12/18
 **/
@Component
@Data
public class FtpFileUtil {
    /*public static void main(String[] args) throws IOException {
        FtpFileUtil ftpFileUtil = new FtpFileUtil2();
        String filePath = "601160";
        ftpFileUtil.setHost("192.168.31.220");
        ftpFileUtil.setPort(30);
        ftpFileUtil.setUserName("administrator");
        ftpFileUtil.setPassword("DELL123dell");
        ftpFileUtil.setProjectPath("/football_gate/");
        String newFileName = UUIDUtils.getUUID()+".jpg";
        System.out.println("文件名："+newFileName);
        File pdfFile = new File("C:\\Users\\ww\\Pictures\\Saved Pictures\\1111.jpg");
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Boolean resultBoolean = ftpFileUtil.uploadFile(ftpFileUtil.getFtpProjectPath(2), filePath, newFileName, multipartFile.getInputStream());
        System.out.println(resultBoolean);
        System.out.println("访问地址：http://"+ftpFileUtil.getAccessIp(2)+filePath+"/"+newFileName);

        List<String> fileNames = new ArrayList<>();
        fileNames.add("20111230");
        ftpFileUtil.zipMultiFile(ftpFileUtil.getFtpProjectPath(2), filePath, false, fileNames, "");
    }*/

    @Value("${spring.ftp.userName}")
    private String userName;
    @Value("${spring.ftp.password}")
    private String password;
    @Value("${spring.ftp.host}")
    private String host;// 请求ftp地址
    @Value("${spring.ftp.port}")
    private Integer port;// 请求ftp端口号
    @Value("${spring.ftp.httpPath}")
    private String httpPath;// 访问的ip地址及端口号
    @Value("${spring.ftp.projectPath}")
    private String projectPath;// 保存的具体路径(项目名路径)

    /**
     * 获取访问ip前缀
     *
     * @param code 访问的文件类型枚举类标识
     * @return
     */
    public String getAccessIp(int code) {
        // 访问的ip地址及端口号+访问的一级路径
        return this.httpPath + this.projectPath + FileTypeEnum.getName(code);
    }

    /**
     * ftp文件保存的路径
     *
     * @param code
     * @return
     */
    public String getFtpProjectPath(int code) {
        return this.projectPath + FileTypeEnum.getName(code);
    }

    /**
     * 上传文件
     *
     * @param projectPath 保存路径的路径（以项目名为主）
     * @param filePath    文件夹名称
     * @param fileName    保存的文件名
     * @param inputStream 文件流
     * @return
     * @throws IOException
     */
    public Boolean uploadFile(String projectPath, String filePath, String fileName, InputStream inputStream) throws IOException {
        //1、创建FTPClient对象（对于连接ftp服务器，以及上传和上传都必须要用到一个对象）
        FTPClient ftp = getConnect(this.host, this.port, this.userName, this.password);
        if (ftp == null) return false;
        try {
            // 2、changWorkingDirectory(linux上的文件夹)：检测所传入的目录是否存在，如果存在返回true，否则返回false
            // basePath+filePath-->/football_gate/601159
            String path = projectPath + filePath;
            if (!ftp.changeWorkingDirectory(path)) createDir(path, ftp);
            // 3.把文件转换为二进制字符流的形式进行上传
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // 4、这才是真正上传方法storeFile(filename,input),返回Boolean雷类型，上传成功返回true
            if (!ftp.storeFile(fileName, inputStream)) return false;
            // 5.关闭输入流
            inputStream.close();
            // 6.退出ftp
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            closeFtp(ftp);
        }
        return true;
    }

    /**
     * 重命名/移动 文件
     *
     * @param oldPath     原有文件路径及文件名
     * @param projectPath 项目名路径
     * @param newPath     文件新路径 不含文件名 文件夹不变动时，传null值  移动文件时必传
     * @param newFileName 新的文件名字，必传
     * @return
     * @throws IOException
     */
    public Boolean renameFile(String oldPath, String projectPath, String newPath, String newFileName) throws IOException {
        //1、创建FTPClient对象（对于连接ftp服务器，以及上传和上传都必须要用到一个对象）
        FTPClient ftp = getConnect(this.host, this.port, this.userName, this.password);
        if (ftp == null || StringUtils.isEmpty(oldPath)) return false;
        String path = "";
        try {
            // 2.判断文件名是否修改 思路：先根据旧的文件路径更改文件名后，再判断修改路径
            String oldFileName = oldPath.substring(oldPath.lastIndexOf(File.separator) + 1);
            if (!oldFileName.equals(newFileName)) {// 更改文件名
                path = oldPath.substring(0, oldPath.lastIndexOf(File.separator));
                ftp.changeWorkingDirectory(path);//将目录切换至指定路径
                path = path + "/" + newFileName;
                ftp.rename(oldPath, path);
            }
            // 3.判断修改保存的文件夹--即移动文件
            if (!StringUtils.isEmpty(newPath)) {// 更改路径
                if (StringUtils.isEmpty(path)) path = oldPath;
                ftp.enterLocalPassiveMode();
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                InputStream inputStream = ftp.retrieveFileStream(new String(path.getBytes("UTF-8"), "ISO-8859-1"));
                uploadFile(projectPath, newPath, newFileName, inputStream);
                ftp.deleteFile(path);
            }
            // 4.退出ftp
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            closeFtp(ftp);
        }
        return true;
    }

    /**
     * 请求连接Ftp
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    private static FTPClient getConnect(String host, int port, String username, String password) {
        FTPClient ftp = new FTPClient();
        //1、定义返回的状态码
        int reply;
        try {
            ftp.enterLocalPassiveMode();// 这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            //2、连接ftp(当前项目所部署的服务器和ftp服务器之间可以相互通讯，表示连接成功)
            ftp.connect(host, port);
            //3、输入账号和密码进行登录
            ftp.login(username, password);
            //4、接受状态码(如果成功，返回230，如果失败返回503)
            reply = ftp.getReplyCode();
            //5、根据状态码检测ftp的连接，调用isPositiveCompletion(reply)-->如果连接成功返回true，否则返回false
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();//说明连接失败，需要断开连接
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     * 关闭ftp连接
     *
     * @param ftp
     */
    private static void closeFtp(FTPClient ftp) {
        try {
            if (ftp != null && ftp.isConnected())
                ftp.disconnect();// 断开ftp的连接
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir
     * @return
     */
    private static boolean createDir(String dir, FTPClient ftp) {
        if (StringUtils.isBlank(dir))
            return true;
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
            //尝试切入目录
            if (ftp.changeWorkingDirectory(d))
                return true;
            String[] arr = dir.split("/");
            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录
            for (String s : arr) {
                sbfDir.append("/");
                sbfDir.append(s);
                //目录编码，解决中文路径问题
                d = new String(sbfDir.toString().getBytes("GBK"), "iso-8859-1");
                //尝试切入目录
                if (ftp.changeWorkingDirectory(d))
                    continue;
                if (!ftp.makeDirectory(d)) {
                    System.out.println("[失败]ftp创建目录：" + sbfDir.toString());
                    return false;
                }
                System.out.println("[成功]创建ftp目录：" + sbfDir.toString());
            }
            //将目录切换至指定路径
            return ftp.changeWorkingDirectory(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ****** zip相关开始

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ext      文件的扩展名(查询指定扩展名时必传)
     * @throws IOException
     */
    public Boolean getFileNameList(String pathName, String ext) throws IOException {
        ArrayList<String> arFiles = new ArrayList<>();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            FTPClient ftp = getConnect(this.host, this.port, this.userName, this.password);
            if (ftp == null) return false;
            try {
                ftp.changeWorkingDirectory(pathName);// 切换目录到当前目录
                FTPFile[] files = ftp.listFiles();
                for (FTPFile file : files) {
                    if (file.isFile()) {
                        if (StringUtils.isNotEmpty(ext)) {
                            if (file.getName().endsWith(ext))
                                arFiles.add(pathName + file.getName());
                        } else {
                            arFiles.add(pathName + file.getName());
                        }
                    } else if (file.isDirectory()) {
                        // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                        if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                            getFileNameList(pathName + file.getName() + "/", ext);
                        }
                    }
                }
                // 4.退出ftp
                ftp.logout();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException(e);
            } finally {
                closeFtp(ftp);
            }
        }
        return true;
    }

    /**
     * zip打包
     *
     * @param projectPath 访问一级路径 （完整路径 = projectPath+ftpPath+"/"
     * @param ftpPath     文件夹名称（要访问的文件夹名称）
     * @param dirFlag     zip文件中第一层是否包含一级目录，true包含；false没有
     * @param fileNames   需要打包的文件标识（不含后缀名）
     * @param ext         文件的扩展名(查询指定扩展名时必传)
     * @return 用户访问路径格式  "http://"+host+":90/uploads"+projectPath+ftpPath+"/"+ftpPath.zip"
     */
    public Boolean zipMultiFile(String projectPath, String ftpPath, boolean dirFlag,
                                List<String> fileNames, String ext) {
        ZipOutputStream zipOut = null;
        String zipPath;// 本地压缩后zip文件名称
        File zipFile = null;// 本第要压缩的文件
        String filePath = projectPath + ftpPath + "/";// 需要遍历的目录，必须以"/"开始和结束
        if (filePath.startsWith("/") && filePath.endsWith("/")) {
            FTPClient ftp = getConnect(this.host, this.port, this.userName, this.password);
            if (ftp == null) return false;
            try {
                ftp.changeWorkingDirectory(filePath);// 切换目录到当前目录
                FTPFile[] files = ftp.listFiles();
                String diskPath = getLocalTemPath();// 本地临时路径
                zipPath = diskPath + ftpPath + ".zip"; //压缩后zip文件名称
                zipFile = new File(zipPath);
                zipOut = new ZipOutputStream(new FileOutputStream(zipFile));// 压缩文件

                for (FTPFile fileSec : files) {
                    if (fileSec.isFile()) {
                        int len = fileSec.getName().lastIndexOf(".");
                        String suffix = "";
                        if (len > 0)
                            suffix = fileSec.getName().substring(len);
                        for (String str : fileNames) {
                            if (fileSec.getName().equals(str + suffix)) { // 提取指定文件名打包
                                String baseDir = "";
                                if (dirFlag)
                                    baseDir = fileSec.getName() + File.separator;
                                recursionZip(ftp, zipOut, fileSec, filePath + fileSec.getName(), baseDir);
                                break;
                            }
                        }
                    } else if (fileSec.isDirectory()) {
                        // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                        if (!".".equals(fileSec.getName()) && !"..".equals(fileSec.getName()))
                            zipMultiFile(projectPath, ftpPath, false, fileNames, ext);
                    }
                }
                zipOut.close();
                InputStream inputStream = new FileInputStream(new File(zipPath));
                uploadFile(projectPath, "", ftpPath + ".zip", inputStream);
                ftp.logout();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != zipOut) {
                    try {
                        zipOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                closeFtp(ftp);
                if (null != zipFile)
                    deleteDir(zipFile);
            }
        }
        return true;
    }

    private static void recursionZip(FTPClient ftp, ZipOutputStream zipOut, FTPFile file, String ftpFilePath, String baseDir) throws Exception {
        if (file.isDirectory()) {
            ftp.changeWorkingDirectory(ftpFilePath);// 切换目录到当前目录
            FTPFile[] files = ftp.listFiles();
            for (FTPFile fileSec : files) {
                recursionZip(ftp, zipOut, fileSec, ftpFilePath + fileSec.getName(), baseDir + fileSec.getName() + File.separator);
            }
        } else {
            byte[] buf = new byte[1024];
            InputStream input = ftp.retrieveFileStream(ftpFilePath);
            if (input == null) return;
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while ((len = input.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            input.close();
            ftp.completePendingCommand();// 此处注意：必须调用本方法，否则后续获取文件为null指针异常，
        }
    }

    /**
     * 获取当前项目所在服务器位置
     *
     * @return
     */
    private static String getLocalTemPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String savePath = request.getSession().getServletContext().getRealPath("");
        int indexOf = savePath.lastIndexOf("\\");
        if (indexOf == -1) { // Linux下临时路径获取， 即获取当前项目所在目录--相对路径
            savePath = System.getProperty("user.dir");
        } else { // 获取绝对路径
            savePath = savePath.substring(0, savePath.lastIndexOf("\\"));
        }
        return savePath + "/";
    }

    /**
     * 删除本地指定文件夹及文件夹内容
     *
     * @param dir
     */
    private static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++)
                deleteDir(new File(dir, children[i]));
        }
        dir.delete();
    }

}
