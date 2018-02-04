package com.yikejian.store.domain.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.store.api.v1.dto.ImageDto;
import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * @author jackalope
 * @Title: Image
 * @Package com.yikejian.store.domain.store
 * @Description: TODO
 * @date 2018/2/4 16:03
 */
@Entity
public class Image extends BaseEntity {

    @Id
    @GeneratedValue
    private Long imageId;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 封面
     */
    private Integer cover;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    public ImageDto toImageDto() {
        ImageDto imageDto = new ImageDto();
        imageDto.setImageId(getImageId());
        imageDto.setUrl(getUrl());
        imageDto.setCover(getCover());
        return imageDto;
    }

    public Image fromImageDto(ImageDto imageDto) {
        if (StringUtils.isNotBlank(imageDto.getUrl())) {
            setUrl(imageDto.getUrl());
        }
        if (imageDto.getCover() != null) {
            setCover(imageDto.getCover());
        }
        return this;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
