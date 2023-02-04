package tech.paranoidandroid.cucumber.json.deserializers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.json.Tag;

public class TagsDeserializer extends CucumberJsonDeserializer<Tag[]> {

    @Override
    protected Tag[] deserialize(JsonNode rootNode, Configuration configuration) {
        List<Tag> tags = new ArrayList<>();
        for (JsonNode tagNode : rootNode) {
            String tagName = tagNode.get("name").asText();
            if (shouldIncludeTag(tagName, configuration.getTagsToExcludeFromChart())) {
                tags.add(new Tag(tagName));
            }
        }

        return tags.toArray(new Tag[tags.size()]);
    }

    public boolean shouldIncludeTag(String tagName, Collection<Pattern> tagsToExcludeFromChart) {
        for (Pattern pattern : tagsToExcludeFromChart) {
            if (tagName.matches(pattern.pattern())) {
                return false;
            }
        }
        return true;
    }
}
