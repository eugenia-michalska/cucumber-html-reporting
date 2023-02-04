package tech.paranoidandroid.cucumber.generators.integrations.helpers;

import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BuildInfoAssertion extends TableAssertion {

    public String getProjectName() {
        WebAssertion[] cells = getBodyRow().getCells();
        Assertions.assertThat(cells).isNotEmpty();
        return cells[0].text();
    }

    public String getBuildNumber() {
        WebAssertion[] cells = getBodyRow().getCells();
        Assertions.assertThat(cells).hasSizeGreaterThanOrEqualTo(2);
        return cells[1].text();
    }

    public void hasBuildDate(boolean withBuildNumber) {
        // date format: dd MMM yyyy, HH:mm
        WebAssertion[] cells = getBodyRow().getCells();
        Assertions.assertThat(cells).hasSize(withBuildNumber ? 3 : 2);
        assertThat(cells[withBuildNumber ? 2 : 1].text()).matches("^[0-3][0-9] \\w{3} \\d{4}, \\d{2}:\\d{2}$");
    }
}
