package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.TopicControlMessage;

import java.io.DataInputStream;
import java.io.IOException;

public final class JoinTopic extends TopicControlMessage
{
    public JoinTopic( String message )
    {
        super( MessageType.MSG_JOIN, message );
    }

    public JoinTopic( MessageType type, DataInputStream input ) throws IOException
    {
        super( type, input );
    }
}
