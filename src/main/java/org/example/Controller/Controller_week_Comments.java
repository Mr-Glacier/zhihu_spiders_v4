package org.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.example.Dao.Dao_comments;
import org.example.Entity.Bean_comments;
import org.example.Until.ReadUntil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//逻辑层,用于下载解析,周评论
public class Controller_week_Comments {
//100419996_1
     Dao_comments dao_comments = new Dao_comments(1, 2);
    private ReadUntil readUntil = new ReadUntil();

//解析一级评论
    public void Method_1_getAllCommentsData(String path){
        List<String> fileList = readUntil.getFileNames(path);
        for (String fileName:fileList){
            System.out.println(fileName);
            String JSON_comments = readUntil.Method_ReadFile(path+fileName);
            Method_2_AnalysisComments(JSON_comments,fileName);
        }
    }
    public void Method_2_AnalysisComments(String contentJson,String filename) {


        if (!contentJson.equals("Error")) {
            JSONObject jsonObject = JSON.parseObject(contentJson);

            JSONObject page = jsonObject.getJSONObject("paging");

            String totals = page.getString("totals");

            if (!totals.equals("0")) {
                String str = page.getString("previous").replace("https://www.zhihu.com/api/v4/comment_v5/answers/", "");

                String ArticleID = str.substring(0, str.indexOf("/root_comment?"));

                JSONArray dataList = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataList.size(); i++) {

                    JSONObject item = dataList.getJSONObject(i);

                    String id_comment = item.getString("id");
                    String type_comment = item.getString("type");
                    String resource_type = item.getString("resource_type");
                    String member_id = item.getString("member_id");
                    String url_comment = item.getString("url");
                    String hot_comment = item.getString("hot");
                    String top_comment = item.getString("top");


//            String decodedHtml = StringEscapeUtils.unescapeHtml4(item.getString("content"));
//            Document document1 = Jsoup.parse(decodedHtml);
//            Elements ietm = document1.select("p");
                    String content_comment = item.getString("content").replace("<br>", "");


//            1013909553_1

                    boolean status = content_comment.contains("<p>");
                    if (status) {
                        String decodedHtml = StringEscapeUtils.unescapeHtml4(content_comment);
                        Document document1 = Jsoup.parse(decodedHtml);
                        Elements html1 = document1.select("p");
                        content_comment = html1.text();

//                System.out.println(content_comment);
                    }


                    String score_comment = item.getString("scoret");
                    String created_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(item.getString("created_time")) * 1000));

                    String is_delete = item.getString("is_delete");
                    String collapsed = item.getString("collapsed");
                    String reviewing = item.getString("reviewing");
                    String reply_comment_id = item.getString("reply_comment_id");
                    String reply_root_comment_id = item.getString("reply_root_comment_id");

                    String liked = item.getString("liked");
                    String like_count = item.getString("like_count");
                    String disliked = item.getString("disliked");
                    String dislike_count = item.getString("dislike_count");
                    String is_author = item.getString("is_author");
                    String can_like = item.getString("can_like");
                    String can_dislike = item.getString("can_dislike");
                    String can_delete = item.getString("can_delete");
                    String can_reply = item.getString("can_reply");
                    String can_hot = item.getString("can_hot");
                    String can_author_top = item.getString("can_author_top");
                    String is_author_top = item.getString("is_author_top");
                    String can_collapse = item.getString("can_collapse");
                    String can_share = item.getString("can_share");
                    String can_unfold = item.getString("can_unfold");
                    String can_truncate = item.getString("can_truncate");
                    String can_more = item.getString("can_more");
                    String child_comment_count = item.getString("child_comment_count");
                    String is_visible_only_to_myself = item.getString("is_visible_only_to_myself");


                    JSONObject author = item.getJSONObject("author");

                    String id_author = author.getString("id");
                    String url_token = author.getString("url_token");
                    String name = author.getString("name");
                    String avatar_url = author.getString("avatar_url");
                    String avatar_url_template = author.getString("avatar_url_template");
                    String is_org = author.getString("is_org");
                    String type_author = author.getString("type");
                    String url_author = author.getString("url");
                    String user_type = author.getString("user_type");
                    String headline = author.getString("headline");
                    String gender = author.getString("gender");
                    String is_advertiser = author.getString("is_advertiser");
                    String level_info = author.getString("level_info");
                    String is_anonymous = author.getString("is_anonymous");


                    JSONArray comment_tag = item.getJSONArray("comment_tag");

                    String type_ip = "-";
                    String text_ip = "-";
                    String color_ip = "-";
                    String night_color_ip = "-";
                    String has_border_ip = "-";
                    if (comment_tag.size() != 0) {
                        JSONObject comment_tag_data = comment_tag.getJSONObject(0);
                        type_ip = comment_tag_data.getString("type");
                        text_ip = comment_tag_data.getString("text");
                        color_ip = comment_tag_data.getString("color");
                        night_color_ip = comment_tag_data.getString("night_color");
                        has_border_ip = comment_tag_data.getString("has_border");
                    }
                    String FatherID = "0000";
                    String Comments_leave = "1";

                    Bean_comments dayHot_comments_1 = new Bean_comments();

                    dayHot_comments_1.setFatherID(FatherID);
                    dayHot_comments_1.setComments_leave(Comments_leave);
                    dayHot_comments_1.setId_Comment(id_comment);
                    dayHot_comments_1.setType_Comment(type_comment);
                    dayHot_comments_1.setResource_Type(resource_type);
                    dayHot_comments_1.setMember_Id(member_id);
                    dayHot_comments_1.setUrl_Comment(url_comment);
                    dayHot_comments_1.setHot_Comment(hot_comment);
                    dayHot_comments_1.setTop_Comment(top_comment);
                    dayHot_comments_1.setContent_Comment(content_comment);
                    dayHot_comments_1.setScore_Comment(score_comment);
                    dayHot_comments_1.setCreated_Time(created_time);
                    dayHot_comments_1.setIs_Delete(is_delete);
                    dayHot_comments_1.setCollapsed(collapsed);
                    dayHot_comments_1.setReviewing(reviewing);
                    dayHot_comments_1.setReply_Comment_Id(reply_comment_id);
                    dayHot_comments_1.setReply_Root_Comment_Id(reply_root_comment_id);
                    dayHot_comments_1.setLiked(liked);
                    dayHot_comments_1.setLike_Count(like_count);
                    dayHot_comments_1.setDisliked(disliked);
                    dayHot_comments_1.setDislike_Count(dislike_count);
                    dayHot_comments_1.setIs_Author(is_author);
                    dayHot_comments_1.setCan_Like(can_like);
                    dayHot_comments_1.setCan_Dislike(can_dislike);
                    dayHot_comments_1.setCan_Delete(can_delete);
                    dayHot_comments_1.setCan_Reply(can_reply);
                    dayHot_comments_1.setCan_Hot(can_hot);
                    dayHot_comments_1.setCan_Author_Top(can_author_top);
                    dayHot_comments_1.setIs_Author_Top(is_author_top);
                    dayHot_comments_1.setCan_Collapse(can_collapse);
                    dayHot_comments_1.setCan_Share(can_share);
                    dayHot_comments_1.setCan_Unfold(can_unfold);
                    dayHot_comments_1.setCan_Truncate(can_truncate);
                    dayHot_comments_1.setCan_More(can_more);
                    dayHot_comments_1.setChild_Comment_Count(child_comment_count);
                    dayHot_comments_1.setIs_Visible_Only_To_Myself(is_visible_only_to_myself);

                    dayHot_comments_1.setId_Author(id_author);
                    dayHot_comments_1.setUrl_Token(url_token);
                    dayHot_comments_1.setName_Author(name);
                    dayHot_comments_1.setAvatar_Url(avatar_url);
                    dayHot_comments_1.setAvatar_Url_Template(avatar_url_template);
                    dayHot_comments_1.setIs_Org(is_org);
                    dayHot_comments_1.setType_Author(type_author);
                    dayHot_comments_1.setUrl_Author(url_author);
                    dayHot_comments_1.setUser_Type(user_type);
                    dayHot_comments_1.setHeadline(headline);
                    dayHot_comments_1.setGender(gender);
                    dayHot_comments_1.setIs_Advertiser(is_advertiser);
                    dayHot_comments_1.setLevel_Info(level_info);
                    dayHot_comments_1.setIs_Anonymous(is_anonymous);

                    dayHot_comments_1.setType_Ip(type_ip);
                    dayHot_comments_1.setText_Ip(text_ip);
                    dayHot_comments_1.setColor_Ip(color_ip);
                    dayHot_comments_1.setNight_Color_Ip(night_color_ip);
                    dayHot_comments_1.setHas_Border_Ip(has_border_ip);

                    dayHot_comments_1.setArticleID(ArticleID);

                    dao_comments.MethodInsert(dayHot_comments_1);


                    if (child_comment_count.equals("1") || child_comment_count.equals("2")) {
                        JSONArray child_comments = item.getJSONArray("child_comments");
                        for (int j = 0; j < child_comments.size(); j++) {
                            JSONObject child = child_comments.getJSONObject(j);
                            String id_comment_child = child.getString("id");
                            String type_comment_child = child.getString("type");
                            String resource_type_child = child.getString("resource_type");
                            String member_id_child = child.getString("member_id");
                            String url_comment_child = child.getString("url");
                            String hot_comment_child = child.getString("hot");
                            String top_comment_child = child.getString("top");

//                        String decodedHtml2 = StringEscapeUtils.unescapeHtml4(child.getString("content"));
//                        Document document12 = Jsoup.parse(decodedHtml2);
//                        Elements ietm2 = document12.select("p");
                            String content_comment_child = child.getString("content").replace("<br>", "");


                            boolean status2 = content_comment_child.contains("<p>");
                            if (status2) {
                                String decodedHtml = StringEscapeUtils.unescapeHtml4(content_comment_child);
                                Document document2 = Jsoup.parse(decodedHtml);
                                Elements html1 = document2.select("p");
                                content_comment_child = html1.text();

//                            System.out.println(content_comment_child);
                            }


                            String score_comment_child = child.getString("score");
                            String created_time_child = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(child.getString("created_time")) * 1000));

                            String is_delete_child = child.getString("is_delete");
                            String collapsed_child = child.getString("collapsed");
                            String reviewing_child = child.getString("reviewing");
                            String reply_comment_id_child = child.getString("reply_comment_id");
                            String reply_root_comment_id_child = child.getString("reply_root_comment_id");
                            String liked_child = child.getString("liked");
                            String like_count_child = child.getString("like_count");
                            String disliked_child = child.getString("disliked");
                            String dislike_count_child = child.getString("dislike_count");
                            String is_author_child = child.getString("is_author");
                            String can_like_child = child.getString("can_like");
                            String can_dislike_child = child.getString("can_dislike");
                            String can_delete_child = child.getString("can_delete");
                            String can_reply_child = child.getString("can_reply");
                            String can_hot_child = child.getString("can_hot");
                            String can_author_top_child = child.getString("can_author_top");
                            String is_author_top_child = child.getString("is_author_top");
                            String can_collapse_child = child.getString("can_collapse");
                            String can_share_child = child.getString("can_share");
                            String can_unfold_child = child.getString("can_unfold");
                            String can_truncate_child = child.getString("can_truncate");
                            String can_more_child = child.getString("can_more");
                            String child_comment_count_child = child.getString("child_comment_count");
                            String is_visible_only_to_myself_child = child.getString("is_visible_only_to_myself");


                            JSONObject author_child = child.getJSONObject("author");

                            String id_author_child = author_child.getString("id");
                            String url_token_child = author_child.getString("url_token");
                            String name_child = author_child.getString("name");
                            String avatar_url_child = author_child.getString("avatar_url");
                            String avatar_url_template_child = author_child.getString("avatar_url_template");
                            String is_org_child = author_child.getString("is_org");
                            String type_author_child = author_child.getString("type");
                            String url_author_child = author_child.getString("url");
                            String user_type_child = author_child.getString("user_type");
                            String headline_child = author_child.getString("headline");
                            String gender_child = author_child.getString("gender");
                            String is_advertiser_child = author_child.getString("is_advertiser");
                            String level_info_child = author_child.getString("level_info");
                            String is_anonymous_child = author_child.getString("is_anonymous");


                            JSONArray cococo = child.getJSONArray("comment_tag");
                            String type_ip_child = "-";
                            String text_ip_child = "-";
                            String color_ip_child = "-";
                            String night_color_ip_child = "-";
                            String has_border_ip_child = "-";
                            if (cococo.size() != 0) {

                                JSONObject comment_tag_child = cococo.getJSONObject(0);

                                type_ip_child = comment_tag_child.getString("type");
                                text_ip_child = comment_tag_child.getString("text");
                                color_ip_child = comment_tag_child.getString("color");
                                night_color_ip_child = comment_tag_child.getString("night_color");
                                has_border_ip_child = comment_tag_child.getString("has_border");
                            }
                            String FatherID_child = id_comment;
                            String Comments_leave_child = "2";

                            Bean_comments dayHot_comments_2 = new Bean_comments();

                            dayHot_comments_2.setFatherID(FatherID_child);
                            dayHot_comments_2.setComments_leave(Comments_leave_child);

                            dayHot_comments_2.setFatherID(id_comment);
                            dayHot_comments_2.setComments_leave(Comments_leave_child);
                            dayHot_comments_2.setId_Comment(id_comment_child);
                            dayHot_comments_2.setType_Comment(type_comment_child);
                            dayHot_comments_2.setResource_Type(resource_type_child);
                            dayHot_comments_2.setMember_Id(member_id_child);
                            dayHot_comments_2.setUrl_Comment(url_comment_child);
                            dayHot_comments_2.setHot_Comment(hot_comment_child);
                            dayHot_comments_2.setTop_Comment(top_comment_child);
                            dayHot_comments_2.setContent_Comment(content_comment_child);
                            dayHot_comments_2.setScore_Comment(score_comment_child);
                            dayHot_comments_2.setCreated_Time(created_time_child);
                            dayHot_comments_2.setIs_Delete(is_delete_child);
                            dayHot_comments_2.setCollapsed(collapsed_child);
                            dayHot_comments_2.setReviewing(reviewing_child);
                            dayHot_comments_2.setReply_Comment_Id(reply_comment_id_child);
                            dayHot_comments_2.setReply_Root_Comment_Id(reply_root_comment_id_child);
                            dayHot_comments_2.setLiked(liked_child);
                            dayHot_comments_2.setLike_Count(like_count_child);
                            dayHot_comments_2.setDisliked(disliked_child);
                            dayHot_comments_2.setDislike_Count(dislike_count_child);
                            dayHot_comments_2.setIs_Author(is_author_child);
                            dayHot_comments_2.setCan_Like(can_like_child);
                            dayHot_comments_2.setCan_Dislike(can_dislike_child);
                            dayHot_comments_2.setCan_Delete(can_delete_child);
                            dayHot_comments_2.setCan_Reply(can_reply_child);
                            dayHot_comments_2.setCan_Hot(can_hot_child);
                            dayHot_comments_2.setCan_Author_Top(can_author_top_child);
                            dayHot_comments_2.setIs_Author_Top(is_author_top_child);
                            dayHot_comments_2.setCan_Collapse(can_collapse_child);
                            dayHot_comments_2.setCan_Share(can_share_child);
                            dayHot_comments_2.setCan_Unfold(can_unfold_child);
                            dayHot_comments_2.setCan_Truncate(can_truncate_child);
                            dayHot_comments_2.setCan_More(can_more_child);
                            dayHot_comments_2.setChild_Comment_Count(child_comment_count_child);
                            dayHot_comments_2.setIs_Visible_Only_To_Myself(is_visible_only_to_myself_child);

                            dayHot_comments_2.setId_Author(id_author_child);
                            dayHot_comments_2.setUrl_Token(url_token_child);
                            dayHot_comments_2.setName_Author(name_child);
                            dayHot_comments_2.setAvatar_Url(avatar_url_child);
                            dayHot_comments_2.setAvatar_Url_Template(avatar_url_template_child);
                            dayHot_comments_2.setIs_Org(is_org_child);
                            dayHot_comments_2.setType_Author(type_author_child);
                            dayHot_comments_2.setUrl_Author(url_author_child);
                            dayHot_comments_2.setUser_Type(user_type_child);
                            dayHot_comments_2.setHeadline(headline_child);
                            dayHot_comments_2.setGender(gender_child);
                            dayHot_comments_2.setIs_Advertiser(is_advertiser_child);
                            dayHot_comments_2.setLevel_Info(level_info_child);
                            dayHot_comments_2.setIs_Anonymous(is_anonymous_child);

                            dayHot_comments_2.setType_Ip(type_ip_child);
                            dayHot_comments_2.setText_Ip(text_ip_child);
                            dayHot_comments_2.setColor_Ip(color_ip_child);
                            dayHot_comments_2.setNight_Color_Ip(night_color_ip_child);
                            dayHot_comments_2.setHas_Border_Ip(has_border_ip_child);

                            dayHot_comments_2.setArticleID(ArticleID);
                            dao_comments.MethodInsert(dayHot_comments_2);

                        }
                    }

                }
            }
        }
    }


