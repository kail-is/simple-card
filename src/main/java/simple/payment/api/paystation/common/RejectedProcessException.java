package simple.payment.api.paystation.common;

public class RejectedProcessException extends RuntimeException {
    String message;


    public RejectedProcessException() {
        this.message = "fail";
    }

    public RejectedProcessException(String message) {
        this.message = message;
    }

}
