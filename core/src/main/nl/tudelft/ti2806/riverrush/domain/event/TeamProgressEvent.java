package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Is fired when a team is a little further on the progressbar.
 */
public class TeamProgressEvent extends AbstractTeamEvent {

    private Double progress;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.serialize(protocol));
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("progress").append(protocol.getKeyValueSeperator()).append(this.progress.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        super.deserialize(keyValuePairs);
        if (keyValuePairs.containsKey("progress")) {
            this.progress = Double.parseDouble(keyValuePairs.get("progress"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    /**
     * @return the progress in an int between 0 and 100
     */
    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(final Double aProgress) {
        this.progress = aProgress;
    }
}
