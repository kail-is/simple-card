package simple.payment.api.paystation.in;

public record CardApiCancelCommand(
        String customerId,
        String paymentId,
        String transactionId
) {}

