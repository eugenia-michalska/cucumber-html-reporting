package tech.paranoidandroid.cucumber.generators.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.json.Step;
import org.junit.Test;

import tech.paranoidandroid.cucumber.generators.TagReportPage;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.BriefAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.DocumentAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.ElementAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.LinkAssertion;
import tech.paranoidandroid.cucumber.generators.integrations.helpers.TableRowAssertion;
import tech.paranoidandroid.cucumber.json.support.TagObject;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class TagReportPageIntegrationTest extends PageTest {

    @Test
    public void generatePage_generatesTitle() {

        // given
        setUpWithJson(SAMPLE_JSON);
        final TagObject tag = tags.get(0);
        page = new TagReportPage(reportResult, configuration, tag);
        final String titleValue = String.format("Cucumber Reports  - Tag: %s", tag.getName());

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        String title = document.getHead().getTitle();

        assertThat(title).isEqualTo(titleValue);
    }

    @Test
    public void generatePage_generatesStatsTableBody() {

        // given
        setUpWithJson(SAMPLE_JSON);
        final TagObject tag = tags.get(0);
        page = new TagReportPage(reportResult, configuration, tag);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        TableRowAssertion bodyRow = document.getReport().getTableStats().getBodyRow();

        bodyRow.hasExactValues(tag.getName(), "1", "1", "0", "2", "0.231", "Failed");
    }

    @Test
    public void generatePage_generatesFeatureNames() {

        // given
        setUpWithJson(SAMPLE_JSON);
        final TagObject tag = tags.get(1);
        page = new TagReportPage(reportResult, configuration, tag);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        ElementAssertion[] elements = document.getElements();

        LinkAssertion featureName = elements[0].getFeatureName();
        featureName.hasLabelAndAddress(features.get(0).getName(), features.get(0).getReportFileName());
    }

    @Test
    public void generatePage_generatesTagsList() {

        // given
        setUpWithJson(SAMPLE_JSON);
        final TagObject tag = tags.get(0);
        page = new TagReportPage(reportResult, configuration, tag);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());
        ElementAssertion[] elements = document.getElements();

        assertThat(elements).hasSameSizeAs(tags.get(0).getElements());
    }

    @Test
    public void generatePage_generatesSteps() {

        // given
        setUpWithJson(SAMPLE_JSON);
        final TagObject tag = tags.get(1);
        page = new TagReportPage(reportResult, configuration, tag);

        // when
        page.generatePage();

        // then
        DocumentAssertion document = documentFrom(page.getWebPage());

        BriefAssertion stepElement = document.getElements()[0].getStepsSection().getSteps()[3].getBrief();
        Step step = tag.getElements().get(0).getSteps()[3];
        assertThat(stepElement.getName()).hasSameSizeAs(step.getName());
    }
}
