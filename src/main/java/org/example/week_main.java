package org.example;

import org.example.Controller.Controller_week_Comments;
import org.example.Until.HelpCreateFile;
import org.example.Until.ReadUntil;

import java.util.List;

public class week_main {
    public static void main(String[] args) {

        String mainPath = "E:\\ZKZD2023\\大V项目\\知乎数据\\WeekHot\\";

//        周日期文件夹
        String DatePath = mainPath+"1226\\";

        String titlePath = DatePath+"Title\\";
        String articlePath = DatePath+"Article\\";
        String commentsPath = DatePath+"Comments_1\\";

        HelpCreateFile.Method_Creat_folder(DatePath);
        HelpCreateFile.Method_Creat_folder(titlePath);
        HelpCreateFile.Method_Creat_folder(articlePath);
        HelpCreateFile.Method_Creat_folder(commentsPath);

        Controller_week_Comments controller_week_comments = new Controller_week_Comments();

        String Child_CommentsPath_1 = "E:\\ZKZD2023\\大V项目\\知乎数据\\WeekHot\\1226\\Child_Comments_1\\";
        String Child_CommentsPath_2 = "E:\\ZKZD2023\\大V项目\\知乎数据\\WeekHot\\1226\\Child_Comments_2\\";


        controller_week_comments.Method_3_getAllCommentsData_Child(Child_CommentsPath_1);
        controller_week_comments.Method_3_getAllCommentsData_Child(Child_CommentsPath_2);

//        ReadUntil readUntil = new ReadUntil();
//
//        Controller_week_Comments controller_week_comments = new Controller_week_Comments();
//        List<String> fileList = readUntil.getFileNames(commentsPath);
//        for (String fileName:fileList){
//            System.out.println(fileName);
//            String JSON_comments = readUntil.Method_ReadFile(commentsPath+fileName);
//            controller_week_comments.Method_2_AnalysisComments(JSON_comments,fileName);
//        }
    }
}
