package tech.paranoidandroid.cucumber.generators;

import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FailuresOverviewPageTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    public void getWebPage_ReturnsFailureReportFileName() {

        // given
        page = new FailuresOverviewPage(reportResult, configuration);

        // when
        String fileName = page.getWebPage();

        // then
        assertThat(fileName).isEqualTo(FailuresOverviewPage.WEB_PAGE);
    }
}
