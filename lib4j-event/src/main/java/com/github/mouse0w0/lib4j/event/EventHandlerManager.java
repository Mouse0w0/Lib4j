package com.github.mouse0w0.lib4j.event;

import java.util.*;

public class EventHandlerManager implements EventDispatcher {

    private final Map<EventType<?>, CompositeEventHandler<?>> eventHandlers = new HashMap<>();

    public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<T> eventHandler) {
        getOrCreateEventHandler(eventType).getEventHandlers().add(eventHandler);
    }

    public <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<T> eventHandler) {
        getOrCreateEventHandler(eventType).getEventHandlers().remove(eventHandler);
    }

    public <T extends Event> EventHandler<T> getEventHandler(EventType<T> eventType) {
        return (EventHandler<T>) eventHandlers.get(eventType);
    }

    private <T extends Event> CompositeEventHandler getOrCreateEventHandler(EventType<T> eventType) {
        CompositeEventHandler<T> eventHandler = (CompositeEventHandler<T>) eventHandlers.get(eventType);
        if(eventHandler == null) {
            eventHandler = new CompositeEventHandler<>();
            eventHandlers.put(eventType, eventHandler);
        }
        return eventHandler;
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain eventDispatchChain) {
        eventHandlers.get(event.getEventType()).onRawEvent(event);
        return event;
    }
}
