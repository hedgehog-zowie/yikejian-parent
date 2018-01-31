package com.yikejian.store.api.v1;

import com.google.common.collect.Sets;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * <code>StoreControllerV1Test</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/26 10:52
 */
public class StoreProductControllerV1Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreProductControllerV1Test.class);

    private static RestTemplate restTemplate;
    private static final String POST_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/product?access_token=%s";
    private static final String POSTS_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/products?access_token=%s";
    private static final String PUT_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/product?access_token=%s";
    private static final String PUTS_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/products?access_token=%s";
    private static final String GET_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/product/%s?access_token=%s";
    private static final String GETS_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s/products?access_token=%s";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY5ODE3MDcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiNDdiNDUzNTMtYThiMS00MjQwLWIyNTYtNzgzZjkyYjQ2ZWM0IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.Cs7Z99H6jYPqyOnx7iZYqL1DEW90Z2TtjU0c1RvojW2PPBdQXrkP8GOKV_nhY4qOidse-LF-ZGiCMNzdnW5d_7V1yB7ceGn4rRyuRzVcYQshb-gbEZG7SjBvtQvjGKR1QmAk82YizX2p3vt5rpkdzXPx0mP5uvJJGpnsr9LQXwk6oZioA7sUx7RjYLE7CxO_934gt_BiG7gsorXGxhBQXO4U3-pRnxiGLVHhdF052AzzO-HFx_BTy17KhO6cxBa6TXuvGA5Ju2HoHz5M1X704Pu8fOsGXpJLw3_bcE5FV6A3mtOez3Z0e3J-n8Yh96PcW6Rb8fRe2wuGb1gkFYM6wg";

    @BeforeClass
    public static void beforeClass() {
        restTemplate = new RestTemplate();
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testPost() {
        String postUrl = String.format(POST_URL_TEMPLATE, ACCESS_TOKEN);
        StoreProduct storeProduct = restTemplate.postForObject(postUrl, newStoreProduct("产品XX"), StoreProduct.class);
        assertEquals(1, storeProduct.getProductId().intValue());
        assertEquals("产品XX", storeProduct.getProduct().getProductName());
        LOGGER.info(JsonUtils.toJson(storeProduct));
        List<StoreProduct> storeProductList = restTemplate.postForObject(postUrl, newStoreProductSet(), List.class);
        assertEquals(2, storeProductList.size());
    }

    @Test
    public void testPut() {
        String putUrl = String.format(PUT_URL_TEMPLATE, ACCESS_TOKEN);
        StoreProduct storeProduct = newStoreProduct("产品XX_put");
        storeProduct.setStoreProductId(1L);
        restTemplate.put(putUrl, storeProduct);
        String getUrl = String.format(GET_URL_TEMPLATE, 1, ACCESS_TOKEN);
        StoreProduct gotStoreProduct = restTemplate.getForObject(getUrl, StoreProduct.class);
        assertEquals(1, gotStoreProduct.getStoreProductId().longValue());
        assertEquals("产品XX_put", gotStoreProduct.getProduct().getProductName());
        LOGGER.info(JsonUtils.toJson(gotStoreProduct));
    }

    @Test
    public void testGet() {
        String getUrl = String.format(GET_URL_TEMPLATE, 1, ACCESS_TOKEN);
        Store gotStore = restTemplate.getForObject(getUrl, Store.class);
        assertEquals(1, gotStore.getStoreId().longValue());
        LOGGER.info(JsonUtils.toJson(gotStore));
    }

    @Test
    public void testGets() throws UnsupportedEncodingException {
        String getUrl = String.format(GETS_URL_TEMPLATE, 1, ACCESS_TOKEN);
        List<StoreProduct> storeProductList = restTemplate.getForObject(getUrl, List.class);
        assertEquals(2, storeProductList.size());
        LOGGER.info(JsonUtils.toJson(storeProductList));
    }

    private StoreProduct newStoreProduct(String name) {
        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setProductId(1L);
        return storeProduct;
    }

    private Set<StoreProduct> newStoreProductSet() {
        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        return storeProductSet;
    }

}
