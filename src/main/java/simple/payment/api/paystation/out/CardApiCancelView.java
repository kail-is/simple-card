package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardApiCancelView(
        PaymentState state,
        UUID paymentId,
        UUID transactionId,
        Long price,
        String sellerId,
        String canceledId,
        LocalDateTime requestAt,
        LocalDateTime canceledAt
) {}
