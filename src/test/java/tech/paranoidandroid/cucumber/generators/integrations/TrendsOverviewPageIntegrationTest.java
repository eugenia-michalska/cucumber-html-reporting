package tech.paranoidandroid.cucumber.generators.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import mockit.Deencapsulation;
import tech.paranoidandroid.cucumber.ReportBuilder;
import tech.paranoidandroid.cucumber.Trends;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.LeadAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.WebAssertion;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import tech.paranoidandroid.cucumber.generators.TrendsOverviewPage;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class TrendsOverviewPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        Trends trends = Deencapsulation.invoke(ReportBuilder.class, "loadTrends", TRENDS_FILE);
        page = new TrendsOverviewPage(reportResult, configuration, trends);
        final String titleValue = String.format("Cucumber Reports  - Trends Overview",
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
        Trends trends = Deencapsulation.invoke(ReportBuilder.class, "loadTrends", TRENDS_FILE);
        page = new TrendsOverviewPage(reportResult, configuration, trends);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        LeadAssertion lead = document.getLead();

        assertThat(lead.getHeader()).isEqualTo("Trends Statistics");
        assertThat(lead.getDescription()).isEqualTo("The following graph shows features, scenarios and steps for a period of time.");
    }

    @Test
    public void generatePage_generatesCharts() {

        // given
        setUpWithJson(SAMPLE_JSON);
        Trends trends = Deencapsulation.invoke(ReportBuilder.class, "loadTrends", TRENDS_FILE);
        page = new TrendsOverviewPage(reportResult, configuration, trends);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());

        // naive checking
        Assertions.assertThat(document.byId("trends-features-chart", WebAssertion.class)).isNotNull();
        assertThat(document.byId("trends-scenarios-chart", WebAssertion.class)).isNotNull();
        assertThat(document.byId("trends-steps-chart", WebAssertion.class)).isNotNull();
    }
}
