package org.progos.tasteofabuddhabicms.model;

import java.io.Serializable;

/**
 * Created by NomBhatti on 11/30/2015.
 */
public class Restaurant implements Serializable {

    String id;
    String imgUrl;

    public Restaurant(String id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
