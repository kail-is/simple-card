package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiApproveRejectView(
        PaymentState state,
        String failReason,
        LocalDateTime requestAt
) {}