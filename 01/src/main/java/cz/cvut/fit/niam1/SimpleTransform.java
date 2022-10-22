package cz.cvut.fit.niam1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class SimpleTransform {

    @PostMapping(value = "/transform", consumes = "text/plain")
    String transform(@RequestBody String message) throws Exception {
        StringBuilder out = new StringBuilder();

        /* import the PLAINTEXT message */
        int start = message.indexOf("===") + 3;
        int end = message.indexOf("===", start);
        String data = message.substring(start, end);
        String[] parts = data.split("\"");
        String offset = "   ";

        /* export the JSON message */
        out.append("{" + System.lineSeparator());
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            if (i == 0) {
                /* id label */
                part = part.substring(part.indexOf(" ") + 1, part.indexOf(":"));
                part = offset + "\"" + part + "\": ";
            }
            else if (i == 1) {
                /* id data */
                part = "\"" + part + "\"," + System.lineSeparator();
            }
            else if (i == 2) {
                /* location label */
                part = part.substring(0, part.indexOf(":"));
                part = offset + "\"" + part.toLowerCase() + "\": ";
            }
            else if (i == 3) {
                /* location data */
                part = "\"" + part + "\"," + System.lineSeparator();
            }
            else if (i == 4) {
                /* person label */
                part = part.substring(0, part.indexOf(":"));
                part = offset + "\"" + part.toLowerCase() + "\": {" + System.lineSeparator();
            }
            else if (i == 5) {
                /* person data */
                String[] name = part.split(" ");

                /* person name */
                part = offset + offset + "\"name\": " + "\"" + name[0] + "\"," + System.lineSeparator();
                out.append(part);

                /* person surname */
                part = offset + offset + "\"surname\": " + "\"" + name[1] + "\"" + System.lineSeparator();
                out.append(part);

                part = offset + "}" + System.lineSeparator();
            }

            out.append(part);
        }
        out.append("}" + System.lineSeparator());

        return out.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleTransform.class, args);
    }

}
