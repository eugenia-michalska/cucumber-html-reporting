package tech.paranoidandroid.cucumber.generators.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.LeadAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.TableRowAssertion;
import org.junit.Test;

import tech.paranoidandroid.cucumber.generators.StepsOverviewPage;
import tech.paranoidandroid.cucumber.presentation.PresentationMode;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StepsOverviewPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        configuration.setBuildNumber("333");
        page = new StepsOverviewPage(reportResult, configuration);
        final String titleValue = String.format("Cucumber Reports (no %s) - Steps Overview",
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
        page = new StepsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        LeadAssertion lead = document.getLead();

        assertThat(lead.getHeader()).isEqualTo("Steps Statistics");
        assertThat(lead.getDescription()).isEqualTo("The following graph shows step statistics for this build."
                + " Below list is based on results. step does not provide information about result then is not listed below."
                + " Additionally @Before and @After are not counted because they are part of the scenarios, not steps.");
    }

    @Test
    public void generatePage_generatesStatsTableHeader() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new StepsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion[] headerRows = document.getReport().getTableStats().getHeaderRows();

        assertThat(headerRows).hasSize(1);

        TableRowAssertion firstRow = headerRows[0];
        firstRow.hasExactValues("Implementation", "Occurrences", "Average duration", "Max duration", "Total durations", "Ratio");
    }

    @Test
    public void generatePage_generatesStatsTableFooter() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new StepsOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion footerCells = document.getReport().getTableStats().getFooterRow();

        footerCells.hasExactValues("16", "23", "4.325", "1:39.107", "1:39.492", "Totals");
    }
}
