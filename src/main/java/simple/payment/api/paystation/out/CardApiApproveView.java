package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiApproveView(
        PaymentState state,
        Long paymentAmount,
        String paymentId,
        String transactionId,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
) {}