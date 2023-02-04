package tech.paranoidandroid.cucumber.json.deserializers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.json.support.Status;

/**
 * Deserializes Status and maps all known but not supported into UNDEFINED status.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StatusDeserializer extends CucumberJsonDeserializer<Status> {

    // https://github.com/cucumber/cucumber-js/blob/master/lib/cucumber/status.js
    static final List<String> UNKNOWN_STATUSES = Arrays.asList("ambiguous");

    @Override
    public Status deserialize(JsonNode rootNode, Configuration configuration) {

        String status = rootNode.asText();
        if (UNKNOWN_STATUSES.contains(status)) {
            return Status.UNDEFINED;
        } else {
            return Status.valueOf(status.toUpperCase(Locale.ENGLISH));
        }
    }
}
