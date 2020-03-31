package com.zeptune.nepali_swipe_news.retrofit.retofit;

import android.util.Log;

import com.zeptune.nepali_swipe_news.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtils {
private static Gson gson;
    private static String BASE_URL;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static OkHttpClient getDefaultOkHttpClient() throws Exception {
        if (okHttpClient == null) {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_0, TlsVersion.TLS_1_1, TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                    ).build();
            List<ConnectionSpec> connectionSpecs = new ArrayList<>();
            connectionSpecs.add(spec);

            if (BuildConfig.DEBUG) {
                connectionSpecs.add(ConnectionSpec.CLEARTEXT);
            }

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectionSpecs(connectionSpecs);

            if (BuildConfig.DEBUG) {
                builder
                        .sslSocketFactory(getBypassSSLSocketFactory())
                        .hostnameVerifier(getBypassHostNameVerifier());
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    public static Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null || BASE_URL == null || !BASE_URL.equals(baseUrl)) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(new JakeWhartonHowCouldYouDoThisToMeConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getDefaultOkHttpClient())
                        .build();
            } catch (Exception e) {
                if (e.getLocalizedMessage() != null) {

                    Log.e("Error", e.getLocalizedMessage());
                }
                e.printStackTrace();
            }
        }
        BASE_URL = baseUrl;
        return retrofit;
    }

    private static SSLSocketFactory getBypassSSLSocketFactory() throws Exception {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.getSocketFactory();
    }

    private static HostnameVerifier getBypassHostNameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }
    public static String getErrorMessage(Response response) {
        System.err.println("Response: " + response.raw());
        ResponseBody responseBody = response.errorBody();
        try {
            String errorMessage;
            if (responseBody != null) {
                String errorBody = responseBody.string();
                System.err.println(errorBody);
                MyResponse myResponse = fromJson(errorBody,
                        MyResponse.class);
                errorMessage = myResponse == null || myResponse.error == null ?
                        "Error " + response.code() + ": " + response.message() : myResponse.error;
            } else errorMessage = "No data received.";

            return errorMessage;
        } catch (JsonSyntaxException e) {
            return "Malformed response from server!";
        } catch (Exception e) {
            return "Error " + response.code() + ": " + e.getMessage();
        }
    }
    public class MyResponse{
        String message;
        String error;
    }
    public static <T> T fromJson(String json, Class classOfT) {
        return getGson().fromJson(json, (Type) classOfT);
    }
    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }


}
