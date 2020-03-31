package com.zeptune.nepali_swipe_news.parentview;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.CategoryActivity;
import com.zeptune.nepali_swipe_news.MainActivity;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.models.Datum;
import com.zeptune.nepali_swipe_news.test_news_pakages.news_main.viewpage_fragments.news_detail_fragment.NewsDetailFragment;
import com.zeptune.nepali_swipe_news.all_interfaces.NewsInterface;
import com.zeptune.nepali_swipe_news.all_interfaces.ParentAdapeterNotofier;
import com.zeptune.nepali_swipe_news.utils.Util;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerticalFragment extends Fragment implements View.OnClickListener {
    ViewPager nestedViewPager;
    Activity mActivity;
    ToggleVerticalViewPagerScrolling tv;
    NewsInterface newsInterface;
    Context mContext;
    ImageView imgForNoImgLink, imageView;
    Button seeDetails;
    public static String MY_PREFS_NAME = "myPrefString";
    String newLin,description,date,imgUrl,newLink;
    private InterstitialAd mInterstitialAd;
    private boolean ishowAds = true;
    FrameLayout nativeAdFrameLayout;
    private AdView mAdView;
    public String title;


    private VerticalFragment() {
        // Required empty public constructor
    }



    public static VerticalFragment newInstance(Datum dataModel, Context mContext) {
        VerticalFragment fragment = new VerticalFragment();
        Bundle args = new Bundle();
        args.putString("title", dataModel.getTitle());
        args.putString("description", dataModel.getDescription());
        args.putString("date", dataModel.getPublished());
        args.putString("imgUrl", dataModel.getImage());
        args.putString("newLink", dataModel.getFullLink());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        seeDetails = view.findViewById(R.id.seeDetails);
        ParentAdapeterNotofier parentAdapeterNotofier = (ParentAdapeterNotofier) mContext;
        parentAdapeterNotofier.notiyUpadate(true);

        if (getArguments() != null) {
            title = getArguments().getString("title");
            description = getArguments().getString("description");
            date = getArguments().getString("date");
            imgUrl = getArguments().getString("imgUrl");
            newLink = getArguments().getString("newLink");
        }
        nativeAdFrameLayout = view.findViewById(R.id.nativeAdFrameLayout);
        mAdView = view.findViewById(R.id.adViewNew);
//        BitmapDrawable background = ImagePreviewerUtils.getBlurredScreenDrawable(context, source.getRootView());
//        BitmapDrawable getBlurredScreenDrawable(Context context, View screen){
//            Bitmap screenshot = takeScreenshot(screen);


        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String newslink = prefs.getString("imageLink", "");//"No name defined" is the default value.
        Log.d("uuuuuuuuuuuuuu", "onCreateView: " + newslink);
        imageView = view.findViewById(R.id.imgNews);
        imgForNoImgLink = view.findViewById(R.id.imgForNoImgLink);
//       if(!TextUtils.equals("no-img",newslink)){
//           imageView.setVisibility(View.VISIBLE);
//           nativeAdFrameLayout.setVisibility(View.GONE);
//       }else {
//           imageView.setVisibility(View.GONE);
//           nativeAdFrameLayout.setVisibility(View.VISIBLE);
//       }
        nativeAdFrameLayout.setVisibility(View.GONE);


        Log.d("imageIsNull", "onCreateView: null" + imgUrl);


        try {
            loadImage(imageView, imgUrl);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextView newsDate = view.findViewById(R.id.newsDate);
        newsDate.setText(date);

        Log.d("Wesitelinkdsfs", "newInstanfadsfasce: " + newLink);
        TextView mytitle = view.findViewById(R.id.title);
        TextView mudescription = view.findViewById(R.id.description);
        mytitle.setText(title);
        mudescription.setText(description);

        seeDetails.setOnClickListener(this);
        Log.d("loading fragment", "oncreate");
        loadNativeAds();
        loadBannerAds();
        return view;
    }

    private void loadNativeAds() {

        if (BuildConfig.DEBUG) {
            final AdLoader adLoader = new AdLoader.Builder(mContext, "ca-app-pub-8562845792538150/1279984356")
                    .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            // Show the ad.

                            LayoutInflater inflater = LayoutInflater.from(mContext);
                            UnifiedNativeAdView adView = (UnifiedNativeAdView) inflater
                                    .inflate(R.layout.ad_unified, null);
                            displayUnifiedNativeAd(adView, unifiedNativeAd);
                            adView.setBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                            nativeAdFrameLayout.removeAllViews();
                            nativeAdFrameLayout.addView(adView);


                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Handle the failure by logging, altering the UI, and so on.
                            Log.d("native ads failed",String.valueOf(errorCode));
                            nativeAdFrameLayout.setVisibility(View.GONE);
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();
//            ca-app-pub-8562845792538150/1279984356
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            final AdLoader adLoader = new AdLoader.Builder(mContext, "ca-app-pub-3940256099942544/2247696110")
                    .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            // Show the ad.

                            LayoutInflater inflater = LayoutInflater.from(mContext);
                            UnifiedNativeAdView adView = (UnifiedNativeAdView) inflater
                                    .inflate(R.layout.ad_unified, null);
                            displayUnifiedNativeAd(adView, unifiedNativeAd);
                            adView.setBackgroundColor(mContext.getResources().getColor(R.color.soft_sky_blue));
                            nativeAdFrameLayout.removeAllViews();
                            nativeAdFrameLayout.addView(adView);


                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Handle the failure by logging, altering the UI, and so on.
                            nativeAdFrameLayout.setVisibility(View.GONE);
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }

    //set native ads mapping
    private void displayUnifiedNativeAd(UnifiedNativeAdView adView, UnifiedNativeAd ad) {
        TextView headlineView = adView.findViewById(R.id.ad_headline);
        headlineView.setText(ad.getHeadline());
        adView.setHeadlineView(headlineView);
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        mediaView.setMediaContent(ad.getMediaContent());
        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

        adView.setMediaView(mediaView);
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStoreView(adView.findViewById(R.id.ad_store));

        ((TextView) adView.getHeadlineView()).setText(ad.getHeadline());
        if (ad.getBody() == null) {
            adView.getBodyView().setVisibility(View.GONE);
        }
        ((TextView) adView.getHeadlineView()).setText(ad.getHeadline());
        ((TextView) adView.getBodyView()).setText(ad.getBody());
        ((Button) adView.getCallToActionView()).setText(ad.getCallToAction());
        NativeAd.Image icon = ad.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (ad.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(ad.getPrice());
        }

        if (ad.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(ad.getStore());
        }

        if (ad.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(ad.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (ad.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(ad.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(ad);


    }


    private void setUpToLoadAds() {

        mInterstitialAd = new InterstitialAd(mContext);
        // set the ad unit ID
        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(getString(R.string.inteterstial_test_ads));
        } else {
            mInterstitialAd.setAdUnitId(getString(R.string.inteterstial_real_ads));
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        if (!Util.isToShowAds(mContext)) {
            Util.updateSharePerfence(mContext);
            Log.d("loading ads", "return ads");
            mInterstitialAd = null;
            ishowAds = true;

        } else {
            // Load ads into Interstitial Ads
            ishowAds = false;
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    showInterstitial();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    ishowAds = true;
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    ishowAds = true;
                }

                @Override
                public void onAdClosed() {
                    ishowAds = true;
                    Util.setDefaultValueToNotShowAds(mContext);

                }
            });

        }


    }


    private void showInterstitial() {
        Log.d("loading ads", "load addd");
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            Log.d("loading ads", "show ads");
            Util.setDefaultValueToNotShowAds(mContext);

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seeDetails:
                //my_category_container
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (getActivity() instanceof CategoryActivity) {
                    Fragment fragment = fragmentManager.findFragmentById(R.id.my_category_container);
                    NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("newslink", newLink);
                    newsDetailFragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack("mainNewDetails");
                    if (fragment != null) {
                        fragmentTransaction.add(R.id.my_category_container, newsDetailFragment);
                        fragmentTransaction.commit();

                    } else {
                        fragmentTransaction.replace(R.id.my_category_container, newsDetailFragment);
                        fragmentTransaction.commit();

                    }
                } else if (getActivity() instanceof MainActivity) {
                    Fragment fragment = fragmentManager.findFragmentById(R.id.constraintMainLayout);
                    NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("newslink", newLink);
                    newsDetailFragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack("mainNewDetails");
                    if (fragment != null) {
                        fragmentTransaction.add(R.id.constraintMainLayout, newsDetailFragment);

                        fragmentTransaction.commit();

                    } else {
                        fragmentTransaction.replace(R.id.constraintMainLayout, newsDetailFragment);
                        fragmentTransaction.commit();

                    }
                }

                break;
        }
    }


    public interface ToggleVerticalViewPagerScrolling {
        void trigger(int page);

    }

    public void loadImage(final ImageView imageView, final String imgUrl) throws ExecutionException, InterruptedException {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                Bitmap chefBitmap = null;
                try {
                    chefBitmap = Glide.with(mContext)
                            .asBitmap()
                            .load(imgUrl)
                            .submit()
                            .get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Bitmap finalChefBitmap = chefBitmap;
                final Bitmap finalChefBitmap1 = chefBitmap;
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (finalChefBitmap != null) {
                                Bitmap blurred = blur(mContext, finalChefBitmap);
                                BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), finalChefBitmap);
                                imageView.setBackground(ob);
                                mAdView.setVisibility(View.VISIBLE);
//                            imageView.setImageBitmap(finalChefBitmap1);
                            } else {
//                                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loadingimage);
//                                Bitmap blurred = blur(mContext, bitmap);
//                                BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), bitmap);
//                                imageView.setBackground(ob);
////                                  imageView.setImageBitmap(bitmap);
                                mAdView.setVisibility(View.INVISIBLE);
                                nativeAdFrameLayout.setVisibility(View.VISIBLE);
                            }
                            // Stuff that updates the UI

                        }
                    });
                }
            }
        });


