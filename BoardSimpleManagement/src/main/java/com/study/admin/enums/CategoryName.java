package com.study.admin.enums;

import java.util.Arrays;
import java.util.List;

public enum CategoryName {

    N ("공지사항", Arrays.asList("general", "urgent")),
    F ("자유", Arrays.asList("hobby", "humor", "touching")),
    G ("갤러리", Arrays.asList("celebrity", "memorial"," place"));

    private String title;
    private List<String> categoryList;

    CategoryName(String title, List<String> categoryList) {
        this.title = title;
        this.categoryList = categoryList;
    }

    public String title() {
        return title;
    }

    public List<String> categoryList() {
        return categoryList;
    }

}
