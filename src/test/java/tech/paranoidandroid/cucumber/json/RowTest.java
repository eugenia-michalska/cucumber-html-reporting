package tech.paranoidandroid.cucumber.json;

import static org.assertj.core.api.Assertions.assertThat;

import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class RowTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(SAMPLE_JSON);
    }

    @Test
    public void getCells_ReturnsCells() {

        // given
        Row[] rows = features.get(0).getElements()[0].getSteps()[2].getRows();

        // when
        String[] cells = rows[0].getCells();

        // then
        assertThat(rows).hasSize(5);
        assertThat(cells).containsExactly("Müller", "Deutschland");
    }

}
