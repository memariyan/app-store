package ir.ac.iust.appstore.communication.download;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Environment;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ir.ac.iust.appstore.model.FileDownload;

/**
 * Created by Mohammad on 7/8/2016.
 */
public class UserFileDownloader extends AsyncTask<Object, Void, Boolean>
{
    private static HashMap<Long, UserFileDownloader> fileDownloadingMap = new HashMap<>();//download cache : this cache is static for continue download in background

    //--------------------------------------------------------------variables and constructors---------------------------------------------------------------------
    private DownloadHandler downloadHandler;
    private FileDownload fileDownload;//current file download
    private InputStream in;
    private FileOutputStream fos;
    private BufferedOutputStream bout;
    private String downloadFileAddress;
    private boolean downloadCanceled;

    private UserFileDownloader(FileDownload fileDownload, DownloadHandler downloadHandler, Context context)
    {
        this.downloadHandler = downloadHandler;
        this.fileDownload = fileDownload;

        //check download resume or not
        downloadFileAddress = getStorageAddressFor(context, fileDownload);
        boolean resumeDownload = false;
        if (new File(downloadFileAddress).exists() && !fileDownload.getDownloadGroup().equals(FileDownload.DownloadGroup.APK))
            resumeDownload = true;

        //start download
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downloadFileAddress, resumeDownload);
    }

    public static String getStorageAddressFor(Context context, FileDownload fileDownload)
    {
        if(fileDownload.getDownloadGroup().equals(FileDownload.DownloadGroup.APK))
        {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+ "/" + fileDownload.getFileName();
        }
        else
        {
            File downloadFolder = new File(new ContextWrapper(context).getDir("media", Context.MODE_PRIVATE).getPath() + "/downloads/");
            if (!downloadFolder.exists())
                downloadFolder.mkdir();

            //check download resume or not
            return downloadFolder.getPath() + "/" + fileDownload.getFileName();
        }
    }

    @Override
    protected Boolean doInBackground(Object... params)
    {
        try
        {
            int downloaded = 0;
            boolean resumeDownload = (boolean) params[1];
            String fileAddress = (String) params[0];

            URL url = new URL(fileDownload.getDownloadUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (resumeDownload)
            {
                File file = new File(fileAddress);
                if (file.exists())
                {
                    downloaded = (int) file.length();
                    connection.setRequestProperty("Range", "bytes=" + (file.length()) + "-");
                }
            }
            else
                connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

            connection.setDoInput(true);
            in = new BufferedInputStream(connection.getInputStream());
            fos = (downloaded == 0) ? new FileOutputStream(fileAddress) : new FileOutputStream(fileAddress, true);
            bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int x = 0;
            while ((x = in.read(data, 0, 1024)) >= 0 && !downloadCanceled)
            {
                bout.write(data, 0, x);
                downloaded += x;
                downloadHandler.onProgress(downloaded);
            }
            bout.flush();
            bout.close();
            fos.close();
            in.close();

            return true;
        }
        catch (InterruptedIOException e)
        {
            try
            {
                bout.flush();
                bout.close();
                fos.close();
                in.close();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
        catch (Exception e)
        {
            downloadHandler.onFailure(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean successDownload)
    {
        if (successDownload)
        {
            downloadHandler.onSuccess(downloadFileAddress);
            System.out.println("success download....");
        }
    }

    public FileDownload getFileDownload()
    {
        return fileDownload;
    }

    public void setFileDownload(FileDownload fileDownload)
    {
        synchronized (fileDownload)
        {
            this.fileDownload = fileDownload;
        }
    }

    private void pauseDownload()
    {
        cancel(true);
        downloadCanceled = true;
    }

    //we can use multiple download handler in different situation then this method change handler with another download handler
    private void changeDownloadHandler(DownloadHandler downloadHandler)
    {
        synchronized (downloadHandler)
        {
            this.downloadHandler = downloadHandler;
        }
    }

    public static void pushAndStartDownload(FileDownload fileDownload, DownloadHandler downloadHandler, Context context)
    {
        fileDownloadingMap.put(fileDownload.getId(), new UserFileDownloader(fileDownload, downloadHandler, context));
    }

    public static void pauseDownload(long fileDownloadId)
    {
        if (fileDownloadingMap.containsKey(fileDownloadId))
        {
            fileDownloadingMap.get(fileDownloadId).pauseDownload();
            fileDownloadingMap.remove(fileDownloadId);
        }
    }

    //this method used when user switch between download activity and another activity
    public static void changeDownloadHandler(long fileDownloadId, DownloadHandler downloadHandler)
    {
        if (fileDownloadingMap.containsKey(fileDownloadId))
            fileDownloadingMap.get(fileDownloadId).changeDownloadHandler(downloadHandler);
    }

    public static void changeFileDownload(long fileDownloadId, FileDownload fileDownload)
    {
        if (fileDownloadingMap.containsKey(fileDownloadId))
            fileDownloadingMap.get(fileDownloadId).setFileDownload(fileDownload);
    }

    public static DownloadHandler getDownloadHandler(long fileDownloadId)
    {
        return fileDownloadingMap.containsKey(fileDownloadId) ? fileDownloadingMap.get(fileDownloadId).downloadHandler : null;
    }

    public static List<FileDownload> getRunningDownloads()
    {
        List<FileDownload> fileDownloads = new ArrayList<FileDownload>();
        Iterator<Map.Entry<Long, UserFileDownloader>> itr = fileDownloadingMap.entrySet().iterator();
        while (itr.hasNext())
        {
            Map.Entry<Long, UserFileDownloader> entry = itr.next();
            fileDownloads.add(entry.getValue().getFileDownload());
        }
        return fileDownloads;
    }
}
