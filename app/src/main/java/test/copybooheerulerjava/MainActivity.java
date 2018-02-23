package test.copybooheerulerjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Ruler mRuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* RulerView rulerView = (RulerView) findViewById(R.id.ruler);

        mRuler = new Ruler(this);
        mRuler.setInterval(15);
        mRuler.setMinScale(30);
        mRuler.setMaxScale(80);
        mRuler.setScaleColor(Color.RED);
        mRuler.setScaleTextColor(Color.GREEN);
        mRuler.setSmallerScaleHight(10);
        mRuler.setBiggerScaleHight(20);
        mRuler.setSpaceBetween(15);

        rulerView.setRulerStyle(mRuler);*/
    }
}
