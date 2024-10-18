package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardApiCancelRejectView(
        PaymentState state,
        String failReason,
        UUID paymentId,
        LocalDateTime requestedAt
) {}
