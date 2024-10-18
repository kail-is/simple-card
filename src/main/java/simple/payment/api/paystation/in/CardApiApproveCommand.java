package simple.payment.api.paystation.in;

public record CardApiApproveCommand(
        String sellerId,
        int installmentPeriod,
        Long price,
        String cardIdentityCertifyNumber,
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany
) {}