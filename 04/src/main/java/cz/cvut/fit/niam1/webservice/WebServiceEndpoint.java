package cz.cvut.fit.niam1.webservice;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @Autowired
    private WebServiceClient client;

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "getPaymentsRequest" )
    @ResponsePayload
    public GetPaymentsResponse getPayments( @RequestPayload GetPaymentsRequest request ) {
        GetPaymentsResponse response = new GetPaymentsResponse( );

        response.getPaymentWithId( ).addAll( repository.getPayments( ) );

        return response;
    }

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "addPaymentRequest" )
    @ResponsePayload
    public AddPaymentResponse addPayment( @RequestPayload AddPaymentRequest request ) {
        AddPaymentResponse response = new AddPaymentResponse( );

        String cardNumber = request.getPaymentWithoutId( ).getCardNumber( );
        String cardOwner = request.getPaymentWithoutId( ).getCardOwner( );

        if ( client.validateCard( cardNumber, cardOwner ).isResult( ) ) {
            repository.addPayment( request.getPaymentWithoutId( ) );
        }

        return response;
    }

}
