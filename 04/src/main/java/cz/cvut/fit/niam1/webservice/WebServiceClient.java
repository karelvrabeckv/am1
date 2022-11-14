package cz.cvut.fit.niam1.webservice;

import https.courses_fit_cvut_cz.ni_am1.hw.web_services.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class WebServiceClient extends WebServiceGatewaySupport {

    public ValidateCardResponse validateCard( String cardNumber, String cardOwner ) {
        ValidateCardRequest request = new ValidateCardRequest( );

        request.setCardNumber( cardNumber );
        request.setCardOwner( cardOwner );

        return ( ValidateCardResponse ) getWebServiceTemplate( ).marshalSendAndReceive( request );
    }

}
