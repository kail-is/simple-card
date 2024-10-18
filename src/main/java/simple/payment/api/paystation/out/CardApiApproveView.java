package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardApiApproveView(
        PaymentState state,
        Long price,
        String sellerId,
        UUID paymentId,
        UUID transactionId,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
) {}