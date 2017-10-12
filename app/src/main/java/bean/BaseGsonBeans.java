package bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * beans类 基本类，用于gosn
 * Created by Pooky on 2017/10/12.
 */

public class BaseGsonBeans implements Serializable {
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
