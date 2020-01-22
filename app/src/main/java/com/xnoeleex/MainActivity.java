package com.xnoeleex;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isHooked()){
            Toast.makeText(this, "XNoEleEx all in functional", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "XNoEleEx not in functional", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isHooked(){
        return false;
    }
}
