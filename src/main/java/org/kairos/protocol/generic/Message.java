package org.kairos.protocol.generic;

import org.kairos.protocol.MessageType;

import java.io.DataInputStream;
import java.lang.reflect.InvocationTargetException;

public abstract class Message
{

    protected final MessageType msgType;
    static final int MAX_MSG_BODY = 1008;
    static final int MAX_TOPIC_NAME = 64;

    public Message( MessageType type )
    {
        if( type.getID() < 0 || type.getID() > 255 )
        {
            throw new IllegalArgumentException( "MessageType ID out-of-bounds [0 - 255]" );
        }
        this.msgType = type;
    }

    public Message( MessageType type, DataInputStream input )
    {
        this.msgType = type;
    }

    public final MessageType getType()
    {
        return msgType;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + ":" + getType().toString();
    }

    public abstract byte[] serialize();

    public static Message deserialize( int msgID, DataInputStream input ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        MessageType msgType = MessageType.fromId( msgID );
        Class<? extends Message> msgPackager = msgType.getMessagePackager();
        return msgPackager.getDeclaredConstructor( MessageType.class, DataInputStream.class ).newInstance( msgType,
                                                                                                           input
        );
    }
}
