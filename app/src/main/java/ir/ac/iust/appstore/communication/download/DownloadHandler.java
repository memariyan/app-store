package ir.ac.iust.appstore.communication.download;

import ir.ac.iust.appstore.model.api.BaseTaskHandler;

/*this is a handler for execute specific task on download progress*/
public interface DownloadHandler extends BaseTaskHandler
{
    void onProgress(int downloaded);
}
