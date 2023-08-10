package cn.kk.base.io;

public interface ObjectCallback<T> {
    void onResult(boolean success, T result);
}
