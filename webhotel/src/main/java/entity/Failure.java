package entity;

public class Failure {
    private int code;

//    @org.jetbrains.annotations.Contract(pure = true)
    public Failure(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
