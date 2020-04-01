package com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.zeptune.nepali_swipe_news.Animation.ProgressBarAnimation;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.CategoryActivity;
import com.zeptune.nepali_swipe_news.MainActivity;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.all_interfaces.InterfaceFragmentToogle;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.News_Variabls;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends BaseNewsDetailFragment implements NewsInterface {


    public NewsDetailFragment() {
        // Required empty public constructor
    }

    LinearLayout myNewsDetailFrame;
    ImageView img_err_detail;
    TextView txt_error;
    Button btn_reload_detail;
    WebView webView;
    View view;
    FrameLayout progressFrame;
    private static String BASE_URL;
    Context mContext;
    private static String file_type = "image/*";
    private boolean multiple_files = true;
    private String cam_file_data = null; //for storing camera file information
    private ValueCallback<Uri> file_data; //data/header received after file
    private ValueCallback<Uri[]> file_path; //received file(s) temp. location\
    private final static int file_req_code = 1;
    private static final String TAG = "LatestNewTag";
    Toolbar toolbar;
    TextView newsLinkView;
    String mylink;
    ProgressBar progress_Bar_webview;
    private InterfaceFragmentToogle interfaceFragmentToogle;
    private AdView adViewMenuNewsDetail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Wesitelink", "newdfsfInstance: " + News_Variabls.NEWS_WEBSITE_URL_STRING);
        if (getActivity() instanceof CategoryActivity) {
            ((CategoryActivity) getActivity()).setNewsData(this);
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setNewsLink(this);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news_detail, container, false);
            setHasOptionsMenu(true);
            initui();
            toolbar = view.findViewById(R.id.toolbar_newsDetail);
            myNewsDetailFrame = view.findViewById(R.id.myNewsDetailFrame);
            txt_error = view.findViewById(R.id.txt_error);
            progress_Bar_webview = view.findViewById(R.id.progress_Bar_webview);
            btn_reload_detail = view.findViewById(R.id.btn_reload_detail);
        btn_reload_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl();
            }
        });
            initObj();
            toolbar.setNavigationIcon(mContext.getResources().getDrawable(R.drawable.ic_navigate_before_black_24dp));
            toolbar.setBackgroundColor(mContext.getResources().getColor(R.color.lightbue));
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsDetailFragment.super.performBackPress();
                }
            });
            toolbar.setTitleMarginStart(0);
            toolbar.setTitle("");

            newsLinkView = view.findViewById(R.id.newsLinkDisplay);
            newsLinkView.setText("Link to be loaded");

            webView = view.findViewById(R.id.website_news);


            Bundle bundle = this.getArguments();
            if (bundle != null) {
//                mylink = bundle.getString("newslink", "");
//                webView.loadUrl(mylink);
//                newsLinkView.setText(mylink);
                myNewsDetailFrame.setVisibility(View.GONE);
            } else {
//                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                mylink = sharedpreferences.getString("newsLink", "");
//                webView.loadUrl(mylink);
//                newsLinkView.setText(mylink);
                myNewsDetailFrame.setVisibility(View.GONE);
            }

//            progressFrame = view.findViewById(R.id.website_news_progress_frame);
//            progressFrame.setVisibility(View.VISIBLE);
            if (savedInstanceState != null) {
                webView.restoreState(savedInstanceState);
            } else {


                WebSettings webSettings = webView.getSettings();

                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setAllowUniversalAccessFromFileURLs(true);

                if (Build.VERSION.SDK_INT >= 21) {
                    webSettings.setMixedContentMode(0);
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }


                //Toast.makeText(getContext(), BASE_URL, Toast.LENGTH_SHORT).show();
                webView.setWebViewClient(new CustomWebViewClient());


            }
        }


        setWebClient();
