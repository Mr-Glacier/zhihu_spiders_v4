package org.example;

import org.example.Controller.Controller_Article;
import org.example.Controller.Controller_Title;
import org.example.Controller.Controller_week_Comments;
import org.example.Until.HelpCreateFile;
import org.example.Until.SentEmail;

public class day_main {
    public static void main(String[] args) {

        String mainPath ="E:\\ZKZD2023\\大V项目\\知乎数据\\DayHot\\";


        //      周日期文件夹
        String DatePath = mainPath+"1228\\";

        String titlePath = DatePath+"Title\\";
        String articlePath = DatePath+"Article\\";
        String commentsPath = DatePath+"Comments\\";

        HelpCreateFile.Method_Creat_folder(DatePath);
        HelpCreateFile.Method_Creat_folder(titlePath);
        HelpCreateFile.Method_Creat_folder(articlePath);
        HelpCreateFile.Method_Creat_folder(commentsPath);


        //Controller_Title controller_title = new Controller_Title();
        String d_c0 = "AFAZa535LxePTo9TKY3y657wQW-nDqZllr0=|1691136542";

        String BeginTitleUrl= "https://www.zhihu.com/api/v4/creators/rank/hot?domain=100009&period=day";
        //controller_title.Method_1_DealUrl(BeginTitleUrl,titlePath,d_c0);


        Controller_Article controller_article = new Controller_Article();

        controller_article.Method_1_readSql(articlePath,d_c0);

        SentEmail sentEmail = new SentEmail();

        sentEmail.Method_SentEmail(0, "dayhot1228_Article --->执行完毕");



//        Controller_Article controller_article = new Controller_Article();
//
//        controller_article.Method_1_getfilesList(articlePath);


//        String day_main_path = "E:\\ZKZD2023\\大V项目\\知乎数据\\DayHot\\1219\\";
//
//        String p1 = day_main_path+"Comments\\";
//        String p2 = day_main_path+"Comments1_2\\";
//        String p3 = day_main_path+"Comments2\\";
//        String p4 = day_main_path+"Comments2_2\\";
//
//        String p5 = day_main_path+"Child_Comments_1\\";
//        String p6 = day_main_path+"Child_Comments_1\\";
//
//
//        Controller_week_Comments controller_week_comments = new Controller_week_Comments();
//
//        controller_week_comments.Method_1_getAllCommentsData(p1);
//        controller_week_comments.Method_1_getAllCommentsData(p2);
//        controller_week_comments.Method_1_getAllCommentsData(p3);
//        controller_week_comments.Method_1_getAllCommentsData(p4);
//
//        controller_week_comments.Method_3_getAllCommentsData_Child(p5);
//        controller_week_comments.Method_3_getAllCommentsData_Child(p6);
//        controller_week_comments.Method_1_getAllCommentsData(p5);
//        controller_week_comments.Method_1_getAllCommentsData(p6);

//        SentEmail sentEmail = new SentEmail();
//
//        sentEmail.Method_SentEmail(0, "dayhot_Comments --->执行完毕");

    }
}
