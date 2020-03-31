package com.zeptune.nepali_swipe_news.retrofit.retofit;

import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;



public class ServiceFactory {


    public static NewsSercvices getNewsServices(){
        return RetrofitUtils.getRetrofit(News_Variabls.BASEURL_CATEGORY).create(NewsSercvices.class);
    }

    public static  NewsSercvices getAllCategory(){
        return RetrofitUtils.getRetrofit(News_Variabls.BASEURL_CATEGORY).create(NewsSercvices.class);
    }

    public static NewsSercvices getRasifals(){
        return RetrofitUtils.getRetrofit(News_Variabls.BASEURL_CATEGORY).create(NewsSercvices.class);
    }

    public static CoronaServices getCoronaServices(){
        return RetrofitUtils.getRetrofit(News_Variabls.BASEURL_CATEGORY).create(CoronaServices.class);
    }

}