//        loadBannerAds();
        return view;
    }

    private void initui() {
        adViewMenuNewsDetail = view.findViewById(R.id.adViewMenuNewsDetail);
    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_test_ads));

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        adViewMenuNewsDetail.loadAd(request);
    }

    private void initObj() {
        interfaceFragmentToogle = (InterfaceFragmentToogle) mContext;
    }

    private void setWebClient() {
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress_Bar_webview.setVisibility(View.VISIBLE);
                ProgressBarAnimation anim = new ProgressBarAnimation(progress_Bar_webview, 10, newProgress);
                anim.setDuration(1000);
                progress_Bar_webview.startAnimation(anim);
                progress_Bar_webview.setProgress(newProgress);
                if (newProgress == 75) {

                }


                if (progress_Bar_webview.getProgress() == 100) {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            progress_Bar_webview.setVisibility(View.GONE);
                        }
                    }, 300);

                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (file_permission() && Build.VERSION.SDK_INT >= 21) {
                    file_path = filePathCallback;
                    Intent takePictureIntent = null;
                    Intent takeVideoIntent = null;
                    boolean includeVideo = false;
                    boolean includePhoto = false;

                    /*-- Checking



                    the accept parameter to determine which intent(s) to include- */
                    paramCheck:
                    for (String acceptTypes : fileChooserParams.getAcceptTypes()) {
                        String[] splitTypes = acceptTypes.split(", ?+");//splitting into chunks to detect individual value
                        for (String acceptType : splitTypes) {
                            switch (acceptType) {
                                case "*/*":
                                    includePhoto = true;
                                    includeVideo = true;
                                    break paramCheck;
                                case "image/*":
                                    includePhoto = true;
                                    break;
                                case "video/*":
                                    includeVideo = true;
                                    break;

                            }
                        }
                    }

                    if (fileChooserParams.getAcceptTypes().length == 0) {
                        includePhoto = true;
                        includeVideo = true;
                    }
                    if (includePhoto) {
                        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = create_image();
                            } catch (IOException ex) {
                                Log.e(TAG, "Image file creation failed", ex);
                            }
                            if (photoFile != null) {
                                cam_file_data = "file:" + photoFile.getAbsolutePath();
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                            } else {
                                cam_file_data = null;
                                takePictureIntent = null;
                            }
                        }
                    }
                    if (includeVideo) {
                        takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        if (takeVideoIntent.resolveActivity(mContext.getPackageManager()) != null) {
                            File videoFile = null;
                            try {
                                videoFile = create_video();
                            } catch (IOException ex) {
                                Log.e(TAG, "Video file cration failed", ex);
                            }
                            if (videoFile != null) {
                                cam_file_data = "file:" + videoFile.getAbsolutePath();
                                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
                            } else {
                                cam_file_data = null;
                                takeVideoIntent = null;
                            }


                        }
                    }
                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType(file_type);
                    if (multiple_files) {
                        contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    }

                    Intent[] intentArray;
                    if (takePictureIntent != null && takeVideoIntent != null) {
                        intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
                    } else if (takePictureIntent != null) {
                        intentArray = new Intent[]{takePictureIntent};
                    } else if (takeVideoIntent != null) {
                        intentArray = new Intent[]{takeVideoIntent};
                    } else {
                        intentArray = new Intent[0];
                    }

                    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "File chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, file_req_code);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private File create_video() throws IOException {
        String file_name = new SimpleDateFormat("yyyy_mm_ss").format(new Date());
        String new_name = "file_" + file_name + "_";
        File sd_directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(new_name, ".mp4", sd_directory);//.3gp ??

    }

    private File create_image() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private boolean file_permission() {
        return true;
    }

    public boolean onKeyDown() {
        if (webView.canGoBack()) {
            this.webView.goBack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            if (resultCode == Activity.RESULT_CANCELED) {
                if (requestCode == file_req_code) {
                    file_path.onReceiveValue(null);
                    return;
                }
            }
            /*-- Continue if response is positive --*/
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == file_req_code) {
                    if (null == file_path) {
                        return;
                    }
                    ClipData clipData;
                    String stringData;
                    try {
                        clipData = data.getClipData();
                        stringData = data.getDataString();
                    } catch (Exception e) {
                        clipData = null;
                        stringData = null;
                    }
                    if (clipData == null && stringData == null && cam_file_data != null) {
                        results = new Uri[]{Uri.parse(cam_file_data)};

                    } else {
                        if (clipData != null) {
                            final int numSelectedFiles = clipData.getItemCount();
                            results = new Uri[numSelectedFiles];
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                results[i] = clipData.getItemAt(i).getUri();
                            }


                        } else {
                            results = new Uri[]{Uri.parse(stringData)};
                        }
                    }
                }
            }
            file_path.onReceiveValue(results);
            file_path = null;

        } else {
            if (requestCode == file_req_code) {
                if (file_data == null) return;
                Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
                file_data.onReceiveValue(result);
                file_data = null;
            }
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void newsLink(String newsLink, int type) {
        if (type == 1) {
            toolbar.setVisibility(View.VISIBLE);
            myNewsDetailFrame.setVisibility(View.GONE);
            newsLinkView.setText(newsLink);
            webView.loadUrl(newsLink);
        }
//        BASE_URL=newsLink;
//        Log.d(TAG, "newsLink: "+newsLink);
    }

    @Override
    public void newImageLink(String imageLink) {

    }

    @Override
    public void swipingDown(Boolean hideLayout) {

    }

    @Override
    public void onRightSwipe(Boolean rightSwipe) {

    }

    @Override
    public void verticalViewpagerPosition(int position) {

    }


    public class CustomWebViewClient extends android.webkit.WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (Build.VERSION.SDK_INT > 21) {
                Log.e(TAG, "onReceivedError: " + error.getErrorCode());

                //Toast.makeText(mContext, "Failed loading app!"+error.getErrorCode(), Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
            Toast.makeText(mContext, "Login Request", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

//            progressFrame.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.new_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openLinkInBrowser: {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mylink));
                startActivity(browserIntent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUrl(String newsUrl) {

//        webView.loadUrl(newsUrl);
//        newsLinkView.setText(newsUrl);
        mylink = newsUrl;
    }

    public void loadUrl() {
        if(isNetworkAvailable()){
        if (!TextUtils.isEmpty(mylink)) {
            newsLinkView.setText(mylink);
            webView.loadUrl(mylink);
            if(myNewsDetailFrame.getVisibility()==View.VISIBLE){
                myNewsDetailFrame.setVisibility(View.GONE);
            }
        }else {
            if(myNewsDetailFrame.getVisibility()==View.GONE){
                myNewsDetailFrame.setVisibility(View.VISIBLE);
                txt_error.setText("No Link");
            }
        }
        }
        else{
            if(myNewsDetailFrame.getVisibility()==View.GONE){
                myNewsDetailFrame.setVisibility(View.VISIBLE);
                txt_error.setText("No Internet Connection");
            }
        }

    }

    public void clearWebview() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.clearCache(true);
            webView.clearHistory();
            webView.clearFormData();
            WebStorage.getInstance().deleteAllData();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
