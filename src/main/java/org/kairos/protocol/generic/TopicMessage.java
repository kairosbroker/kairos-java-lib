package org.kairos.protocol.generic;

import org.kairos.protocol.MessageType;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class TopicMessage extends Message
{
    private final String msg;

    public TopicMessage( MessageType type, String message )
    {
        super( type );
        this.msg = message;
    }

    public TopicMessage( MessageType type, DataInputStream input ) throws IOException
    {
        super( type, input );

        int msgSizeParam = input.readInt();
        if( msgSizeParam < 0 || msgSizeParam > MAX_MSG_BODY )
        {
            throw new ProtocolException( "Message body size arrgument has invalid payload size" );
        }

        byte[] buffer = new byte[ msgSizeParam ];
        int msgSizeRead = input.read( buffer );

        if( msgSizeParam != msgSizeRead )
        {
            throw new ProtocolException( "payload size mismatch" );
        }

        this.msg = new String( buffer );
    }

    public final String getMessage()
    {
        return msg;
    }

    @Override
    public byte[] serialize()
    {
        int msgSize = ( Byte.SIZE / 8 ) + ( Integer.SIZE / 8 ) + getMessage().getBytes( StandardCharsets.UTF_8 ).length;
        ByteBuffer serializedMessage = ByteBuffer.allocate( msgSize );

        serializedMessage.put( (byte)getType().getID() );
        serializedMessage.putInt( getMessage().getBytes( StandardCharsets.UTF_8 ).length );
        serializedMessage.put( getMessage().getBytes( StandardCharsets.UTF_8 ) );

        return serializedMessage.array();
    }
}
