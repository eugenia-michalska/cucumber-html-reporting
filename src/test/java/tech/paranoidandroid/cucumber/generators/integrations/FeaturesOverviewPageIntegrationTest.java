package tech.paranoidandroid.cucumber.generators.integrations;

import tech.paranoidandroid.cucumber.generators.FeaturesOverviewPage;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.TableRowAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.WebAssertion;
import tech.paranoidandroid.cucumber.presentation.PresentationMode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FeaturesOverviewPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        configuration.setBuildNumber("1");
        page = new FeaturesOverviewPage(reportResult, configuration);
        final String titleValue = String.format("Cucumber Reports (no %s) - Features Overview",
                configuration.getBuildNumber());

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        String title = document.getHead().getTitle();

        assertThat(title).isEqualTo(titleValue);
    }

    @Test
    public void generatePage_generatesClassifications() {

        // given
        final String[] names = {"Platform", "Browser", "Branch", "Repository"};
        final String[] values = {"Win", "Opera", "master", "<a href=\"example.com\" rel=\"nofollow noopener noreferrer\">Example Repository</a>"};
        setUpWithJson(SAMPLE_JSON);
        for (int i = 0; i < names.length; i++) {
            configuration.addClassifications(names[i], values[i]);
        }
        page = new FeaturesOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion[] classifications = document.getClassifications();

        assertThat(classifications).hasSize(names.length);
        for (int i = 0; i < names.length; i++) {
            String[] cells = classifications[i].getCellsHtml();
            assertThat(cells).containsExactly(names[i], values[i]);
        }
    }

    @Test
    public void generatePage_generatesCharts() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new FeaturesOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());

        Assertions.assertThat(document.byId("charts", WebAssertion.class)).isNotNull();
    }

    @Test
    public void generatePage_generatesStatsTableHeader() {

        // given
        setUpWithJson(SAMPLE_JSON);
        page = new FeaturesOverviewPage(reportResult, configuration);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion[] headerRows = document.getReport().getTableStats().getHeaderRows();

        assertThat(headerRows).hasSize(2);

        TableRowAssertion firstRow = headerRows[0];
        firstRow.hasExactValues("", "Scenarios", "Features");

        TableRowAssertion secondRow = headerRows[1];
        secondRow.hasExactValues("Feature", "Passed", "Failed", "Skipped", "Total", "Duration", "Status");
    }
}
