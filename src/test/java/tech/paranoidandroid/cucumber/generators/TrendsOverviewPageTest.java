package tech.paranoidandroid.cucumber.generators;

import java.io.File;
import java.io.IOException;

import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class TrendsOverviewPageTest extends PageTest {

    private final String TRENDS_FILE = pathToSampleFile("cucumber-trends.json");
    private final String TRENDS_TMP_FILE = TRENDS_FILE + "-tmp";

    @Before
    public void setUp() throws IOException {
        setUpWithJson(SAMPLE_JSON);
        // refresh the file if it was already copied by another/previous test
        FileUtils.copyFile(new File(TRENDS_FILE), new File(TRENDS_TMP_FILE));
    }

    @Test
    public void getWebPage_ReturnsTrendsOverviewFileName() {

        // given
        page = new TrendsOverviewPage(reportResult, configuration, null);

        // when
        String fileName = page.getWebPage();

        // then
        assertThat(fileName).isEqualTo(TrendsOverviewPage.WEB_PAGE);
    }
}
