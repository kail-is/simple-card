package simple.payment.api.paystation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import simple.payment.api.paystation.common.RejectedProcessException;
import simple.payment.api.paystation.common.Utils;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.CardApiCancelCommand;
import simple.payment.api.paystation.out.CardApiCancelRejectView;
import simple.payment.api.paystation.out.CardApiCancelView;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@Component
public class CancelPayment {


    public ResponseEntity<?> process(CardApiCancelCommand command) {

        PaymentState state = getCancelRandomState();
        LocalDateTime requestAt = LocalDateTime.now();

        try {
            Utils.someKindOfProcess(state);
            return ResponseEntity.ok(createCancelSuccessView(state, command, requestAt));
        } catch (RejectedProcessException | InterruptedException e) {
            return ResponseEntity.badRequest().body(createCancelRejectedView(state, command, requestAt));
        }

    }


    private CardApiCancelView createCancelSuccessView(
            PaymentState state,
            CardApiCancelCommand command,
            LocalDateTime requestAt) {

        LocalDateTime canceledAt = LocalDateTime.now();

        return new CardApiCancelView(
                state,
                command.paymentId(),
                command.transactionId(),
                UUID.randomUUID().toString(),
                requestAt,
                canceledAt
        );
    }

    private CardApiCancelRejectView createCancelRejectedView(
            PaymentState state,
            CardApiCancelCommand command,
            LocalDateTime requestAt) {

        String failReason = getCancelFailReason(state);

        return new CardApiCancelRejectView(
                state,
                failReason,
                command.paymentId(),
                requestAt
        );
    }


    private final Random random = new Random();
    private PaymentState getCancelRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, CANCELED, REJECTED};
        return states[random.nextInt(states.length)];
    }

    private String getCancelFailReason(PaymentState state) {

        if(state == TIMEOUT) {
            return "거래 시간을 초과했습니다.";
        }

        String[] failReasons = new String[]{
                "환불할 수 없는 거래입니다. 가맹점에 문의하세요.",
                "환불 가능 기한을 초과하였습니다.",
                "중복 환불을 시도했습니다."
        };
        return failReasons[random.nextInt(failReasons.length)];
    }


}

