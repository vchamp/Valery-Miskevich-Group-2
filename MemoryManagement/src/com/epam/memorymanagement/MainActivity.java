package com.epam.memorymanagement;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ImageGridFragment()).commit();
		}
	}

    @Override protected void onPause() {
        super.onPause();
        ImagesApplication.App.get().images().cleanCache();
    }
}
