package cz.cvut.fit.niam1.wsserver;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet( ApplicationContext applicationContext ) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet( );
        servlet.setApplicationContext( applicationContext );
        servlet.setTransformWsdlLocations( true );
        return new ServletRegistrationBean( servlet, "/ws/*" );
    }

    @Bean( name = "flight-bookings" )
    public DefaultWsdl11Definition defaultWsdl11Definition( XsdSchema flightBookingsSchema ) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition( );
        wsdl11Definition.setPortTypeName( "FlightBookingsPort" );
        wsdl11Definition.setLocationUri( "/ws" );
        wsdl11Definition.setTargetNamespace( "https://courses.fit.cvut.cz/NI-AM1/tutorials/02/index.html" );
        wsdl11Definition.setSchema( flightBookingsSchema );
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema flightBookingsSchema( ) {
        return new SimpleXsdSchema( new ClassPathResource( "flight-bookings.xsd" ) );
    }

}
