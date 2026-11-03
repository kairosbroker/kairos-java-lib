package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.TopicMessage;

import java.io.DataInputStream;
import java.io.IOException;

public final class Consume extends TopicMessage
{
    public Consume( String message )
    {
        super( MessageType.MSG_CONSUME, message );
    }

    public Consume( MessageType type, DataInputStream input ) throws IOException
    {
        super( type, input );
    }
}
