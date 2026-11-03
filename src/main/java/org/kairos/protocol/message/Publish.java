package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.TopicMessage;

import java.io.DataInputStream;
import java.io.IOException;

public final class Publish extends TopicMessage
{
    public Publish( String message )
    {
        super( MessageType.MSG_PUBLISH, message );
    }

    public Publish( MessageType type, DataInputStream input ) throws IOException
    {
        super( type, input );
    }
}
