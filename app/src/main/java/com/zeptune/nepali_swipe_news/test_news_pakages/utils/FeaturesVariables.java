package com.zeptune.nepali_swipe_news.test_news_pakages.utils;

public class FeaturesVariables {
    public static final String FEATURES_RASIFAL = "rasifal";
    public static final String FEATURES_GOLD_SILVER = "gold_silver";
    public static final String FEATURES_DATE = "date_nep";
    public static final String FEATURES_CURRENCY = "currency_features";


    public enum FeaturesTypesEnum {
        FEATURES_RASIFAL {
            @Override
            public String getFeaturesTypeCode() {
                return FeaturesVariables.FEATURES_RASIFAL;
            }
        },
        FEATURES_GOLD_SILVER {
            @Override
            public String getFeaturesTypeCode() {
                return FeaturesVariables.FEATURES_GOLD_SILVER;
            }
        },
        FEATURES_CURRENCY {
            @Override
            public String getFeaturesTypeCode() {
                return FeaturesVariables.FEATURES_CURRENCY;
            }
        },
        FEATURES_DATE {
            @Override
            public String getFeaturesTypeCode() {
                return FeaturesVariables.FEATURES_DATE;
            }
        };

        public String getFeaturesTypeCode() {
            throw new AbstractMethodError();
        }
    }

}
