package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCancelRejectView(
        PaymentState state,
        String failReason,
        String paymentId,
        LocalDateTime requestedAt
) {}
