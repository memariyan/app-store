package ir.ac.iust.appstore.model.api;

/**
 * Created by Mohammad on 2016-10-29.
 */
public interface ApiTaskHandler
{
    void onSuccess(ApiCommunication apiCommunication);
    void onFailure(ApiCommunication apiCommunication, String reason);
}
