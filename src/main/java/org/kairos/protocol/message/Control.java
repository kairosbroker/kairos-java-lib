package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.CodeMessage;

import java.io.DataInputStream;

public final class Control extends CodeMessage
{
    public Control( MessageType type )
    {
        super( type );
        if( type.getMessagePackager() != this.getClass() )
        {
            throw new IllegalArgumentException( "Invalid MessageType to Message package" );
        }
    }

    public Control( MessageType type, DataInputStream input )
    {
        super( type, input );
    }
}
