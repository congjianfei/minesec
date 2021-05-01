package com.minesec.service.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
//D:\MyWorkCode\JavaWeb\MineSec\src\main\resources\ssl\ca.crt
public class Mineutils {
//    public static final String PKIROOTPATH = "/home/lc/code/demospring/src/main/resources/ssl/";
    public static final String PKIROOTPATH = "D:\\MyWorkCode\\JavaWeb\\MineSec\\src\\main\\resources\\ssl\\";//todo 上线的时候需要更换
    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean genCertWithFile(String csr_path, String cert_path_dst) {
        boolean res = true;
        String cmd = String.format("openssl x509 -req -CA %s -CAkey %s -CAcreateserial -in %s -out %s",
                PKIROOTPATH + "ca.crt", PKIROOTPATH + "ca.key", csr_path, cert_path_dst);
//        String cmd = "ls -l";
        System.out.println("Run cmd: " + cmd);
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
            BufferedReader stdInKey = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            String s = null;

            while ((s = stdInKey.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("Exec result: " + (ps.exitValue()==0 ? "success!" : "failed!") );
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    public static String genCertWithStream(String csr) {
        String csr_path = PKIROOTPATH + "out"+ File.separator+"client-sdk.csr";
        String cert_path = PKIROOTPATH + "out"+File.separator+"client-sdk.crt";
        try {
            IOUtils.writeFile(csr, csr_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String res = null;
        if(!genCertWithFile(csr_path, cert_path)) return res;
        try {
            res = IOUtils.readFile(cert_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
