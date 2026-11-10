package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.CodeMessage;

import java.io.DataInputStream;

public final class Ping extends CodeMessage
{
    public Ping()
    {
        super( MessageType.MSG_PING );
    }

    public Ping( MessageType type, DataInputStream input )
    {
        super( MessageType.MSG_PING, input );
    }
}
