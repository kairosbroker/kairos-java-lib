package org.kairos.protocol.generic;

import org.kairos.protocol.MessageType;

import java.io.DataInputStream;
import java.nio.ByteBuffer;

public abstract class CodeMessage extends Message
{

    public CodeMessage( MessageType type )
    {
        super( type );
    }

    public CodeMessage( MessageType type, DataInputStream input )
    {
        super( type, input );
    }

    @Override
    public byte[] serialize()
    {
        int msgSize = ( Byte.SIZE / 8 );
        ByteBuffer serializedMessage = ByteBuffer.allocate( msgSize );

        serializedMessage.put( (byte)getType().getID() );

        return serializedMessage.array();
    }
}
