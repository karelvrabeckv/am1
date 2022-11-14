package cz.cvut.fit.niam1.wsserver;

import https.courses_fit_cvut_cz.ni_am1.tutorials._02.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/02/index.html", localPart = "getBookingsRequest" )
    @ResponsePayload
    public GetBookingsResponse getBookings( @RequestPayload GetBookingsRequest request ) {
        GetBookingsResponse response = new GetBookingsResponse( );

        response.getBookingWithId( ).addAll( repository.getBookings( ) );

        return response;
    }

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/02/index.html", localPart = "addBookingRequest" )
    @ResponsePayload
    public AddBookingResponse addBooking( @RequestPayload AddBookingRequest request ) {
        AddBookingResponse response = new AddBookingResponse( );

        repository.addBooking( request.getBookingWithoutId( ) );

        return response;
    }

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/02/index.html", localPart = "updateBookingRequest" )
    @ResponsePayload
    public UpdateBookingResponse updateBooking( @RequestPayload UpdateBookingRequest request ) {
        UpdateBookingResponse response = new UpdateBookingResponse( );

        OptionalBookingWithId oBWI = request.getOptionalBookingWithId( );
        repository.updateBooking( oBWI.getId( ), oBWI.getOptionalBookingWithoutId( ) );

        return response;
    }

    @PayloadRoot( namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/02/index.html", localPart = "deleteBookingRequest" )
    @ResponsePayload
    public DeleteBookingResponse deleteBooking( @RequestPayload DeleteBookingRequest request ) {
        DeleteBookingResponse response = new DeleteBookingResponse( );

        repository.deleteBooking( request.getId( ) );

        return response;
    }

}
