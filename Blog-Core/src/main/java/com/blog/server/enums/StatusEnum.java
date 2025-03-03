package com.blog.server.enums;

public enum StatusEnum implements DictionaryEnum<Integer>{

    ENABLE(1, "正常", CssTag.PRIMARY),
    DISABLE(0, "停用", CssTag.DANGER);;

    private final int value;
    private final String description;
    private final String cssTag;

    StatusEnum(int value, String description, String cssTag) {
        this.value = value;
        this.description = description;
        this.cssTag = cssTag;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String cssTag() {
        return cssTag;
    }

    public static class CssTag {

        public static final String PRIMARY = "";
        public static final String DANGER = "danger";
        public static final String WARNING = "warning";
        public static final String SUCCESS = "success";
        public static final String INFO = "info";

        private CssTag() {
        }
    }
}
