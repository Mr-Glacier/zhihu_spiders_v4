package org.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.Dao.Dao_title;
import org.example.Entity.Bean_title;
import org.example.Until.ReadUntil;
import org.example.Until.SaveUntil;
import org.example.Until.until_get_x96;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Controller_Title {


    private SaveUntil saveUntil = new SaveUntil();

    //private String d_c0 = "ANAVq1233hePTvVxMOP93fo4nJBLgZeL3-U=|1702863227";

    public void Method_1_DealUrl(String Beginurl, String savePath,String d_c0) {
        String Begin_x96 = until_get_x96.Make_x96(Beginurl,d_c0);
        String Begin_Content = Method_2_RequestData(Beginurl, Begin_x96);
        if (Begin_Content.equals("Error")){
            do {
                System.out.println("第一次访问出错");
                Begin_x96 = until_get_x96.Make_x96(Beginurl, d_c0);
                Begin_Content = Method_2_RequestData(Beginurl, Begin_x96);
            } while (Begin_Content.equals("Error"));
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String Down_time = format.format(date);

        saveUntil.Method_SaveFile(savePath + "Title_" + Down_time + ".txt", Begin_Content);
        System.out.println("完成存储 :\t" + "Title_" + Down_time + ".txt");

        Method_4_AnalysisOneTitleJSON(Begin_Content);


        JSONObject Item1 = JSON.parseObject(Begin_Content);
        JSONObject paging_Item = Item1.getJSONObject("paging");

        String is_end = paging_Item.getString("is_end");

        if (is_end.equals("false")) {
            String request_url = "https://www.zhihu.com/api/v4" + paging_Item.getString("next");
            String request_x96 = until_get_x96.Make_x96(request_url, d_c0);
            while (true) {

                String request_content = Method_2_RequestData(request_url, request_x96);
                if (request_content.equals("Error")) {
                    do {
                        request_content = Method_2_RequestData(request_url, request_x96);
                    } while (request_content.equals("Error"));
                }
                Date date2 = new Date();
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
                String Down_time2 = format2.format(date2);
                saveUntil.Method_SaveFile(savePath + "Title_" + Down_time2 + ".txt", request_content);
                Method_4_AnalysisOneTitleJSON(request_content);

                System.out.println("完成存储 :\t" + "Title_" + Down_time2 + ".txt");
                JSONObject Item2 = JSON.parseObject(request_content);
                JSONObject paging_Item2 = Item2.getJSONObject("paging");

                String is_end2 = paging_Item2.getString("is_end");
                if (is_end2.equals("true")) {
                    break;
                }
                request_url = "https://www.zhihu.com/api/v4" + paging_Item2.getString("next");
                request_x96 = until_get_x96.Make_x96(request_url, d_c0);

            }

        }

    }


    //    使用Jsoup发送请求的方法
    public String Method_2_RequestData(String url, String x96) {
        String resultJson = "Error";
        String Cookie = "_zap=bc01297a-0fdb-49ee-a9c1-0c19b67b21a5; d_c0=AFAZa535LxePTo9TKY3y657wQW-nDqZllr0=|1691136542; __snaker__id=9g1alyosN6oMQcsW; YD00517437729195%3AWM_TID=Wt%2Bqla8oyZJEUUAFFFOA0sq7hi7mHdS7; YD00517437729195%3AWM_NI=ZY71WCcWipZFzFv2nH%2BCr5uR1LpKcxZBZvfFyULiL0wcduZUZhGi2WRe%2FAk5Jej886JnOIVCsJE%2Fd74941l2PdZQfwy2FB3mqQutw1iAUKQGymt841UlP7CDQ3%2B6KzbZaDU%3D; YD00517437729195%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eea4bb60b2a7be86f274b89a8bb3c15e978a8badd53f9c99b895e47ba3e981d9c22af0fea7c3b92a9a87f9d2d649a189a791d94eba9abad3ed44e99dbed6f85af394c0ace2749a9e9697e54ebb8b848acf3cb5bdac96d95f9ab8898ab15d92bbe1a2ea3f8abba6d9bb3e8ab281aad549b1968bd0e73df8e7fcd2ef3a94bc96b2e57dbbee8aa3b267b38fbb95f63bab9f9bacd44dbbb3bcd9b73b97b4a0d8c25093ede5d7ca72a286afa5d837e2a3; q_c1=a8619ad0cf064071809c1b6cb9be237e|1702095751000|1702095751000; _xsrf=f271949c-7ba0-4fc7-b430-efa32806a984; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1702678831,1702913441,1702982398,1703058018; BAIDU_SSP_lcr=https://www.google.com/; z_c0=2|1:0|10:1703217655|4:z_c0|92:Mi4xSWdQYkNRQUFBQUFBVUJscm5ma3ZGeVlBQUFCZ0FsVk45MWR5WmdBelI4SnVOTzU3aENaNW9MTm1CQ1I0NmFVcXVR|5e2ff88e9fe4ee26000607533a16201ae62e557d9d863b3299a05206b214990f; gdxidpyhxdE=dJVNbO4hgUTrLuXcbfkNOelZpQLZeKXcILZPAJ9zrkpVOf%2BEV%5CUQBPYkU5hOgvz72GUtxLGj9Hbs%2F676Wfx3QhlqcbAijSzMJycquoD8f7L%2F8Dq6eAiMu%5CUiao284EY9TZIg%2B%2BwGC%2BK7ivCplr1rZMZeSt0DHjWcp9BNqryipRbqh2bL%3A1703526471864; SESSIONID=Ov6bLEkFSB8lFoSQ2AvcokUi0RIDAOQU7sQyita13FF; JOID=W1gcCkl3pCMengOscXdWdCcK-oFmMdsVd9lPzjcv7xJy02f2KfyRonucA6V6Q8IjOyIx9xUITP258jfWlmfs-M8=; osd=V18SBE17oy0Qmg-rf3lSeCAE9IVqNtUbc9VIwDkr4xV83WP6LvKfpnebDat-T8UtNSY98BsGSPG-_DnSmmDi9ss=; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1703645300; tst=r; KLBRSID=37f2e85292ebb2c2ef70f1d8e39c2b34|1703645355|1703644274";
        try {
//            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("X-Requested-With", "fetch")
                    .header("Sec-Ch-Ua-Platform", "Windows")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("X-Requested-With", "fetch")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("X-Zse-93", "101_3_3.0")
                    .header("X-Zse-96", x96)
                    .header("Cookie", Cookie)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true).execute();
            resultJson = res.body();
            Thread.sleep(2080);
        } catch (Exception e) {
            e.printStackTrace();
            return resultJson;
        }
        return resultJson;
    }

    private ReadUntil readUntil = new ReadUntil();

    private  Dao_title dao_title_day = new Dao_title(0,4);

    //    解析全部话题入库
    public void Method_3_AnalysisTitles(String titleSavePath){
        List<String> filesname =  readUntil.getFileNames(titleSavePath);
        for (String file:filesname){
            System.out.println(file);
            String content = readUntil.Method_ReadFile(titleSavePath+file);
            Method_4_AnalysisOneTitleJSON(content);
        }
    }


    public void Method_4_AnalysisOneTitleJSON(String JSONContent){
        JSONObject jsonObject = JSON.parseObject(JSONContent);

        JSONArray dataArry = jsonObject.getJSONArray("data");

        for (int i = 0; i < dataArry.size(); i++) {

            JSONObject onetitle = dataArry.getJSONObject(i);

            JSONObject question = onetitle.getJSONObject("question");

            String url=question.getString("url");

            String created1=question.getString("created");
            Long Ctime = Long.valueOf(created1);
            String created = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Ctime * 1000));


            String updated_time1=question.getString("updated_time");
            Long Utime = Long.valueOf(updated_time1);
            String updated_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Utime * 1000));

            String title=question.getString("title");
            String type=question.getString("type");
            String id=question.getString("id");
            String token=question.getString("token");
            String is_recent_hot=question.getString("is_recent_hot");
            String label=question.getString("label");


            JSONObject reaction = onetitle.getJSONObject("reaction");

            String new_pv=reaction.getString("new_pv");
            String new_pv_7_days=reaction.getString("new_pv_7_days");
            String new_follow_num=reaction.getString("new_follow_num");
            String new_follow_num_7_days=reaction.getString("new_follow_num_7_days");
            String new_answer_num=reaction.getString("new_answer_num");
            String new_answer_num_7_days=reaction.getString("new_answer_num_7_days");
            String new_upvote_num=reaction.getString("new_upvote_num");
            String new_upvote_num_7_days=reaction.getString("new_upvote_num_7_days");
            String pv=reaction.getString("pv");
            String follow_num=reaction.getString("follow_num");
            String answer_num=reaction.getString("answer_num");
            String upvote_num=reaction.getString("upvote_num");
            String pv_incr_rate=reaction.getString("pv_incr_rate");
            String head_percent=reaction.getString("head_percent");
            String new_pv_yesterday=reaction.getString("new_pv_yesterday");
            String new_pv_t_yesterday=reaction.getString("new_pv_t_yesterday");
            String score=reaction.getString("score");
            String score_level=reaction.getString("score_level");
            String text=reaction.getString("text");

            Bean_title bean_title = new Bean_title();
            bean_title.setUrl(url );
            bean_title.setCreated(created );
            bean_title.setUpdated_Time(updated_time );
            bean_title.setTitle(title );
            bean_title.setType(type);
            bean_title.setId(id );
            bean_title.setToken(token );
            bean_title.setIs_Recent_Hot(is_recent_hot );
            bean_title.setLabel(label );
            bean_title.setNew_Pv(new_pv);
            bean_title.setNew_Pv_7_Days(new_pv_7_days);
            bean_title.setNew_Follow_Num(new_follow_num);
            bean_title.setNew_Follow_Num_7_Days(new_follow_num_7_days);
            bean_title.setNew_Answer_Num(new_answer_num);
            bean_title.setNew_Answer_Num_7_Days(new_answer_num_7_days);
            bean_title.setNew_Upvote_Num(new_upvote_num);
            bean_title.setNew_Upvote_Num_7_Days(new_upvote_num_7_days);
            bean_title.setPv(pv);
            bean_title.setFollow_Num(follow_num);
            bean_title.setAnswer_Num(answer_num);
            bean_title.setUpvote_Num(upvote_num);
            bean_title.setPv_Incr_Rate(pv_incr_rate);
            bean_title.setHead_Percent(head_percent);
            bean_title.setNew_Pv_Yesterday(new_pv_yesterday);
            bean_title.setNew_Pv_T_Yesterday(new_pv_t_yesterday);
            bean_title.setScore(score);
            bean_title.setScore_Level(score_level);
            bean_title.setText(text);
            dao_title_day.MethodInsert(bean_title);



        }
    }

}
