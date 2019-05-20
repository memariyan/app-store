package ir.ac.iust.appstore.model;

import java.util.HashMap;
import java.util.Map;

import ir.ac.iust.appstore.R;

/*this class is a model for download a file in application and used as database model,download cache model and transfer model from tree structure to any class */

public class FileDownload
{
    //--------------------------------------------DownloadGroup and Status Enums----------------------------------------------------------------------

    /*this enum define kind of download file , for example download offline map file from server ...*/
    public enum DownloadGroup
    {
        OFFLINE_MAP(1),
        APK(2),
        UNDEFINED(0);

        public int value;

        DownloadGroup(int value)
        {
            this.value=value;
        }

        //------------------------------------Helper part for convert int value to correctness Enum value-----------------------------------------------
        private static final Map<Integer, DownloadGroup> intToTypeMap = new HashMap<Integer, DownloadGroup>();
        static
        {
            for ( DownloadGroup type :  DownloadGroup.values()) {
                intToTypeMap.put(type.value, type);
            }
        }

        public static DownloadGroup fromInt(int i) {
            DownloadGroup type = intToTypeMap.get(i);
            if (type == null)
                return DownloadGroup.UNDEFINED;
            return type;
        }
    }

    /*this enum define status of a file download */
    public enum Status
    {
        WAITING(1, R.string.download_status_prepared),
        DOWNLOADING(2,R.string.download_status_downloading),
        DOWNLOADED(3,R.string.download_status_downloaded),
        CANCELED(4,R.string.empty_content),
        PAUSED(5,R.string.download_status_paused),
        UNZIPPING(6,R.string.download_status_unzipping),
        UNDEFINED(0,R.string.empty_content );

        public int value;
        public int viewStringId;

        Status(int value,int viewStringId)
        {
            this.value=value;
            this.viewStringId=viewStringId;
        }

        //------------------------------------Helper part for convert int value to correctness Enum value-----------------------------------------------
        private static final Map<Integer, Status> intToTypeMap = new HashMap<Integer, Status>();
        static
        {
            for ( Status type :  Status.values()) {
                intToTypeMap.put(type.value, type);
            }
        }

        public static Status fromInt(int i) {
            Status type = intToTypeMap.get(i);
            if (type == null)
                return Status.UNDEFINED;
            return type;
        }
    }

    //--------------------------------------------------------variables and constructors----------------------------------------------------------------

    public FileDownload() {}

    public FileDownload(String viewName, String downloadUrl, String fileName, int size, DownloadGroup downloadGroup, Status status)
    {
        this.viewName = viewName;
        this.downloadUrl=downloadUrl;
        this.size=size;
        this.fileName=fileName;
        this.status=status;
        this.downloadGroup=downloadGroup;
    }

    private long id;
    private String viewName;
    private String downloadUrl;
    private String fileName;
    private int size;
    private int downloadedAmount;
    private Status status;
    private DownloadGroup downloadGroup;

    //----------------------------------------------------------Getter and Setter methods------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DownloadGroup getDownloadGroup() {
        return downloadGroup;
    }

    public void setDownloadGroup(DownloadGroup downloadGroup) {
        this.downloadGroup = downloadGroup;
    }

    public int getDownloadedAmount() {
        return downloadedAmount;
    }

    public void setDownloadedAmount(int downloadedAmount) {
        this.downloadedAmount = downloadedAmount;
    }

    /*two download object may be difference but in my opinion if two ids is equal , two object is equal ... do you have any idea :|*/
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof FileDownload)
            return ((FileDownload)obj).getId()==this.id;

        return false;
    }
}
