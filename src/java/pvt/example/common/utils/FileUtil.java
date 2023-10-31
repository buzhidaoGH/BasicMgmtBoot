package pvt.example.common.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 类&emsp;&emsp;名：FileUtil <br/>
 * 描&emsp;&emsp;述：文件处理工具
 */
public class FileUtil {
    /**
     * 获取target的目录或者jar包的位置
     * @return File
     */
    public static String getLocalDirectory(String dir) {
        ApplicationHome home = new ApplicationHome(FileUtil.class);
        File jarF = home.getSource();
        return new File(jarF.getParent() + "\\" + dir).getAbsolutePath() + "\\";
    }

    /**
     * 上传文件保存至本地;默认根目录(默认同级upload和开发同级upload)
     * @param myFile 接收的文件
     * @param dir    保存的目录
     * @return 文件的路径名称;服务器;
     */
    public static String uploadFile(MultipartFile myFile, String dir) {
        return FileUtil.uploadFile(myFile, dir, "upload");
    }

    /**
     * 上传文件保存至本地;
     * @param myFile  接收的文件
     * @param dir     保存的目录
     * @param rootDir 保存的默认根目录(默认同级upload和开发同级upload)
     * @return 文件的路径名称;服务器;
     */
    public static String uploadFile(MultipartFile myFile, String dir, String rootDir) {
        String localDirectory = FileUtil.getLocalDirectory(rootDir + "\\");
        String userDir = System.getProperty("user.dir") + "\\" + rootDir + "\\";
        if (!myFile.isEmpty()) {
            String filename = myFile.getOriginalFilename();
            filename = UUID.randomUUID() + "-" + filename;
            try {
                File localDirectoryFile = new File(localDirectory + dir + "\\", filename);
                if (!localDirectoryFile.exists()) {boolean mkdirs = localDirectoryFile.mkdirs();}
                myFile.transferTo(localDirectoryFile);
                File userDirFile = new File(userDir);
                // 判断文件夹2是否存在并且是否和文件夹1同名位置,不存在则不创建并传入
                if (!localDirectory.equals(userDir) &&
                        userDirFile.exists() &&
                        userDirFile.isDirectory()) {
                    userDirFile = new File(userDir + dir + "\\");
                    if (!userDirFile.exists()) {boolean mkdirs = userDirFile.mkdirs();}
                    FileCopyUtils.copy(localDirectoryFile, new File(userDirFile, filename));
                }
                return "/" + rootDir + "/" + dir + "/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 删除本地文件(默认根目录为同级upload和开发同级upload)
     * @param fileName 文件名称
     * @param dir      文件目录
     * @return 是否删除成功
     */
    public static Boolean deleteFile(String fileName, String dir) {
        return FileUtil.deleteFile(fileName, dir, "upload");
    }

    /**
     * 删除本地文件
     * @param fileName 文件名称
     * @param dir      文件目录
     * @param rootDir  默认根目录(upload)
     * @return 是否删除成功
     */
    public static Boolean deleteFile(String fileName, String dir, String rootDir) {
        return FileUtil.deleteFile(rootDir + "\\" + dir + "\\" + fileName);
    }

    public static Boolean deleteFile(String fileDirPath) {
        fileDirPath = fileDirPath.replaceAll("^/", "");
        fileDirPath = fileDirPath.replaceAll("/", "\\\\");
        String localDirectory = FileUtil.getLocalDirectory(fileDirPath);
        String userDir = System.getProperty("user.dir") + "\\" + fileDirPath;
        if (localDirectory.equalsIgnoreCase(userDir)) {
            return FileSystemUtils.deleteRecursively(new File(localDirectory));
        } else {
            return FileSystemUtils.deleteRecursively(new File(localDirectory))
                    && FileSystemUtils.deleteRecursively(new File(userDir));
        }
    }
}
