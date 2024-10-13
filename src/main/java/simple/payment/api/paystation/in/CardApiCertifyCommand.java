package simple.payment.api.paystation.in;

public record CardApiCertifyCommand(
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany,
        String customerName
) {}

