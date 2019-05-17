package ir.ac.iust.appstore.model.api;

import java.io.Serializable;

/*this is an interface for do async task and contain two state one of them is success that fire after success operation and another is failure that fire after failed operation*/

public interface BaseTaskHandler extends Serializable
{
    void onSuccess(Object... results); //inputs may be contain variables that produced after async task
    void onFailure(String reason); //reason input is the explain of raised error
}
