package simple.payment.api.paystation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import simple.payment.api.paystation.common.RejectedProcessException;
import simple.payment.api.paystation.common.Utils;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.CardApiCertifyCommand;
import simple.payment.api.paystation.out.CardApiCertifyRejectedView;
import simple.payment.api.paystation.out.CardApiCertifyView;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@Component
public class CertifyPayment {

    private final Random random = new Random();

    private PaymentState getCertifyRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, CERTIFIED, INVALID};
        return states[random.nextInt(states.length)];
    }

    public ResponseEntity<?> process(CardApiCertifyCommand command) {

        PaymentState state = getCertifyRandomState();
        LocalDateTime requestAt = LocalDateTime.now();

        try {
            Utils.someKindOfProcess(state);
            return ResponseEntity.ok(createCertifySuccessView(state, command, requestAt));
        } catch (RejectedProcessException | InterruptedException e) {
            return ResponseEntity.badRequest().body(createCertifyRejectedView(state, command, requestAt));
        }

    }


    private CardApiCertifyView createCertifySuccessView(
            PaymentState state,
            CardApiCertifyCommand command,
            LocalDateTime requestAt) {

        return new CardApiCertifyView(
                state,
                command.cardNumber(),
                command.customerName(),
                UUID.randomUUID().toString(),
                requestAt,
                LocalDateTime.now()
        );
    }

    private CardApiCertifyRejectedView createCertifyRejectedView(
            PaymentState state,
            CardApiCertifyCommand command,
            LocalDateTime requestAt) {

        String failReason = getCertifyFailReason(state);

        return new CardApiCertifyRejectedView(
                state,
                failReason,
                command.cardNumber(),
                command.customerName(),
                requestAt
        );
    }


    private String getCertifyFailReason(PaymentState state) {

        if(state == TIMEOUT) {
            return "거래 시간을 초과했습니다.";
        }

        String[] failReasons = new String[]{
                "카드 정보가 일치하지 않습니다.",
                "만료된 카드 정보입니다.",
                "분실신고된 카드입니다.",
                "정지된 카드입니다.",
                "CVV 코드가 일치하지 않습니다."
        };

        return failReasons[random.nextInt(failReasons.length)];
    }


}

