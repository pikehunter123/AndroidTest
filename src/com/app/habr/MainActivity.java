package com.app.habr;


import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	static int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 final Button button = (Button) findViewById(R.id.refrbutton);
	        button.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) // клик на кнопку
	            {
	                RefreshTemper();
	            }
	        });

	        RefreshTemper(); // при запуске грузим температуру сразу
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static void main(String[] args){
		 String temp = GetTemper("https://pogoda.yandex.ru/moscow/");
	}
	//----------------------------------------------------------------
    public static String GetTemper(String urlsite) // фукция загрузки температуры
    {
        String matchtemper = "zz"+(i++);
        try
        {
                // загрузка страницы
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder allpage = new StringBuilder();
            int n = 0;
            char[] buffer = new char[40000];
            while (n >= 0)
            {
                n = rd.read(buffer, 0, buffer.length);
                if (n > 0)
                {
                    allpage.append(buffer, 0, n);                    
                }
            }
            // работаем с регулярками
            System.out.println("---"+allpage.toString());
           /* final Pattern pattern = Pattern.compile
            ("<span style=\"color:#[a-zA-Z0-9]+\">[^-+0]+([-+0-9]+)[^<]+</span>[^(а-яА-ЯёЁa-zA-Z0-9)]+([а-яА-ЯёЁa-zA-Z ]+)");
            Matcher matcher = pattern.matcher(allpage.toString());
            if (matcher.find())
            {    
                matchtemper = matcher.group(1);            
            }*/        
            return matchtemper;
        }
        catch (Exception e)
        {
            
        }
        return matchtemper; 
    };
    //----------------------------------------------------------------
    public void RefreshTemper()
    {
    	DbAdapter dbb = new DbAdapter();
    	dbb.createDatabase(getApplicationContext() );
    	dbb.insValue(getApplicationContext());
    	dbb.getValue(getApplicationContext());
    	
        final TextView tTemper = (TextView) findViewById(R.id.temper);
         String bashtemp = "";
         new DownloadImageTask(tTemper)
         .execute("https://pogoda.yandex.ru/moscow/");
        
    };

}
