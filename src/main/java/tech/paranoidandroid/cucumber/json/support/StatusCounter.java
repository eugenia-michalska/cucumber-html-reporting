package tech.paranoidandroid.cucumber.json.support;

import java.util.EnumMap;

/**
 * Keeps information about statuses occurrence.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StatusCounter {

    private EnumMap<Status, Integer> counter = new EnumMap<>(Status.class);

    /**
     * Initialized with {@link Status#SKIPPED}. Once {@link Status#FAILED} is reached, the finalStatus will not update.
     * Updates to {@link Status#UNDEFINED} and {@link Status#PENDING} are calculated into {@link Status#SKIPPED}
     */

    private Status finalStatus = Status.SKIPPED;

    private int size = 0;

    public StatusCounter(Resultsable[] resultsables) {
        this();
        for (Resultsable resultsable : resultsables) {
            Status status = resultsable.getResult().getStatus();

            if (status == Status.UNDEFINED || status == Status.PENDING) {
                incrementFor(Status.SKIPPED);

            } else {
                incrementFor(status);
            }
        }
    }

    public StatusCounter() {
        for (Status status : Status.values()) {
            counter.put(status, 0);
        }
    }

    /**
     * Increments finalStatus counter by single value.
     *
     * @param status finalStatus for which the counter should be incremented.
     */
    public void incrementFor(Status status) {
        final int statusCounter = getValueFor(status) + 1;
        this.counter.put(status, statusCounter);
        size++;

        if (finalStatus != Status.FAILED)
            finalStatus = status;
    }

    /**
     * Gets the number of occurrences for given status.
     *
     * @param status the status
     * @return number of occurrences for given status
     */
    public int getValueFor(Status status) {
        return this.counter.get(status);
    }

    /**
     * Gets the sum of all occurrences for all statuses.
     *
     * @return sum of all occurrences for all statuses
     */
    public int size() {
        return size;
    }

    /**
     * If statuses for all items are the same then this finalStatus is returned, otherwise {@link Status#FAILED}.
     *
     * @return final status for this counter
     */
    public Status getFinalStatus() {
        return finalStatus;
    }
}
