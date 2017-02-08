package com.feedhenry.armark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.craftar.CraftARActivity;
import com.craftar.CraftARCloudRecognition;
import com.craftar.CraftARError;
import com.craftar.CraftARItem;
import com.craftar.CraftARResult;
import com.craftar.CraftARSDK;
import com.craftar.CraftARSearchResponseHandler;
import com.craftar.CraftARTracking;
import com.craftar.ImageRecognition;

import java.util.ArrayList;

public class ArAlmacenActivity extends CraftARActivity  implements
        CraftARSearchResponseHandler, ImageRecognition.SetCollectionListener, View.OnClickListener {

    private final String TAG = "RecognitionOnlyActivity";

    private View mScanningLayout;
    private View mTapToScanLayout;

    CraftARTracking mTracking;
    CraftARSDK mCraftARSDK;
    CraftARCloudRecognition mCloudIR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ar_almacen);
    }

    @Override
    public void onPostCreate() {

        View mainLayout= getLayoutInflater().inflate(R.layout.activity_ar_almacen, null);
        setContentView(mainLayout);

        mScanningLayout = findViewById(R.id.layout_scanning);
        mTapToScanLayout = findViewById(R.id.tap_to_scan);
        mTapToScanLayout.setOnClickListener(this);

        mTracking = CraftARTracking.Instance();

        mCraftARSDK = CraftARSDK.Instance();
        mCraftARSDK.startCapture(this);

    }

    @Override
    public void onPreviewStarted(int frameWidth, int frameHeight) {
        mCloudIR = CraftARCloudRecognition.Instance();
        mCloudIR.setCraftARSearchResponseHandler(this);
        mCloudIR.setCollection("a6c5beeffb524446", this);
        mCraftARSDK.setSearchController(mCloudIR.getSearchController());
    }

    @Override
    public void collectionReady() {
        mTapToScanLayout.setClickable(true);
    }

    @Override
    public void setCollectionFailed(CraftARError craftARError) {
        Log.d(TAG, "search failed! " + craftARError.getErrorMessage());
    }

    @Override
    public void searchResults(ArrayList<CraftARResult> results, long searchTime, int requestCode) {
        mCraftARSDK.getCamera().restartCapture();
        mScanningLayout.setVisibility(View.GONE);
        mTapToScanLayout.setVisibility(View.VISIBLE);

        if(results.size()==0){
            Log.d(TAG,"Nothing found");
            Toast.makeText(getBaseContext(),getString(R.string.recognition_only_toast_nothing_found), Toast.LENGTH_SHORT).show();
        }else{
            CraftARResult result = results.get(0);
            CraftARItem item = result.getItem();
            if (!item.isAR()) {
                String url = item.getUrl();
                if((url!= null)&&(! url.isEmpty())){
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(launchBrowser);
                }
            }
            Log.d(TAG,"Found item "+item.getItemName());
        }
    }

    @Override
    public void searchFailed(CraftARError craftARError, int i) {
        Log.d(TAG,"search failed!");
        Toast.makeText(getBaseContext(), getString(R.string.recognition_only_toast_nothing_found), Toast.LENGTH_SHORT).show();
        mScanningLayout.setVisibility(View.GONE);
        mTapToScanLayout.setVisibility(View.VISIBLE);
        mCraftARSDK.getCamera().restartCapture();
    }

    @Override
    public void onClick(View view) {
        if (view == mTapToScanLayout) {
            mTapToScanLayout.setVisibility(View.GONE);
            mScanningLayout.setVisibility(View.VISIBLE);
            mCraftARSDK.singleShotSearch();
        }
    }

    @Override
    public void onCameraOpenFailed(){
        Toast.makeText(getApplicationContext(), "Camera error", Toast.LENGTH_SHORT).show();
    }


}
