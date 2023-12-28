package org.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.example.Dao.Dao_article;
import org.example.Dao.Dao_title;
import org.example.Entity.Bean_article;
import org.example.Entity.Bean_title;
import org.example.Until.ReadUntil;
import org.example.Until.SaveUntil;
import org.example.Until.until_get_x96;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller_Article {


    private Dao_title dao_title = new Dao_title(0, 4);
    private SaveUntil saveUntil = new SaveUntil();

    public void Method_1_readSql(String path,String d_c0){
        ArrayList<Object> beanlist = dao_title.MethodFind();
        for (int i = 0; i < beanlist.size(); i++) {

            String token  = ((Bean_title)beanlist.get(i)).getToken();

            int C_ID = ((Bean_title)beanlist.get(i)).getC_ID();

            String DownState = ((Bean_title)beanlist.get(i)).getDownState();

            if (DownState.equals("否")){
                System.out.println(token+"\t"+C_ID);
                Method_2_ConllerDown(token,path,d_c0);
                dao_title.Methoc_ChangeDownState(C_ID);
            }

        }
    }


    public void Method_2_ConllerDown(String token,String SavePath,String d_c0){
        String firsturl = "https://www.zhihu.com/api/v4/questions/" +
                token +
                "/feeds?include=data%5B*%5D.is_normal%2Cadmin_closed_comment%2Creward_info%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cattachment%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelevant_info%2Cquestion%2Cexcerpt%2Cis_labeled%2Cpaid_info%2Cpaid_info_content%2Creaction_instruction%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%3Bdata%5B*%5D.mark_infos%5B*%5D.url%3Bdata%5B*%5D.author.follower_count%2Cvip_info%2Cbadge%5B*%5D.topics%3Bdata%5B*%5D.settings.table_of_content.enabled&offset=0&limit=5&order=updated";
        String firstx96 = until_get_x96.Make_x96(firsturl, d_c0);
        String Jsondata_first =  Method_3_RequestData(firsturl,firstx96);
        if (Jsondata_first.equals("Error")){
            do {
                firstx96 = until_get_x96.Make_x96(firsturl, d_c0);
                Jsondata_first =  Method_3_RequestData(firsturl,firstx96);
            }while (Jsondata_first.equals("Error"));
        }

        JSONObject jsonObject = JSONObject.parseObject(Jsondata_first);
        JSONObject pageing = jsonObject.getJSONObject("paging");
        String page = pageing.getString("page");
        saveUntil.Method_SaveFile(SavePath+token+"_"+page+".txt",Jsondata_first);

        Analysis_artcileData(Jsondata_first);

        String is_end = pageing.getString("is_end");
        if (is_end.equals("false")){
            String next_url = pageing.getString("next");
            String x96 = until_get_x96.Make_x96(next_url, d_c0);
            while (true){
                String while_data =  Method_3_RequestData(next_url,x96);
                if (while_data.equals("Error")){
                    do {
                        x96 = until_get_x96.Make_x96(next_url, d_c0);
                        while_data =  Method_3_RequestData(next_url,x96);
                    }while (while_data.equals("Error"));
                }

                JSONObject Item = JSONObject.parseObject(while_data);
                JSONObject Item2 = Item.getJSONObject("paging");
                String while_page = Item2.getString("page");

                saveUntil.Method_SaveFile(SavePath+token+"_"+while_page+".txt",while_data);

                Analysis_artcileData(while_data);

                System.out.println("存储完成一次 :\t"+token+"_"+while_page );
                String while_is_end = Item2.getString("is_end");
                if(while_is_end.equals("true")){
                    break;
                }

                next_url = Item2.getString("next");
            }
        }

        System.out.println("+++++++++++++++++\t 完成一个话题的下载"+token);

    }


    public String Method_3_RequestData(String url, String x96) {
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




    private Dao_article dao_article = new Dao_article(0, 5);
    private ReadUntil readUntil = new ReadUntil();

    public void Method_1_getfilesList(String path){

        List<String> filesList =  readUntil.getFileNames(path);
        for (String fileName : filesList){
            System.out.println("本次解析文件------>\t"+fileName);
            String content = readUntil.Method_ReadFile(path+fileName);
            Analysis_artcileData(content);
        }
    }

    public void Analysis_artcileData(String contentjson){

        JSONObject jsonObject = JSON.parseObject(contentjson);

        JSONArray datalist = jsonObject.getJSONArray("data");

        for (int i = 0; i < datalist.size(); i++) {

            JSONObject onearticle = datalist.getJSONObject(i);

            String type = onearticle.getString("type");
            String target_type = onearticle.getString("target_type");
            String skip_count = onearticle.getString("skip_count");
            String position = onearticle.getString("position");
            String cursor = onearticle.getString("cursor");
            String is_jump_native = onearticle.getString("is_jump_native");


            JSONObject target = onearticle.getJSONObject("target");
            String admin_closed_commen  =  target.getString("admin_closed_commen");
            String annotation_action  =  target.getString("annotation_action");
            String answer_type  =  target.getString("answer_type");
            String attached_info  =  target.getString("attached_info");
            String collapse_reason  =  target.getString("collapse_reason");
            String collapsed_by  =  target.getString("collapsed_by");
//            评论数量
            String comment_count  =  target.getString("comment_count");
            String comment_permission  =  target.getString("comment_permission");
            String content1  =  target.getString("content");

            String decodedHtml = StringEscapeUtils.unescapeHtml4(content1);
            Document document1 = Jsoup.parse(decodedHtml);
            Elements ietm = document1.select("p");
            String content = ietm.text();

            String created_time  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(target.getString("created_time")) * 1000));

            String editable_content  =  target.getString("editable_content");
            String excerpt  =  target.getString("excerpt");
            String extras  =  target.getString("extras");
            String id  =  target.getString("id");
            String is_collapsed  =  target.getString("is_collapsed");
            String is_copyable  =  target.getString("is_copyable");
            String is_jump_native2  =  target.getString("is_jump_native");
            String is_labeled  =  target.getString("is_labeled");
            String is_mine  =  target.getString("is_mine");
            String is_normal  =  target.getString("is_normal");
            String is_sticky  =  target.getString("is_sticky");
            String is_visible  =  target.getString("is_visible");
            String reshipment_settings  =  target.getString("reshipment_settings");
            String sticky_info  =  target.getString("sticky_info");
            String thanks_count  =  target.getString("thanks_count");
            String type2  =  target.getString("type");
            String updated_time  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(target.getString("updated_time")) * 1000));

            String url  =  target.getString("url");
            String visible_only_to_author  =  target.getString("visible_only_to_author");
            String voteup_count  =  target.getString("voteup_count");
            String zhi_plus_extra_info  =  target.getString("zhi_plus_extra_info");
            JSONObject author = target.getJSONObject("author");

            String author_ID = author.getString("id");
            String author_name = author.getString("name");
            String author_url = author.getString("url");
            String author_url_token = author.getString("url_token");
            String user_type = author.getString("user_type");
            String author_follower_count = author.getString("follower_count");

            JSONObject question  = target.getJSONObject("question");

            String question_created = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(question.getString("created")) * 1000));

            String question_id = question.getString("id");
            String question_type = question.getString("question_type");
            String question_title = question.getString("title");
            String question_updated_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(question.getString("updated_time")) * 1000));

            String question_url = question.getString("url");

            Bean_article dayHot_article = new Bean_article();
            dayHot_article.setAdmin_Closed_Commen(admin_closed_commen);
            dayHot_article.setAnnotation_Action(annotation_action);
            dayHot_article.setAnswer_Type(answer_type);
            dayHot_article.setAttached_Info(attached_info);
            dayHot_article.setCollapse_Reason(collapse_reason);
            dayHot_article.setCollapsed_By(collapsed_by);
            dayHot_article.setComment_Count(comment_count);
            dayHot_article.setComment_Permission(comment_permission);


            dayHot_article.setC_Content(content);

            dayHot_article.setCreated_Time(created_time);
            dayHot_article.setEditable_Content(editable_content);
            dayHot_article.setExcerpt(excerpt);
            dayHot_article.setExtras(extras);
            dayHot_article.setId(id);
            dayHot_article.setIs_Collapsed(is_collapsed);
            dayHot_article.setIs_Copyable(is_copyable);
            dayHot_article.setIs_Jump_Native2(is_jump_native2);
            dayHot_article.setIs_Labeled(is_labeled);
            dayHot_article.setIs_Mine(is_mine);
            dayHot_article.setIs_Normal(is_normal);
            dayHot_article.setIs_Sticky(is_sticky);
            dayHot_article.setIs_Visible(is_visible);
            dayHot_article.setReshipment_Settings(reshipment_settings);
            dayHot_article.setSticky_Info(sticky_info);
            dayHot_article.setThanks_Count(thanks_count);
            dayHot_article.setType2(type2);
            dayHot_article.setUpdated_Time(updated_time);
            dayHot_article.setC_Url(url);
            dayHot_article.setVisible_Only_To_Author(visible_only_to_author);
            dayHot_article.setVoteup_Count(voteup_count);
            dayHot_article.setZhi_Plus_Extra_Info(zhi_plus_extra_info);
            dayHot_article.setAuthor_Id(author_ID);
            dayHot_article.setAuthor_Name(author_name);
            dayHot_article.setAuthor_Url(author_url);
            dayHot_article.setAuthor_Url_Token(author_url_token);
            dayHot_article.setUser_Type(user_type);
            dayHot_article.setAuthor_Follower_Count(author_follower_count);
            dayHot_article.setC_Type(type);
            dayHot_article.setTarGet_Type(target_type);
            dayHot_article.setSkip_Count(skip_count);
            dayHot_article.setPosition(position);
            dayHot_article.setC_Cursor(cursor);
            dayHot_article.setIs_Jump_Native(is_jump_native);
            dayHot_article.setQuestion_Created(question_created);
            dayHot_article.setQuestion_Id(question_id);
            dayHot_article.setQuestion_Type(question_type);
            dayHot_article.setQuestion_Title(question_title);
            dayHot_article.setQuestion_Updated_Time(question_updated_time);
            dayHot_article.setQuestion_Url(question_url);
            dayHot_article.setDownState("否");

            dao_article.MethodInsert(dayHot_article);


        }
    }


}
