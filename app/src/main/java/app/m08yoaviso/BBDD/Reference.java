package app.m08yoaviso.BBDD;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Moises on 22/02/2016.
 */
public class Reference extends Application {

    private Firebase ref;


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://appyoaviso.firebaseio.com");
    }

    public Firebase getRef() {
        return ref;
    }

    public void setRef(Firebase ref) {
        this.ref = ref;
    }

}
