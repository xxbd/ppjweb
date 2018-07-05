package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:32 2018/3/12
 * @Modified By:
 */

import Dao.request.BaseMessage;

import java.util.List;

/**
 *@ClassName : RArticleMessage
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/12 14:32
 *@Version : 1.0
 */
public class RArticleMessage extends BaseMessage{
    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }

}
