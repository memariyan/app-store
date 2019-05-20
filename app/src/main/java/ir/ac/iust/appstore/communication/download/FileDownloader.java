package ir.ac.iust.appstore.communication.download;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ir.ac.iust.appstore.model.api.BaseTaskHandler;

/**
 * Created by Mohammad on 6/16/2016.
 */
public class FileDownloader
{
    public static void downloadJSONFile(final Context context, final String urlAddress, final BaseTaskHandler handler)
    {
        new AsyncTask<Void, Void, Boolean>()
        {
            private File outFile;

            @Override
            protected Boolean doInBackground(Void... params)
            {
                int count;
                try
                {
                    ContextWrapper cw = new ContextWrapper(context);
                    File internalStorageDir = cw.getDir("media", Context.MODE_PRIVATE);
                    String outFilePath=internalStorageDir+"/map_file_info.json";
                    outFile=new File(outFilePath);

                    //download json file
                    URL url = new URL(urlAddress);
                    URLConnection conection = url.openConnection();
                    conection.connect();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    OutputStream output = new FileOutputStream(outFile);
                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1)
                        output.write(data, 0, count);

                    output.flush();
                    output.close();
                    input.close();

                    return true;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean successDownload)
            {
                if(successDownload)
                {
                    try
                    {
                        //load json file to json object
                        FileInputStream fis = new FileInputStream(outFile);
                        byte[] jsonData = new byte[(int) outFile.length()];
                        fis.read(jsonData);
                        fis.close();
                        JSONObject jsonObject=new JSONObject(new String(jsonData, "UTF-8"));
                        handler.onSuccess(jsonObject);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                    handler.onFailure("download_failed");

                //delete downloaded file
                if(outFile.exists())
                    outFile.delete();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
