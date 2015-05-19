package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Configures dependency injection.
 */

public abstract class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(EventDispatcher.class).toProvider(this::configureEventDispatcher);
    }

    /**
     * Creates instances of EventDispatcher
     * Override for the ability to pre-attatch any listeners.
     * @return A fresh dispatcher.
     */
    protected abstract EventDispatcher configureEventDispatcher();

}
