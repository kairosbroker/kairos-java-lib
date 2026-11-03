package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.CodeMessage;

public final class Ping extends CodeMessage
{
    public Ping()
    {
        super( MessageType.MSG_PING );
    }
}
