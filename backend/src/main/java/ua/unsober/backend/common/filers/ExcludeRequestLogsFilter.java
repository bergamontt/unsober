package ua.unsober.backend.common.filers;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class ExcludeRequestLogsFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        return event.getMDCPropertyMap().containsKey("request.id") ? FilterReply.DENY : FilterReply.NEUTRAL;
    }

}
