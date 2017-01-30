package com.feedhenry.armark;

import android.content.Context;
import android.util.Log;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;

/**
 * Created by ASUS on 20/10/2016.
 */
public class Inicio_feedHenry_sdk  {

    Context context;



    public void  InicializarFH (final Context context){



        FH.init(context, new FHActCallback() {
            @Override
            public void success(FHResponse pResponse) {

                CloudCall_Post cloudCall_post = new CloudCall_Post();
                cloudCall_post.Post_Promociones(context);
                cloudCall_post.Post_Almacen(context);
                cloudCall_post.Post_Categorias(context);

            }

            @Override
            public void fail(FHResponse pResponse) {
            Log.d("dato","fail");
                Log.e("error", pResponse.getErrorMessage(), pResponse.getError());
            }
        });

    }

}
