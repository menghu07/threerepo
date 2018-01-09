package com.apeny.securitymgr;

import sun.security.provider.PolicyFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.Policy;

/**
 * SecurityManager 有显示启用和隐式用两种方式：
 * 显示方式为 在程序中创建SecurityManager类或其子类的对象，并赋值给System.setSecurityManager()
 * 隐式方式为 java com.apeny.securitymgr.SecurityManagerFile -Djava.lang.SecurityManager
 * Created by apeny on 2018年01月09日.
 */
public class SecurityManagerFile {
    public static void main(String[] args) {
//        readFileApplication();
//        readFileApplicationUseSecurityManager();
        readFileAppUseManagerWithPolicy();
    }

    /**
     * 运行命令 java com.apeny.securitymgr.SecurityManagerFile -Djava.lang.SecurityManager
     */
    private static void implicitManager() {
        System.out.println("security manager: " + System.getSecurityManager());
    }

    /**
     * 运行命令 java com.apeny.securitymgr.SecurityManagerFile
     */
    private static void explicitManager() {
        System.setSecurityManager(new SecurityManager());
        System.out.println("explicit manager: " + System.getSecurityManager());
    }

    /**
     * 不使用安全管理器
     */
    private static void readFileApplication() {
        FileInputStream fileInputStream = null;
        try {
            //跟执行路径有关（Working Directory）相对路径时Working Directory 是Miscellaneous项目的根目录，拒绝路径就可以使任何值了
            fileInputStream = new FileInputStream("src/main/resources/config/applicationContext.xml");
            int fileSize = fileInputStream.available();
            System.out.println("application context size: " + fileSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用安全管理器,默认的安全策略文件${JAVA_HOME}/jre/lib/java.policy
     */
    private static void readFileApplicationUseSecurityManager() {
        FileInputStream fileInputStream = null;
        try {
            System.setSecurityManager(new SecurityManager());
            //跟执行路径有关（Working Directory）相对路径时Working Directory 是Miscellaneous项目的根目录，拒绝路径就可以使任何值了
            fileInputStream = new FileInputStream("src/main/resources/config/applicationContext.xml");
            int fileSize = fileInputStream.available();
            System.out.println("application context size: " + fileSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用安全管理器,使用策略文件src/main/resources/policies/protect.policy,在程序中加载Policy文件
     */
    private static void readFileAppUseManagerWithPolicy() {
        FileInputStream fileInputStream = null;
        try {
            //PolicyFile 创建
            URL url = new URL("file:F:\\ideaworkspace\\LocalProject\\gitrepository\\newthreerepo\\threerepo"
                    + "\\threerepo\\Miscellaneous\\src\\main\\resources\\policies\\protect.policy");
            //必须先设置策略文件,再启动安全管理器
            PolicyFile policyFile = new PolicyFile(url);
            Policy.setPolicy(policyFile);
            System.setSecurityManager(new SecurityManager());
            //跟执行路径有关（Working Directory）相对路径时Working Directory 是Miscellaneous项目的根目录，拒绝路径就可以使任何值了
            fileInputStream = new FileInputStream("src/main/resources/config/applicationContext.xml");
            int fileSize = fileInputStream.available();
            System.out.println("application context size: " + fileSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