//        Glide.with(mContext)
//                .asBitmap().load(imgUrl)
//                .placeholder(mContext.getResources().getDrawable(R.drawable.loadingimage))
//                .listener(new RequestListener<Bitmap>() {
//                              @Override
//                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
////                                  Toast.makeText(mContext,"unexpected error ",Toast.LENGTH_SHORT).show();
//                                  return false;
//                              }
//
//                              @Override
//                              public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
////                                  Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loadingimage);
//                                  Bitmap blurred = blur(mContext, bitmap);
//                                  BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), blurred);
//                                  imageView.setBackground(ob);
////                                  imageView.setImageBitmap(bitmap);
//                                  return false;
//                              }
//                          }
//                ).submit();
//        Glide.with(imageView).load(imgUrl)
//                .apply(new RequestOptions()
//                        .fitCenter()
//                        .placeholder(mContext.getResources().getDrawable(R.drawable.loadingimage))
//                        .format(DecodeFormat.PREFER_ARGB_8888)
//                        .override(Target.SIZE_ORIGINAL))
//                .into(imageView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

    }

    private static Bitmap blur(Context context, Bitmap bitmap) {
        float bitmapScale = 0.3f;
        float blurRadius = 10f;

        int width = Math.round(bitmap.getWidth() * bitmapScale);
        int height = Math.round(bitmap.getHeight() * bitmapScale);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(blurRadius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    private void loadBannerAds() {
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            MobileAds.initialize(getActivity(), "ca-app-pub-8562845792538150/5341328929");

        } else {
            MobileAds.initialize(getActivity(), mContext.getString(R.string.banner_real_ads));

        }
        AdRequest request = new AdRequest.Builder().build();
        mAdView.loadAd(request);
    }

}

