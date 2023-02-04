package tech.paranoidandroid.cucumber.sorting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tech.paranoidandroid.cucumber.json.Feature;
import tech.paranoidandroid.cucumber.json.support.StepObject;
import tech.paranoidandroid.cucumber.json.support.TagObject;

/**
 * Keeps references to classes that sort results.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public final class SortingFactory {

    private final SortingMethod sortingMethod;

    public SortingFactory(SortingMethod sortingMethod) {
        this.sortingMethod = sortingMethod;
    }

    public List<Feature> sortFeatures(Collection<Feature> features) {
        switch (sortingMethod) {
            case NATURAL:
                return new ArrayList<>(features);
            case ALPHABETICAL:
                return toSortedList(features, new FeaturesAlphabeticalComparator());
            default:
                throw createUnknownMethodException(sortingMethod);
        }
    }

    public List<TagObject> sortTags(Collection<TagObject> tags) {
        switch (sortingMethod) {
            case NATURAL:
                return new ArrayList<>(tags);
            case ALPHABETICAL:
                return toSortedList(tags, new TagObjectAlphabeticalComparator());
            default:
                throw createUnknownMethodException(sortingMethod);
        }
    }

    public List<StepObject> sortSteps(Collection<StepObject> steps) {
        switch (sortingMethod) {
            case NATURAL:
                return new ArrayList<>(steps);
            case ALPHABETICAL:
                return toSortedList(steps, new StepObjectAlphabeticalComparator());
            default:
                throw createUnknownMethodException(sortingMethod);
        }
    }

    private static <T> List<T> toSortedList(Collection<T> values, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(values);
        Collections.sort(list, comparator);
        return list;
    }

    private RuntimeException createUnknownMethodException(SortingMethod sortingMethod) {
        return new IllegalArgumentException("Unsupported sorting method: " + sortingMethod);
    }
}
