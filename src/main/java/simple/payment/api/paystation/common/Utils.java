package simple.payment.api.paystation.common;

import simple.payment.api.paystation.enumeration.PaymentState;

import static simple.payment.api.paystation.enumeration.PaymentState.REJECTED;
import static simple.payment.api.paystation.enumeration.PaymentState.TIMEOUT;

public class Utils {

    public static void someKindOfProcess(PaymentState state) throws InterruptedException, RejectedProcessException {

        Thread.sleep(500);

        if(state == TIMEOUT) {
            Thread.sleep(3000);
        }

        if(state == REJECTED || state == TIMEOUT ) {
            throw new RejectedProcessException();
        }


    }

}
