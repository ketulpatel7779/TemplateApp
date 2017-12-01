package com.mastertemplate.callback;
/**
 * Listener used to dispatch on response events from @{@link com.mastertemplate.data.DataManager}.
 */
public interface OnResponse<T> {

    /** This method will be called when result is received from @{@link com.mastertemplate.data.DataManager}
     * @param response  response object
     */
    public void onSuccess(T response);

    /** This method will be called any error occurred during receiving data from @{@link com.mastertemplate.data.DataManager}
     * @param error  @{@link Throwable} object of error
     * @param msg generic error message
     */
    public void onError(Throwable error, String msg);
}