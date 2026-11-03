package org.kairos.protocol.message;

import org.kairos.protocol.MessageType;
import org.kairos.protocol.generic.CodeMessage;

import java.io.DataInputStream;

public class Status extends CodeMessage
{
    public Status( MessageType type )
    {
        super( type );
//        if (type.ordinal() != MessageType.MSG_OK.ordinal() && type.ordinal() != MessageType.MSG_ERROR.ordinal())
        if( type.getMessagePackager() != this.getClass() )
        {
            throw new IllegalArgumentException( "Invalid MessageType to Message package" );
        }
    }

    public Status( MessageType type, DataInputStream input )
    {
        super( type, input );
    }
}
