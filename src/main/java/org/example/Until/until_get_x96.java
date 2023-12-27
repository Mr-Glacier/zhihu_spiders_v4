package org.example.Until;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class until_get_x96 {

//    用于调用python程序进行x96的解密工作
//    输入访问的url,以及参数d_c0
//    返回x96参数
    public static String  Make_x96(String url,String d_c0){
        String x96 = "";
        try {
            String[] args1 = new String[]{"D:\\Anconda\\conda3\\envs\\env_2\\python.exe", "D:\\pycharm\\workplace\\pythonProject\\zhihuEncrypt\\test2\\zhihu_x96_jiemi.py", url,d_c0};
            Process pr = Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                x96 = line;
            }
            in.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x96;
    }
}
