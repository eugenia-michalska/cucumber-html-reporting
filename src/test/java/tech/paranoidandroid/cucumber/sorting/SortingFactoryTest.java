package tech.paranoidandroid.cucumber.sorting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import mockit.Deencapsulation;
import org.junit.Before;
import org.junit.Test;

import tech.paranoidandroid.cucumber.generators.integrations.PageTest;
import tech.paranoidandroid.cucumber.json.Feature;
import tech.paranoidandroid.cucumber.json.support.StepObject;
import tech.paranoidandroid.cucumber.json.support.TagObject;
import tech.paranoidandroid.cucumber.ReportGenerator;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class SortingFactoryTest extends PageTest {

    @Before
    public void setUp() {
        setUpWithJson(ReportGenerator.SAMPLE_JSON);
    }

    @Test
    public void sortFeatures_OnNATURAL_ReturnsSameList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.NATURAL);

        // when
        List<Feature> featureList = sortingFactory.sortFeatures(features);

        // then
        assertThat(featureList).containsExactly(features.get(0), features.get(1));
    }

    @Test
    public void sortFeatures_OnALPHABETICAL_ReturnsSortedList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.ALPHABETICAL);

        // when
        List<Feature> featureList = sortingFactory.sortFeatures(features);

        // then
        assertThat(featureList).containsExactly(features.get(0), features.get(1));
    }

    @Test
    public void sortFeatures_OnINVALID_ThrowsException() {

        // given
        // INVALID is available only for test profile and the reason of this shadow Enum in test profile is
        // to be able to test default: block which throws an exception for unsupported values
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.INVALID);

        // when & then
        assertThatThrownBy(() -> sortingFactory.sortFeatures(features))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(buildErrorMessage());
    }

    @Test
    public void sortTags_OnNATURAL_ReturnsSameList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.NATURAL);

        // when
        List<TagObject> tagObjects = sortingFactory.sortTags(tags);

        // then
        // TODO: as the tags are stored in TreeMap, this returns already sorted elements
        assertThat(tagObjects).containsExactly(tags.get(0), tags.get(1), tags.get(2));
    }

    @Test
    public void sortTags_OnALPHABETICAL_ReturnsSameList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.ALPHABETICAL);

        // when
        List<TagObject> tagObjects = sortingFactory.sortTags(tags);

        // then
        assertThat(tagObjects).containsExactly(tags.get(0), tags.get(1), tags.get(2));
    }

    @Test
    public void sortTags_OnINVALID_ThrowsException() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.INVALID);

        // when & then
        assertThatThrownBy(() -> sortingFactory.sortTags(tags))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(buildErrorMessage());
    }

    @Test
    public void sortSteps_OnNATURAL_ReturnsSameList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.NATURAL);

        // when
        List<StepObject> stepObjects = sortingFactory.sortSteps(steps);

        // then
        assertThat(stepObjects).hasSize(16);
        assertThat(stepObjects).first().isEqualTo(steps.get(0));
        assertThat(stepObjects).last().isEqualTo(steps.get(15));
    }

    @Test
    public void sortSteps_OnALPHABETICAL_ReturnsSameList() {

        // given
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.ALPHABETICAL);

        // when
        List<StepObject> stepObjects = sortingFactory.sortSteps(steps);

        // then
        // TODO: as the tags are stored in TreeMap, this returns already sorted elements
        assertThat(stepObjects).hasSize(16);
        assertThat(stepObjects).first().isEqualTo(steps.get(0));
        assertThat(stepObjects).last().isEqualTo(steps.get(15));
    }

    @Test
    public void sortSteps_OnINVALID_ThrowsException() {

        // given
        SortingMethod sortingMethod = SortingMethod.INVALID;
        SortingFactory sortingFactory = new SortingFactory(sortingMethod);

        // when & then
        assertThatThrownBy(() -> sortingFactory.sortSteps(steps))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(buildErrorMessage());
    }

    @Test
    public void createUnknownMethodException_CreatesException() {

        // given
        SortingMethod invalidSorthingMethod = SortingMethod.ALPHABETICAL;
        SortingFactory sortingFactory = new SortingFactory(SortingMethod.ALPHABETICAL);

        // when
        Exception e = Deencapsulation.invoke(sortingFactory, "createUnknownMethodException", invalidSorthingMethod);

        // then
        assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Unsupported sorting method: " + invalidSorthingMethod);
    }

    private String buildErrorMessage() {
        return "Unsupported sorting method: " + SortingMethod.INVALID;
    }
}