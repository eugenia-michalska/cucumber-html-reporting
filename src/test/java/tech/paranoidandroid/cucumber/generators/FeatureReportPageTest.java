package tech.paranoidandroid.cucumber.generators;

import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import tech.paranoidandroid.cucumber.json.Element;
import tech.paranoidandroid.cucumber.json.Embedding;
import tech.paranoidandroid.cucumber.json.Feature;
import tech.paranoidandroid.cucumber.json.Step;
import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class FeatureReportPageTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    public void getWebPage_ReturnsFeatureFileName() {

        // given
        Feature feature = features.get(1);
        page = new FeatureReportPage(reportResult, configuration, feature);

        // when
        String fileName = page.getWebPage();

        // then
        assertThat(fileName).isEqualTo(feature.getReportFileName());
    }

    @Test
    public void prepareReport_AddsCustomProperties() {

        // given
        Feature feature = features.get(1);
        page = new FeatureReportPage(reportResult, configuration, feature);

        // when
        page.prepareReport();

        // then
        VelocityContext context = page.context;
        assertThat(context.getKeys()).hasSize(14);
        assertThat(context.get("feature")).isEqualTo(feature);
    }

    @Test
    public void getMimeType_OnEmbeddingFromV2CucumberReportFile_SupportsScreenshots() {
        // given
        Feature feature = features.get(0);
        Element element = feature.getElements()[0];
        Step step = element.getSteps()[0];

        // when
        Embedding[] embeddings = step.getEmbeddings();

        // then
        assertThat(embeddings[0].getMimeType()).isEqualTo("image/url");
    }

    @Test
    public void getMimeType_OnEmbeddingFromV3CucumberReportFile_SupportsScreenshots() {
        // given
        Feature feature = features.get(0);
        Element element = feature.getElements()[0];
        Step step = element.getSteps()[0];

        // when
        Embedding[] embeddings = step.getEmbeddings();

        // then
        assertThat(embeddings[1].getMimeType()).isEqualTo("text/plain");
    }
}
