package cn.kk.customview.activity.work.mediasession;

public enum MediaState {


    play(0),
    pause(1),
    pre(2),
    next(3);


    public int value;
    MediaState(int value) {
        this.value = value;
    }
}
