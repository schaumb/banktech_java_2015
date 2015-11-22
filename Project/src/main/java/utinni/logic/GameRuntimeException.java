package utinni.logic;

public class GameRuntimeException extends RuntimeException {
    public GameRuntimeException(String s) {
        super(s);
    }

    public GameRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameRuntimeException(Throwable cause) {
        super(cause);
    }

    public GameRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GameRuntimeException() {
    }
}
