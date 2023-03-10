package tech.paranoidandroid.cucumber.json.support;

import java.util.ArrayList;
import java.util.List;

import tech.paranoidandroid.cucumber.Reportable;
import tech.paranoidandroid.cucumber.ValidationException;
import tech.paranoidandroid.cucumber.json.Element;
import tech.paranoidandroid.cucumber.json.Step;
import tech.paranoidandroid.cucumber.json.Tag;
import tech.paranoidandroid.cucumber.util.Util;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

public class TagObject implements Reportable {

    private final String tagName;
    private final List<Element> elements = new ArrayList<>();

    private final String reportFileName;
    private int scenarioCounter;
    private StatusCounter elementsStatusCounter = new StatusCounter();
    private StatusCounter stepsStatusCounter = new StatusCounter();
    private long totalDuration;
    private int totalSteps;

    /** Default status for current tag: {@link Status#PASSED} if all elements pass {@link Status#FAILED} otherwise. */
    private Status status = Status.PASSED;

    public TagObject(String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            throw new ValidationException("TagName cannot be null!");
        }
        this.tagName = tagName;

        this.reportFileName = Tag.generateFileName(tagName);
    }

    @Override
    public String getName() {
        return tagName;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public boolean addElement(Element element) {
        elements.add(element);

        if (status != Status.FAILED) {
            status = element.getStatus();
        }

        if (element.isScenario()) {
            scenarioCounter++;
        }

        elementsStatusCounter.incrementFor(element.getStatus());

        for (Step step : element.getSteps()) {
            stepsStatusCounter.incrementFor(step.getResult().getStatus());
            totalDuration += step.getDuration();
            totalSteps++;
        }
        return true;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public int getFeatures() {
        throw new NotImplementedException();
    }

    @Override
    public int getPassedFeatures() {
        throw new NotImplementedException();
    }

    @Override
    public int getFailedFeatures() {
        throw new NotImplementedException();
    }

    @Override
    public int getSkippedFeatures() {
        throw new NotImplementedException();
    }

    @Override
    public int getScenarios() {
        return scenarioCounter;
    }

    @Override
    public int getPassedScenarios() {
        return elementsStatusCounter.getValueFor(Status.PASSED);
    }

    @Override
    public int getFailedScenarios() {
        return elementsStatusCounter.getValueFor(Status.FAILED);
    }

    @Override
    public int getSkippedScenarios() {
        return elementsStatusCounter.getValueFor(Status.SKIPPED);
    }

    @Override
    public long getDuration() {
        return totalDuration;
    }

    @Override
    public String getFormattedDuration() {
        return Util.formatDuration(getDuration());
    }

    @Override
    public int getSteps() {
        return totalSteps;
    }

    public int getNumberOfStatus(Status status) {
        return stepsStatusCounter.getValueFor(status);
    }

    @Override
    public int getPassedSteps() {
        return getNumberOfStatus(Status.PASSED);
    }

    @Override
    public int getFailedSteps() {
        return getNumberOfStatus(Status.FAILED);
    }

    @Override
    public int getSkippedSteps() {
        return getNumberOfStatus(Status.SKIPPED);
    }

    @Override
    public int getUndefinedSteps() {
        return getNumberOfStatus(Status.UNDEFINED);
    }

    @Override
    public int getPendingSteps() {
        return getNumberOfStatus(Status.PENDING);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public String getRawStatus() {
        return status.name().toLowerCase();
    }
}
