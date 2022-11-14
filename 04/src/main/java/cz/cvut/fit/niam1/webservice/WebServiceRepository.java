package cz.cvut.fit.niam1.webservice;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<PaymentWithId> payments = new ArrayList<>( );

    public List<PaymentWithId> getPayments( ){
        return payments;
    }

    public void addPayment( PaymentWithoutId p ){
        PaymentWithId pWI = new PaymentWithId( );

        pWI.setId( payments.size( ) );
        pWI.setPaymentWithoutId( p );

        payments.add( pWI );
    }

}
