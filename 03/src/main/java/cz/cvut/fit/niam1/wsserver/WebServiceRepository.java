package cz.cvut.fit.niam1.wsserver;

import https.courses_fit_cvut_cz.ni_am1.tutorials._02.index.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<BookingWithId> bookings = new ArrayList<>( );

    @PostConstruct
    public void init( ) {
        BookingWithoutId bWI1 = new BookingWithoutId( );

        bWI1.setTravelClass( TravelClass.ECONOMY );
        bWI1.setName( "Petr" );
        bWI1.setSurname( "Novak" );
        bWI1.setSex( Sex.M );

        try {
            XMLGregorianCalendar birthdate = DatatypeFactory.newInstance( )
                    .newXMLGregorianCalendar( "1997-08-28" );

            bWI1.setBirthDate( birthdate );

        } catch ( DatatypeConfigurationException e ) {
            e.printStackTrace( );
        }

        bWI1.setBirthPlace( "Prague, Czech Republic" );
        bWI1.setPersonalNumber( "970828/1234" );

        try {
            XMLGregorianCalendar departureDateTime = DatatypeFactory.newInstance( )
                    .newXMLGregorianCalendar( "2022-09-01T20:00:00" );

            bWI1.setDepartureDateTime( departureDateTime );

        } catch ( DatatypeConfigurationException e ) {
            e.printStackTrace( );
        }

        bWI1.setDepartureAirportCode( AirportCode.PRG );

        try {
            XMLGregorianCalendar arrivalDateTime = DatatypeFactory.newInstance( )
                    .newXMLGregorianCalendar( "2022-09-02T04:00:00" );

            bWI1.setArrivalDateTime( arrivalDateTime );

        } catch ( DatatypeConfigurationException e ) {
            e.printStackTrace( );
        }

        bWI1.setArrivalAirportCode( AirportCode.JFK );

        this.addBooking( bWI1 );
    }

    public List<BookingWithId> getBookings( ){
        return bookings;
    }

    public void addBooking( BookingWithoutId b ){
        BookingWithId bWI = new BookingWithId( );

        bWI.setId( bookings.size( ) );
        bWI.setBookingWithoutId( b );

        bookings.add( bWI );
    }

    public void updateBooking( int id, OptionalBookingWithoutId oBWI ){
        // loop through the bookings
        for ( BookingWithId bWI : bookings ) {
            // find the booking to update
            if ( bWI.getId( ) == id ) {
                // loop through the fields of new booking
                for ( Field newField : oBWI.getClass( ).getDeclaredFields( ) ) {
                    try {
                        // get the same field of old booking
                        Field oldField = bWI.getBookingWithoutId( ).getClass( ).getDeclaredField( newField.getName( ) );

                        newField.setAccessible( true );
                        oldField.setAccessible( true );

                        if ( newField.get( oBWI ) != "" && newField.get( oBWI ) != null ) {
                            // update the value of old field by a new one
                            oldField.set( bWI.getBookingWithoutId( ), newField.get( oBWI ) );
                        }
                    } catch ( NoSuchFieldException|IllegalAccessException e ) {
                        e.printStackTrace( );
                    }
                }

                return;
            }
        }
    }

    public void deleteBooking( int id ){
        bookings.removeIf( bWI -> ( bWI.getId( ) == id ) );
    }

}
