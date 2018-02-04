package com.yikejian.store.api.v1.dto;

/**
 * @author jackalope
 * @Title: ImageDto
 * @Package com.yikejian.store.api.v1.dto
 * @Description: TODO
 * @date 2018/2/4 16:12
 */
public class ImageDto {

    private Long imageId;
    private Integer cover;
    private String url;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
