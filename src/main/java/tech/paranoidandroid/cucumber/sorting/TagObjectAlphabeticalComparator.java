package tech.paranoidandroid.cucumber.sorting;

import java.util.Comparator;

import tech.paranoidandroid.cucumber.json.support.TagObject;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class TagObjectAlphabeticalComparator implements Comparator<TagObject> {

    @Override
    public int compare(TagObject tagObject1, TagObject tagObject2) {
        // since there might be the only one TagObject with given tagName, compare by location only
        return Integer.signum(tagObject1.getName().compareTo(tagObject2.getName()));
    }
}
