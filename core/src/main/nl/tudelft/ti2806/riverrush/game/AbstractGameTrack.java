package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

/**
 * Abstract representation of a game track.
 */
public class AbstractGameTrack implements GameTrack {

    private AbstractTeam leftTeam;
    private AbstractTeam rightTeam;

    protected Integer leftDistance;
    protected Integer rightDistance;

    private Integer trackLength;

    public AbstractGameTrack(AbstractTeam leftTeam, AbstractTeam rightTeam, Integer trackLength) {
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
        this.trackLength = trackLength;
        this.rightDistance = 0;
        this.leftDistance = 0;
    }

    @Override
    public Integer getDistanceLeftTeam() {
        return this.leftDistance;
    }

    @Override
    public Integer getDistanceRightTeam() {
        return this.rightDistance;
    }

    @Override
    public AbstractTeam getLeftTeam() {
        return this.leftTeam;
    }

    @Override
    public AbstractTeam getRightReam() {
        return this.rightTeam;
    }
}
