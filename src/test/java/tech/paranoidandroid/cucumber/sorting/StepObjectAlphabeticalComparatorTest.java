package tech.paranoidandroid.cucumber.sorting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;

import tech.paranoidandroid.cucumber.json.support.StepObject;
import org.junit.Test;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StepObjectAlphabeticalComparatorTest {

    private final Comparator<StepObject> comparator = new StepObjectAlphabeticalComparator();

    @Test
    public void compareTo_OnDifferentLocation_ReturnsNoneZero() {

        // given
        StepObject step1 = new StepObject("one");
        StepObject step2 = new StepObject("two");

        // when
        int result = comparator.compare(step1, step2);

        // then
        assertThat(result).isNotZero();
    }

    @Test
    public void compareTo_OnSameLocation_ReturnsZero() {

        // given
        StepObject step1 = new StepObject("one");
        StepObject step2 = new StepObject("one");

        // when
        int result = comparator.compare(step1, step2);

        // then
        assertThat(result).isZero();
    }
}
