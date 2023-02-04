package tech.paranoidandroid.cucumber.generators.integrations.helpers;

import tech.paranoidandroid.cucumber.json.DocString;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Fang Yuan (fayndee@github)
 */
public class DocStringAssertion extends ReportAssertion {

    public void hasDocString(DocString docString) {
        assertThat(docString).isNotNull();
        assertThat(oneBySelector("pre", WebAssertion.class).text()).isEqualTo(docString.getValue());
    }
}
