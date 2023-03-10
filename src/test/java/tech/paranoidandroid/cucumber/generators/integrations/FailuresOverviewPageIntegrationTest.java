package tech.paranoidandroid.cucumber.generators.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.LeadAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.SummaryAssertion;
import org.junit.Test;

import tech.paranoidandroid.cucumber.generators.FailuresOverviewPage;
import tech.paranoidandroid.cucumber.presentation.PresentationMode;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FailuresOverviewPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        configuration.setBuildNumber("1");
        page = new FailuresOverviewPage(reportResult, configuration);
        final String titleValue = String.format("Cucumber Reports (no %s) - Failures Overview",
                configuration.getBuildNumber());

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        String title = document.getHead().getTitle();

        assertThat(title).isEqualTo(titleValue);
    }

    @Test
    public void generatePage_generatesLead() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new FailuresOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        LeadAssertion lead = document.getLead();

        assertThat(lead.getHeader()).isEqualTo("Failures Overview");
        assertThat(lead.getDescription()).isEqualTo("The following summary displays scenarios that failed.");
    }

    @Test
    public void generatePage_onJsonWithoutFailedSteps_generatesProperMessage() {

        // given
        setUpWithJson(SIMPLE_JSON);
        page = new FailuresOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        SummaryAssertion summary = document.getReport();
        assertThat(summary.getEmptyReportMessage()).isEqualTo("You have no failed scenarios in your Cucumber report");
    }
}