//解析二级评论
    public void Method_3_getAllCommentsData_Child(String path){
        List<String> fileList = readUntil.getFileNames(path);
        for (String fileName:fileList){
            System.out.println(fileName);
            String JSON_comments = readUntil.Method_ReadFile(path+fileName);
            Method_4_AnalysisComments_Child(JSON_comments,fileName);
        }
    }

    public void Method_4_AnalysisComments_Child(String contentJson,String filename) {


        if (!contentJson.equals("Error")) {
            JSONObject jsonObject = JSON.parseObject(contentJson);

            JSONObject page = jsonObject.getJSONObject("paging");

            String totals = page.getString("totals");

            if (!totals.equals("0")) {
                String str = page.getString("previous").replace("https://www.zhihu.com/api/v4/comment_v5/comment/", "");

                //https://www.zhihu.com/api/v4/comment_v5/comment/10718365225/child_comment?limit=20&offset=1698884586_10719200084_0&order_by=ts
                String FatherID = str.substring(0, str.indexOf("/child_comment?"));

                String ArticleID = "---";

                JSONArray dataList = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataList.size(); i++) {

                    JSONObject item = dataList.getJSONObject(i);

                    String id_comment = item.getString("id");
                    String type_comment = item.getString("type");
                    String resource_type = item.getString("resource_type");
                    String member_id = item.getString("member_id");
                    String url_comment = item.getString("url");
                    String hot_comment = item.getString("hot");
                    String top_comment = item.getString("top");


//            String decodedHtml = StringEscapeUtils.unescapeHtml4(item.getString("content"));
//            Document document1 = Jsoup.parse(decodedHtml);
//            Elements ietm = document1.select("p");
                    String content_comment = item.getString("content").replace("<br>", "");


//            1013909553_1

                    boolean status = content_comment.contains("<p>");
                    if (status) {
                        String decodedHtml = StringEscapeUtils.unescapeHtml4(content_comment);
                        Document document1 = Jsoup.parse(decodedHtml);
                        Elements html1 = document1.select("p");
                        content_comment = html1.text();

//                System.out.println(content_comment);
                    }


                    String score_comment = item.getString("scoret");
                    String created_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(item.getString("created_time")) * 1000));

                    String is_delete = item.getString("is_delete");
                    String collapsed = item.getString("collapsed");
                    String reviewing = item.getString("reviewing");
                    String reply_comment_id = item.getString("reply_comment_id");
                    String reply_root_comment_id = item.getString("reply_root_comment_id");

                    String liked = item.getString("liked");
                    String like_count = item.getString("like_count");
                    String disliked = item.getString("disliked");
                    String dislike_count = item.getString("dislike_count");
                    String is_author = item.getString("is_author");
                    String can_like = item.getString("can_like");
                    String can_dislike = item.getString("can_dislike");
                    String can_delete = item.getString("can_delete");
                    String can_reply = item.getString("can_reply");
                    String can_hot = item.getString("can_hot");
                    String can_author_top = item.getString("can_author_top");
                    String is_author_top = item.getString("is_author_top");
                    String can_collapse = item.getString("can_collapse");
                    String can_share = item.getString("can_share");
                    String can_unfold = item.getString("can_unfold");
                    String can_truncate = item.getString("can_truncate");
                    String can_more = item.getString("can_more");
                    String child_comment_count = item.getString("child_comment_count");
                    String is_visible_only_to_myself = item.getString("is_visible_only_to_myself");


                    JSONObject author = item.getJSONObject("author");

                    String id_author = author.getString("id");
                    String url_token = author.getString("url_token");
                    String name = author.getString("name");
                    String avatar_url = author.getString("avatar_url");
                    String avatar_url_template = author.getString("avatar_url_template");
                    String is_org = author.getString("is_org");
                    String type_author = author.getString("type");
                    String url_author = author.getString("url");
                    String user_type = author.getString("user_type");
                    String headline = author.getString("headline");
                    String gender = author.getString("gender");
                    String is_advertiser = author.getString("is_advertiser");
                    String level_info = author.getString("level_info");
                    String is_anonymous = author.getString("is_anonymous");


                    JSONArray comment_tag = item.getJSONArray("comment_tag");

                    String type_ip = "-";
                    String text_ip = "-";
                    String color_ip = "-";
                    String night_color_ip = "-";
                    String has_border_ip = "-";
                    if (comment_tag.size() != 0) {
                        JSONObject comment_tag_data = comment_tag.getJSONObject(0);
                        type_ip = comment_tag_data.getString("type");
                        text_ip = comment_tag_data.getString("text");
                        color_ip = comment_tag_data.getString("color");
                        night_color_ip = comment_tag_data.getString("night_color");
                        has_border_ip = comment_tag_data.getString("has_border");
                    }
//                    String FatherID = "0000";
                    String Comments_leave = "2";

                    Bean_comments dayHot_comments_1 = new Bean_comments();

                    dayHot_comments_1.setFatherID(FatherID);
                    dayHot_comments_1.setComments_leave(Comments_leave);
                    dayHot_comments_1.setId_Comment(id_comment);
                    dayHot_comments_1.setType_Comment(type_comment);
                    dayHot_comments_1.setResource_Type(resource_type);
                    dayHot_comments_1.setMember_Id(member_id);
                    dayHot_comments_1.setUrl_Comment(url_comment);
                    dayHot_comments_1.setHot_Comment(hot_comment);
                    dayHot_comments_1.setTop_Comment(top_comment);
                    dayHot_comments_1.setContent_Comment(content_comment);
                    dayHot_comments_1.setScore_Comment(score_comment);
                    dayHot_comments_1.setCreated_Time(created_time);
                    dayHot_comments_1.setIs_Delete(is_delete);
                    dayHot_comments_1.setCollapsed(collapsed);
                    dayHot_comments_1.setReviewing(reviewing);
                    dayHot_comments_1.setReply_Comment_Id(reply_comment_id);
                    dayHot_comments_1.setReply_Root_Comment_Id(reply_root_comment_id);
                    dayHot_comments_1.setLiked(liked);
                    dayHot_comments_1.setLike_Count(like_count);
                    dayHot_comments_1.setDisliked(disliked);
                    dayHot_comments_1.setDislike_Count(dislike_count);
                    dayHot_comments_1.setIs_Author(is_author);
                    dayHot_comments_1.setCan_Like(can_like);
                    dayHot_comments_1.setCan_Dislike(can_dislike);
                    dayHot_comments_1.setCan_Delete(can_delete);
                    dayHot_comments_1.setCan_Reply(can_reply);
                    dayHot_comments_1.setCan_Hot(can_hot);
                    dayHot_comments_1.setCan_Author_Top(can_author_top);
                    dayHot_comments_1.setIs_Author_Top(is_author_top);
                    dayHot_comments_1.setCan_Collapse(can_collapse);
                    dayHot_comments_1.setCan_Share(can_share);
                    dayHot_comments_1.setCan_Unfold(can_unfold);
                    dayHot_comments_1.setCan_Truncate(can_truncate);
                    dayHot_comments_1.setCan_More(can_more);
                    dayHot_comments_1.setChild_Comment_Count(child_comment_count);
                    dayHot_comments_1.setIs_Visible_Only_To_Myself(is_visible_only_to_myself);

                    dayHot_comments_1.setId_Author(id_author);
                    dayHot_comments_1.setUrl_Token(url_token);
                    dayHot_comments_1.setName_Author(name);
                    dayHot_comments_1.setAvatar_Url(avatar_url);
                    dayHot_comments_1.setAvatar_Url_Template(avatar_url_template);
                    dayHot_comments_1.setIs_Org(is_org);
                    dayHot_comments_1.setType_Author(type_author);
                    dayHot_comments_1.setUrl_Author(url_author);
                    dayHot_comments_1.setUser_Type(user_type);
                    dayHot_comments_1.setHeadline(headline);
                    dayHot_comments_1.setGender(gender);
                    dayHot_comments_1.setIs_Advertiser(is_advertiser);
                    dayHot_comments_1.setLevel_Info(level_info);
                    dayHot_comments_1.setIs_Anonymous(is_anonymous);

                    dayHot_comments_1.setType_Ip(type_ip);
                    dayHot_comments_1.setText_Ip(text_ip);
                    dayHot_comments_1.setColor_Ip(color_ip);
                    dayHot_comments_1.setNight_Color_Ip(night_color_ip);
                    dayHot_comments_1.setHas_Border_Ip(has_border_ip);

                    dayHot_comments_1.setArticleID(ArticleID);

                    dao_comments.MethodInsert(dayHot_comments_1);


                    if (child_comment_count.equals("1") || child_comment_count.equals("2")) {
                        JSONArray child_comments = item.getJSONArray("child_comments");
                        for (int j = 0; j < child_comments.size(); j++) {
                            JSONObject child = child_comments.getJSONObject(j);
                            String id_comment_child = child.getString("id");
                            String type_comment_child = child.getString("type");
                            String resource_type_child = child.getString("resource_type");
                            String member_id_child = child.getString("member_id");
                            String url_comment_child = child.getString("url");
                            String hot_comment_child = child.getString("hot");
                            String top_comment_child = child.getString("top");

//                        String decodedHtml2 = StringEscapeUtils.unescapeHtml4(child.getString("content"));
//                        Document document12 = Jsoup.parse(decodedHtml2);
//                        Elements ietm2 = document12.select("p");
                            String content_comment_child = child.getString("content").replace("<br>", "");


                            boolean status2 = content_comment_child.contains("<p>");
                            if (status2) {
                                String decodedHtml = StringEscapeUtils.unescapeHtml4(content_comment_child);
                                Document document2 = Jsoup.parse(decodedHtml);
                                Elements html1 = document2.select("p");
                                content_comment_child = html1.text();

//                            System.out.println(content_comment_child);
                            }


                            String score_comment_child = child.getString("score");
                            String created_time_child = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(child.getString("created_time")) * 1000));

                            String is_delete_child = child.getString("is_delete");
                            String collapsed_child = child.getString("collapsed");
                            String reviewing_child = child.getString("reviewing");
                            String reply_comment_id_child = child.getString("reply_comment_id");
                            String reply_root_comment_id_child = child.getString("reply_root_comment_id");
                            String liked_child = child.getString("liked");
                            String like_count_child = child.getString("like_count");
                            String disliked_child = child.getString("disliked");
                            String dislike_count_child = child.getString("dislike_count");
                            String is_author_child = child.getString("is_author");
                            String can_like_child = child.getString("can_like");
                            String can_dislike_child = child.getString("can_dislike");
                            String can_delete_child = child.getString("can_delete");
                            String can_reply_child = child.getString("can_reply");
                            String can_hot_child = child.getString("can_hot");
                            String can_author_top_child = child.getString("can_author_top");
                            String is_author_top_child = child.getString("is_author_top");
                            String can_collapse_child = child.getString("can_collapse");
                            String can_share_child = child.getString("can_share");
                            String can_unfold_child = child.getString("can_unfold");
                            String can_truncate_child = child.getString("can_truncate");
                            String can_more_child = child.getString("can_more");
                            String child_comment_count_child = child.getString("child_comment_count");
                            String is_visible_only_to_myself_child = child.getString("is_visible_only_to_myself");


                            JSONObject author_child = child.getJSONObject("author");

                            String id_author_child = author_child.getString("id");
                            String url_token_child = author_child.getString("url_token");
                            String name_child = author_child.getString("name");
                            String avatar_url_child = author_child.getString("avatar_url");
                            String avatar_url_template_child = author_child.getString("avatar_url_template");
                            String is_org_child = author_child.getString("is_org");
                            String type_author_child = author_child.getString("type");
                            String url_author_child = author_child.getString("url");
                            String user_type_child = author_child.getString("user_type");
                            String headline_child = author_child.getString("headline");
                            String gender_child = author_child.getString("gender");
                            String is_advertiser_child = author_child.getString("is_advertiser");
                            String level_info_child = author_child.getString("level_info");
                            String is_anonymous_child = author_child.getString("is_anonymous");


                            JSONArray cococo = child.getJSONArray("comment_tag");
                            String type_ip_child = "-";
                            String text_ip_child = "-";
                            String color_ip_child = "-";
                            String night_color_ip_child = "-";
                            String has_border_ip_child = "-";
                            if (cococo.size() != 0) {

                                JSONObject comment_tag_child = cococo.getJSONObject(0);

                                type_ip_child = comment_tag_child.getString("type");
                                text_ip_child = comment_tag_child.getString("text");
                                color_ip_child = comment_tag_child.getString("color");
                                night_color_ip_child = comment_tag_child.getString("night_color");
                                has_border_ip_child = comment_tag_child.getString("has_border");
                            }
                            String FatherID_child = id_comment;
                            String Comments_leave_child = "3";

                            Bean_comments dayHot_comments_2 = new Bean_comments();

                            dayHot_comments_2.setFatherID(FatherID_child);
                            dayHot_comments_2.setComments_leave(Comments_leave_child);

                            dayHot_comments_2.setFatherID(id_comment);
                            dayHot_comments_2.setComments_leave(Comments_leave_child);
                            dayHot_comments_2.setId_Comment(id_comment_child);
                            dayHot_comments_2.setType_Comment(type_comment_child);
                            dayHot_comments_2.setResource_Type(resource_type_child);
                            dayHot_comments_2.setMember_Id(member_id_child);
                            dayHot_comments_2.setUrl_Comment(url_comment_child);
                            dayHot_comments_2.setHot_Comment(hot_comment_child);
                            dayHot_comments_2.setTop_Comment(top_comment_child);
                            dayHot_comments_2.setContent_Comment(content_comment_child);
                            dayHot_comments_2.setScore_Comment(score_comment_child);
                            dayHot_comments_2.setCreated_Time(created_time_child);
                            dayHot_comments_2.setIs_Delete(is_delete_child);
                            dayHot_comments_2.setCollapsed(collapsed_child);
                            dayHot_comments_2.setReviewing(reviewing_child);
                            dayHot_comments_2.setReply_Comment_Id(reply_comment_id_child);
                            dayHot_comments_2.setReply_Root_Comment_Id(reply_root_comment_id_child);
                            dayHot_comments_2.setLiked(liked_child);
                            dayHot_comments_2.setLike_Count(like_count_child);
                            dayHot_comments_2.setDisliked(disliked_child);
                            dayHot_comments_2.setDislike_Count(dislike_count_child);
                            dayHot_comments_2.setIs_Author(is_author_child);
                            dayHot_comments_2.setCan_Like(can_like_child);
                            dayHot_comments_2.setCan_Dislike(can_dislike_child);
                            dayHot_comments_2.setCan_Delete(can_delete_child);
                            dayHot_comments_2.setCan_Reply(can_reply_child);
                            dayHot_comments_2.setCan_Hot(can_hot_child);
                            dayHot_comments_2.setCan_Author_Top(can_author_top_child);
                            dayHot_comments_2.setIs_Author_Top(is_author_top_child);
                            dayHot_comments_2.setCan_Collapse(can_collapse_child);
                            dayHot_comments_2.setCan_Share(can_share_child);
                            dayHot_comments_2.setCan_Unfold(can_unfold_child);
                            dayHot_comments_2.setCan_Truncate(can_truncate_child);
                            dayHot_comments_2.setCan_More(can_more_child);
                            dayHot_comments_2.setChild_Comment_Count(child_comment_count_child);
                            dayHot_comments_2.setIs_Visible_Only_To_Myself(is_visible_only_to_myself_child);

                            dayHot_comments_2.setId_Author(id_author_child);
                            dayHot_comments_2.setUrl_Token(url_token_child);
                            dayHot_comments_2.setName_Author(name_child);
                            dayHot_comments_2.setAvatar_Url(avatar_url_child);
                            dayHot_comments_2.setAvatar_Url_Template(avatar_url_template_child);
                            dayHot_comments_2.setIs_Org(is_org_child);
                            dayHot_comments_2.setType_Author(type_author_child);
                            dayHot_comments_2.setUrl_Author(url_author_child);
                            dayHot_comments_2.setUser_Type(user_type_child);
                            dayHot_comments_2.setHeadline(headline_child);
                            dayHot_comments_2.setGender(gender_child);
                            dayHot_comments_2.setIs_Advertiser(is_advertiser_child);
                            dayHot_comments_2.setLevel_Info(level_info_child);
                            dayHot_comments_2.setIs_Anonymous(is_anonymous_child);

                            dayHot_comments_2.setType_Ip(type_ip_child);
                            dayHot_comments_2.setText_Ip(text_ip_child);
                            dayHot_comments_2.setColor_Ip(color_ip_child);
                            dayHot_comments_2.setNight_Color_Ip(night_color_ip_child);
                            dayHot_comments_2.setHas_Border_Ip(has_border_ip_child);

                            dayHot_comments_2.setArticleID(ArticleID);
                            dao_comments.MethodInsert(dayHot_comments_2);

                        }
                    }

                }
            }
        }
    }

}
