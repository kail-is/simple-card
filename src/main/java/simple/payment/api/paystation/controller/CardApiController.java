package simple.payment.api.paystation.controller;

import org.springframework.web.bind.annotation.*;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.*;
import simple.payment.api.paystation.out.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@RestController
@RequestMapping("/api/card")
public class CardApiController {

    private final Random random = new Random();

    @PostMapping("/certify")
    public CardApiCertifyView certifyPayment(@RequestBody CardApiCertifyCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getCertifyRandomState();
        someKindOfProcess(state);

        LocalDateTime certifiedAt = LocalDateTime.now();

        return new CardApiCertifyView(
                state,
                command.cardNumber(),
                command.customerName(),
                requestAt,
                certifiedAt
        );
    }


    @PostMapping("/pay")
    public CardApiApproveView approvePayment(@RequestBody CardApiApproveCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getApproveRandomState();
        someKindOfProcess(state);

        LocalDateTime approvedAt = LocalDateTime.now();

        return new CardApiApproveView(
                state,
                requestAt,
                approvedAt,
                command.price(),
                UUID.randomUUID()
        );
    }


    @PostMapping("/cancel")
    public CardApiCancelView cancelPayment(@RequestBody CardApiCancelCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getCancelRandomState();
        someKindOfProcess(state);

        LocalDateTime canceledAt = LocalDateTime.now();

        return new CardApiCancelView(
            state,
            command.paymentId(),
            command.transactionId(),
            requestAt,
            canceledAt
        );
    }


    private PaymentState getCertifyRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, FINISHED, REJECTED};
        return states[random.nextInt(states.length)];
    }

    private PaymentState getApproveRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, APPROVED};
        return states[random.nextInt(states.length)];
    }

    private PaymentState getCancelRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, FINISHED, REJECTED};
        return states[random.nextInt(states.length)];
    }

    private void someKindOfProcess(PaymentState state) throws InterruptedException {

        Thread.sleep(500);

        if(state == TIMEOUT) {
            Thread.sleep(3000);
        }

    }

}

