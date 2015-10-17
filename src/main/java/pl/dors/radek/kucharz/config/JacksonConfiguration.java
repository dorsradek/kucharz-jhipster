package pl.dors.radek.kucharz.config;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dors.radek.kucharz.domain.util.CustomDateTimeDeserializer;
import pl.dors.radek.kucharz.domain.util.CustomDateTimeSerializer;
import pl.dors.radek.kucharz.domain.util.CustomLocalDateSerializer;
import pl.dors.radek.kucharz.domain.util.ISO8601LocalDateDeserializer;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();
        module.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        module.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new CustomLocalDateSerializer());
        module.addDeserializer(LocalDate.class, new ISO8601LocalDateDeserializer());
        return module;
    }
}
