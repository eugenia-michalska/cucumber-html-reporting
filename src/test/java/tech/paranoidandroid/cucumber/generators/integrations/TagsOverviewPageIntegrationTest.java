package tech.paranoidandroid.cucumber.generators.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.generators.TagsOverviewPage;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.LeadAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.SummaryAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.TableRowAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.WebAssertion;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class TagsOverviewPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);
        final String titleValue = String.format("Cucumber Reports  - Tags Overview",
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
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        LeadAssertion lead = document.getLead();

        assertThat(lead.getHeader()).isEqualTo("Tags Statistics");
        assertThat(lead.getDescription()).isEqualTo("The following graph shows passing and failing statistics for tags");
    }

    @Test
    public void generatePage_generatesCharts() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());

        Assertions.assertThat(document.byId("charts", WebAssertion.class)).isNotNull();
    }

    @Test
    public void generatePage_insertsChartData() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());

        // check that data used by the charts is correctly inserted into the script section

        assertThat(document.html()).contains("labels:  [ \"@checkout\",  \"@fast\",  \"@featureTag\", ]");
        assertThat(document.html()).contains("data:  [ 62.50,  100.00,  100.00, ]");
        assertThat(document.html()).contains("data:  [ 6.25,  0.00,  0.00, ]");
        assertThat(document.html()).contains("data:  [ 12.50,  0.00,  0.00, ]");
        assertThat(document.html()).contains("data:  [ 6.25,  0.00,  0.00, ]");
        assertThat(document.html()).contains("data:  [ 12.50,  0.00,  0.00, ]");

    }

    @Test
    public void generatePage_generatesStatsTableHeader() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion[] headerRows = document.getReport().getTableStats().getHeaderRows();

        assertThat(headerRows).hasSize(2);

        TableRowAssertion firstRow = headerRows[0];
        firstRow.hasExactValues("", "Scenarios", "Features");

        TableRowAssertion secondRow = headerRows[1];
        secondRow.hasExactValues("Tag", "Passed", "Failed", "Skipped", "Total", "Duration", "Status");
    }

    @Test
    public void generatePage_WithExculedTags_generatesStatsTableBody() {

        // given
        configuration.setTagsToExcludeFromChart("@checkout", "@feature.*");
        setUpWithJson(SAMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion[] bodyRows = document.getReport().getTableStats().getBodyRows();

        assertThat(bodyRows).hasSize(1);

        TableRowAssertion firstRow = bodyRows[0];
        firstRow.hasExactValues("@fast", "1", "0", "0", "1", "0.139", "Passed");
        firstRow.getReportLink().hasLabelAndAddress("@fast", "report-tag_2209724571.html");
    }

    @Test
    public void generatePage_onJsonWithoutTags_generatesProperMessage() {

        // given
        setUpWithJson(SIMPLE_JSON);
        page = new TagsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        SummaryAssertion summary = document.getReport();
        assertThat(summary.getEmptyReportMessage()).isEqualTo("You have no tags in your cucumber report");
    }
}
