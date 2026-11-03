package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.TopicControlMessage;

import java.io.DataInputStream;
import java.io.IOException;

public final class LeaveTopic extends TopicControlMessage
{
    public LeaveTopic( String message )
    {
        super( MessageType.MSG_LEAVE, message );
    }

    public LeaveTopic( MessageType type, DataInputStream input ) throws IOException
    {
        super( type, input );
    }
}
