package com.yikejian.store.domain.store;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.DeviceDto;
import com.yikejian.store.api.v1.dto.ImageDto;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * <code>Store</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    private Long storeId;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 店铺信息
     */
    private String information;
    /**
     * 店铺地址
     */
    private String address;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 起始工作日
     */
    private Integer workDayStart;
    /**
     * 结束工作日
     */
    private Integer workDayEnd;
    /**
     * 开始营业时间(精确到分，如1020表示10点20分)
     */
    private String startTime;
    /**
     * 结束营业时间(精确到分，如1020表示10点20分)
     */
    private String endTime;
    /**
     * 预约规则：单个时间片的长度，单位分钟
     */
    private Integer unitDuration;
    /**
     * 预约规则：单个时间片内允许的最大预约次数（库存数量）
     */
    private Integer unitTimes;
    /**
     * 交通
     */
    private String traffic;
    /**
     * 城市
     */
    private String city;
    /**
     * 商场
     */
    private String mall;
    /**
     * 经度
     */
    private Float longitude;
    /**
     * 纬度
     */
    private Float latitude;
    /**
     * 产品
     */
//    @JsonBackReference("storeProductSet")
    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<StoreProduct> storeProductSet;
    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Image> imageSet;
    /**
     * 设备
     */
//    @JsonBackReference("deviceSet")
    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Device> deviceSet;

    public Store() {
    }

    public Store(Long storeId) {
        this.storeId = storeId;
    }

    public Store mergeOtherStore(Store other) {
        if (StringUtils.isNotBlank(other.getStoreName())) {
            setStoreName(other.getStoreName());
        }
        if (StringUtils.isNotBlank(other.getInformation())) {
            setInformation(other.getInformation());
        }
        if (StringUtils.isNotBlank(other.getAddress())) {
            setAddress(other.getAddress());
        }
        if (StringUtils.isNotBlank(other.getPhoneNumber())) {
            setPhoneNumber(other.getPhoneNumber());
        }
        if (other.getWorkDayStart() != null) {
            setWorkDayStart(other.getWorkDayStart());
        }
        if (other.getWorkDayEnd() != null) {
            setWorkDayEnd(other.getWorkDayEnd());
        }
        if (StringUtils.isNotBlank(other.getStartTime())) {
            setStartTime(other.getStartTime());
        }
        if (StringUtils.isNotBlank(other.getEndTime())) {
            setEndTime(other.getEndTime());
        }
        if (other.getUnitDuration() != null) {
            setUnitDuration(other.getUnitDuration());
        }
        if (other.getUnitTimes() != null) {
            setUnitTimes(other.getUnitTimes());
        }
        if (StringUtils.isNotBlank(other.getTraffic())) {
            setTraffic(other.getTraffic());
        }
        if (StringUtils.isNotBlank(other.getCity())) {
            setCity(other.getCity());
        }
        if (StringUtils.isNotBlank(other.getMall())) {
            setMall(other.getMall());
        }
        if (other.getLongitude() != null) {
            setLongitude(other.getLongitude());
        }
        if (other.getLatitude() != null) {
            setLatitude(other.getLatitude());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        // check product set
        if (other.getStoreProductSet() != null && other.getStoreProductSet().size() > 0) {
            setStoreProductSet(other.getStoreProductSet());
//            for (StoreProduct storeProduct : storeProductSet) {
//                if(other.getStoreProductSet().contains(storeProduct)) {
//                    continue;
//                }
//                storeProduct.setDeleted(1);
//            }
//            storeProductSet.addAll(other.getStoreProductSet());
//            for (StoreProduct storeProduct : storeProductSet) {
//                storeProduct.setStore(this);
//            }
        }
        // check image set
        if (other.getImageSet() != null && other.getImageSet().size() > 0) {
            setImageSet(other.getImageSet());
        }
        // check device set
        if (other.getDeviceSet() != null && other.getDeviceSet().size() > 0) {
            setDeviceSet(other.getDeviceSet());
//            for (Device device : deviceSet) {
//                if(other.getDeviceSet().contains(device)) {
//                    continue;
//                }
//                device.setDeleted(1);
//                for (DeviceProduct deviceProduct : device.getDeviceProductSet()) {
//                    deviceProduct.setDeleted(1);
//                }
//            }
//            deviceSet.addAll(other.getDeviceSet());
//            for (Device device : deviceSet) {
//                device.setStore(this);
//            }
        }
        return this;
    }

    public StoreDto toStoreDto() {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId(getStoreId());
        storeDto.setStoreName(getStoreName());
        storeDto.setInformation(getInformation());
        storeDto.setAddress(getAddress());
        storeDto.setPhoneNumber(getPhoneNumber());
        storeDto.setWorkDayStart(getWorkDayStart());
        storeDto.setWorkDayEnd(getWorkDayEnd());
        storeDto.setStartTime(getStartTime());
        storeDto.setEndTime(getEndTime());
        storeDto.setUnitDuration(getUnitDuration());
        storeDto.setUnitTimes(getUnitTimes());
        storeDto.setTraffic(getTraffic());
        storeDto.setCity(getCity());
        storeDto.setMall(getMall());
        storeDto.setLongitude(getLongitude());
        storeDto.setLatitude(getLatitude());
        storeDto.setEffective(getEffective());
        storeDto.setDeleted(getDeleted());
        List<Long> productIdList = Lists.newArrayList();
        for (StoreProduct storeProduct : getStoreProductSet()) {
            productIdList.add(storeProduct.getProductId());
        }
        storeDto.setProducts(productIdList);
        List<DeviceDto> deviceDtoList = Lists.newArrayList();
        for (Device device : getDeviceSet()) {
            deviceDtoList.add(device.toDeviceDto());
        }
        storeDto.setDevices(deviceDtoList);
        List<ImageDto> imageDtoList = Lists.newArrayList();
        for (Image image : getImageSet()) {
            imageDtoList.add(image.toImageDto());
        }
        storeDto.setImages(imageDtoList);
        return storeDto;
    }

    public Store fromStoreDto(StoreDto storeDto) {
        if (StringUtils.isNotBlank(storeDto.getStoreName())) {
            setStoreName(storeDto.getStoreName());
        }
        if (StringUtils.isNotBlank(storeDto.getAddress())) {
            setAddress(storeDto.getAddress());
        }
        if (StringUtils.isNotBlank(storeDto.getInformation())) {
            setInformation(storeDto.getInformation());
        }
        if (StringUtils.isNotBlank(storeDto.getPhoneNumber())) {
            setPhoneNumber(storeDto.getPhoneNumber());
        }
        if (storeDto.getWorkDayStart() != null) {
            setWorkDayStart(storeDto.getWorkDayStart());
        }
        if (storeDto.getWorkDayEnd() != null) {
            setWorkDayEnd(storeDto.getWorkDayEnd());
        }
        if (StringUtils.isNotBlank(storeDto.getStartTime())) {
            setStartTime(storeDto.getStartTime());
        }
        if (StringUtils.isNotBlank(storeDto.getEndTime())) {
            setEndTime(storeDto.getEndTime());
        }
        if (storeDto.getUnitDuration() != null) {
            setUnitDuration(storeDto.getUnitDuration());
        }
        if (storeDto.getUnitTimes() != null) {
            setUnitTimes(storeDto.getUnitTimes());
        }
        if (StringUtils.isNotBlank(storeDto.getTraffic())) {
            setTraffic(storeDto.getTraffic());
        }
        if (StringUtils.isNotBlank(storeDto.getCity())) {
            setCity(storeDto.getCity());
        }
        if (StringUtils.isNotBlank(storeDto.getMall())) {
            setMall(storeDto.getMall());
        }
        if (storeDto.getLongitude() != null) {
            setLongitude(storeDto.getLongitude());
        }
        if (storeDto.getLatitude() != null) {
            setLatitude(storeDto.getLatitude());
        }
        if (storeDto.getEffective() != null) {
            setEffective(storeDto.getEffective());
        }
        if (storeDto.getDeleted() != null) {
            setDeleted(storeDto.getDeleted());
        }
        if (storeDto.getProducts() != null) {
            Set<StoreProduct> storeProductSet = Sets.newHashSet();
            for (Long productId : storeDto.getProducts()) {
                StoreProduct storeProduct = new StoreProduct();
                storeProduct.setProductId(productId);
                storeProduct.setStore(this);
                storeProduct.setEffective(1);
                storeProduct.setDeleted(0);
                storeProductSet.add(storeProduct);
            }
            setStoreProductSet(storeProductSet);
        }
        if (storeDto.getDevices() != null) {
            Set<Device> deviceSet = Sets.newHashSet();
            for (DeviceDto deviceDto : storeDto.getDevices()) {
                Device device = new Device();
                device.setStore(this);
                device.setEffective(1);
                device.setDeleted(0);
                deviceSet.add(device.fromDeviceDto(deviceDto));
            }
            setDeviceSet(deviceSet);
        }
        if (storeDto.getImages() != null) {
            Set<Image> imageSet = Sets.newHashSet();
            boolean cover = true;
            for (ImageDto imageDto : storeDto.getImages()) {
                Image image = new Image();
                image.setStore(this);
                image.setEffective(1);
                image.setDeleted(0);
                if (cover) {
                    image.setCover(1);
                    cover = false;
                } else {
                    image.setCover(0);
                }
                imageSet.add(image.fromImageDto(imageDto));
            }
            setImageSet(Lists.newArrayList(imageSet));
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store store = (Store) o;

        return storeName.equals(store.storeName);

    }

    @Override
    public int hashCode() {
        return storeName.hashCode();
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getWorkDayStart() {
        return workDayStart;
    }

    public void setWorkDayStart(Integer workDayStart) {
        this.workDayStart = workDayStart;
    }

    public Integer getWorkDayEnd() {
        return workDayEnd;
    }

    public void setWorkDayEnd(Integer workDayEnd) {
        this.workDayEnd = workDayEnd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUnitDuration() {
        return unitDuration;
    }

    public void setUnitDuration(Integer unitDuration) {
        this.unitDuration = unitDuration;
    }

    public Integer getUnitTimes() {
        return unitTimes;
    }

    public void setUnitTimes(Integer unitTimes) {
        this.unitTimes = unitTimes;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Set<StoreProduct> getStoreProductSet() {
        return storeProductSet;
    }

    public void setStoreProductSet(Set<StoreProduct> storeProductSet) {
        this.storeProductSet = storeProductSet;
    }

    public List<Image> getImageSet() {
        return imageSet;
    }

    public void setImageSet(List<Image> imageSet) {
        this.imageSet = imageSet;
    }

    public Set<Device> getDeviceSet() {
        return deviceSet;
    }

    public void setDeviceSet(Set<Device> deviceSet) {
        this.deviceSet = deviceSet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMall() {
        return mall;
    }

    public void setMall(String mall) {
        this.mall = mall;
    }

}
