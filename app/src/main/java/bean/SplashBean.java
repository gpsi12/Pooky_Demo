package bean;

import com.google.gson.annotations.SerializedName;

/**
 * 闪屏界面bean
 * Created by Pooky on 2017/10/12.
 */

public class SplashBean extends BaseGsonBeans{

    /**
     * id : 2828
     * imageUrl : http://www.aw13.cn/public/upload/photo/20170722/5973064fe7205.jpg
     * introduce :  闪屏广告介绍
     */
    @SerializedName("id")
    private String id;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("introduce")
    private String introduce;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
