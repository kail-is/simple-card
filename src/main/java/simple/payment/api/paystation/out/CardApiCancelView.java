package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCancelView(
        PaymentState state,
        String paymentId,
        String transactionId,
        LocalDateTime requestAt,
        LocalDateTime canceledA
) {}
