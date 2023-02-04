package tech.paranoidandroid.cucumber.generators;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.ReportBuilder;
import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FeaturesOverviewPageTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    public void getWebPage_ReturnsFeatureFileName() {

        // given
        page = new FeaturesOverviewPage(reportResult, configuration);

        // when
        String fileName = page.getWebPage();

        // then
        assertThat(fileName).isEqualTo(ReportBuilder.HOME_PAGE);
    }

    @Test
    public void prepareReport_AddsCustomProperties() {

        // given
        page = new FeaturesOverviewPage(reportResult, configuration);

        // when
        page.prepareReport();

        // then
        VelocityContext context = page.context;
        assertThat(context.getKeys()).hasSize(17);

        assertThat(context.get("all_features")).isEqualTo(features);
        assertThat(context.get("report_summary")).isEqualTo(reportResult.getFeatureReport());
    }
}