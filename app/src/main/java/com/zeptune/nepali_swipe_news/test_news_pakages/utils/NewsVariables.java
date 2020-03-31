package com.zeptune.nepali_swipe_news.test_news_pakages.utils;

public class NewsVariables {
    private static final String NEWS_All = "allNews";
    private static final String News_TOPS = "tops";
    private static final String News_POLITICS = "pltc";
    private static final String News_SPORTS = "sprt";
    private static final String News_Technology = "scte";
    private static final String News_WORLD = "wrld";
    private static final String News_BUSINESS = "busi";
    private static final String News_ENTERTAINMENT = "entm";
    private static final String News_HELTH = "hlth";
    private static final String News_UNCATEGORIES = "oths";
    private static final String CORONA_COUNDTEY_WISE_STATE_DB = "country_wise_state";
    private static final String CORONA_WORLD_CASES_DB = "corona_world_cases";
    private static final String CORONA_AFFECTED_COUNTRY_DB = "corona_affted_country";


    public enum NewsOrCoronaTypesEnum {

        CORONA_COUNTRY_WISE_STATE {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.CORONA_COUNDTEY_WISE_STATE_DB;
            }
        },
        CORONA_WORLD_CASES {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.CORONA_WORLD_CASES_DB;
            }
        },
        CORONA_AFFECTED_COUNTRY {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.CORONA_AFFECTED_COUNTRY_DB;
            }
        },
        NEWS_All {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.NEWS_All;
            }
        },
        News_TOPS {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_TOPS;
            }
        },
        News_POLITICS {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_POLITICS;
            }
        },
        News_SPORTS {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_SPORTS;
            }
        },
        News_Technology {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_Technology;
            }
        },
        News_WORLD {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_WORLD;
            }
        },
        News_BUSINESS {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_BUSINESS;
            }
        },
        News_ENTERTAINMENT {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_ENTERTAINMENT;
            }
        },
        News_HEALTH {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_HELTH;
            }
        },
        News_UNCATEGORIES {
            @Override
            public String getNewsTypeCode() {
                return NewsVariables.News_UNCATEGORIES;
            }
        };

        public String getNewsTypeCode() {
            throw new AbstractMethodError();
        }
    }

}
