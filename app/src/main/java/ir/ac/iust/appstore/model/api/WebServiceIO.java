package ir.ac.iust.appstore.model.api;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mohammad on 2016-10-10.
 */
public interface WebServiceIO<T>
{
    enum MimeType
    {
        PROTOCOL_BUFFER ("application/x-protobuf"),

        JSON ("application/json;charset=utf-8"),

        XML ("application/xml"),

        FILE ("multipart/form-data"),

        TEXT_PLAIN("text/plain"),

        FORM("application/x-www-form-urlencoded");

        public String value;

        MimeType(String mimeType)
        {
            this.value =mimeType;
        }
    }

    MimeType getMimeType();

    void setData(T data);

    T getData();

    void writeTo(OutputStream outputStream) throws Exception;

    void readFrom(InputStream inputStream) throws Exception;
}
