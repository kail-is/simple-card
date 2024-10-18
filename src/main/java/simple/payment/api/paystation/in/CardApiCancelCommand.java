package simple.payment.api.paystation.in;

import java.util.UUID;

public record CardApiCancelCommand(
        String sellerId,
        UUID paymentId,
        UUID transactionId
) {}

