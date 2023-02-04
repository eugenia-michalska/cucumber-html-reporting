package tech.paranoidandroid.cucumber.generators.integrations;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.paranoidandroid.cucumber.ReportBuilder;
import tech.paranoidandroid.cucumber.ReportGenerator;
import tech.paranoidandroid.cucumber.ValidationException;
import tech.paranoidandroid.cucumber.generators.AbstractPage;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.json.Output;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public abstract class PageTest extends ReportGenerator {

    protected AbstractPage page;

    protected DocumentAssertion documentFrom(String pageName) {
        File input = new File(configuration.getReportDirectory(),
                ReportBuilder.BASE_DIRECTORY + configuration.getDirectorySuffixWithSeparator() + File.separatorChar + pageName);
        try {
            return new DocumentAssertion(Jsoup.parse(input, StandardCharsets.UTF_8.name(), StringUtils.EMPTY));
        } catch (IOException e) {
            throw new ValidationException(e);
        }
    }

    protected String[] getMessages(Output[] outputs) {
        List<String> messages = new ArrayList<>();
        for (Output output : outputs) {
            messages.addAll(Arrays.asList(output.getMessages()));
        }

        return messages.toArray(new String[messages.size()]);
    }
}
