package com.ninjamind.conference.events;

import java.util.UUID;

/**
 * Tous les evenements sont au moins identifiés par un identifiant unique
 * @author ehret_g
 */
public abstract class AbstractEvent {
    /**
     * UUID
     */
    protected UUID key;

    protected AbstractEvent() {
        this.key = UUID.randomUUID();
    }

    public UUID getKey() {
        return key;
    }
}
