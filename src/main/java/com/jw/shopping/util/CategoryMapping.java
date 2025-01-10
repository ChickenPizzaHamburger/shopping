package com.jw.shopping.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CategoryMapping {

    private static final Map<String, Integer> CATEGORY_MAPPING;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("all", 0);
        
        map.put("clothing-accessories", 1);
        map.put("cosmetics", 2);
        map.put("food", 3);
        map.put("books-music", 4);
        map.put("travel-tickets", 5);
        map.put("baby-products", 6);
        map.put("kitchen-products", 7);
        map.put("home-products", 8);
        map.put("interior", 9);
        map.put("electronics", 10);
        map.put("hobbies", 11);
        map.put("vehicles", 12);
        map.put("toys-stationery", 13);
        map.put("pet-products", 14);
        map.put("health", 15);

        CATEGORY_MAPPING = Collections.unmodifiableMap(map); // 불변 Map 생성
    }

    public static Integer getCategoryId(String categoryName) {
        return CATEGORY_MAPPING.get(categoryName);
    }

    public static Map<String, Integer> getAllCategories() {
        return CATEGORY_MAPPING;
    }
}