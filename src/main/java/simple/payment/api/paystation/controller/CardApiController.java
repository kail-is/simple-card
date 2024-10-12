package simple.payment.api.paystation.controller;

import org.springframework.web.bind.annotation.*;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.CardApiApproveCommand;
import simple.payment.api.paystation.out.CardApiApproveView;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@RestController
@RequestMapping("/api/card")
public class CardApiController {

    private final Random random = new Random();
    private PaymentState getPayRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, APPROVED};
        return states[random.nextInt(states.length)];
    }

    @PostMapping("/pay")
    public CardApiApproveView approvePayment(@RequestBody CardApiApproveCommand command) {
        PaymentState state = getPayRandomState();

        try {
            if(state == TIMEOUT){
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new CardApiApproveView(
                state,
                LocalDateTime.now(),
                command.price(),
                UUID.randomUUID()
        );
    }
}

