package org.kairos.protocol;

import org.kairos.protocol.generic.Message;
import org.kairos.protocol.message.*;

public enum MessageType
{
    MSG_PING( 255, Ping.class ), //  Control
    MSG_CONNECT( 1, Control.class ),
    MSG_DISCONNECT( 2, Control.class ),
    MSG_TIMEOUT( 3, Control.class ),
    MSG_ID_PUBLISHER( 10, Control.class ),
    MSG_ID_CONSUMER( 11, Control.class ),
    MSG_JOIN( 30, JoinTopic.class ),
    /*MSG_CREATE(TopicControl.class),*/
    MSG_LEAVE( 31, LeaveTopic.class ),
    MSG_PUBLISH( 40, Publish.class ),
    MSG_CONSUME( 41, Consume.class ),
    MSG_OK( 200, Status.class ),
    MSG_ERROR( 202, Status.class );

    private final int msgID;
    private final Class<? extends Message> msgPackager;

    MessageType( int msgCode, Class<? extends Message> msgPackager )
    {
        this.msgID = msgCode;
        this.msgPackager = msgPackager;
    }

    public int getID()
    {
        return msgID;
    }

    public Class<? extends Message> getMessagePackager()
    {
        return msgPackager;
    }

    public static MessageType fromId( int msgID )
    {
        for( MessageType type : values() )
        {
            if( type.getID() == msgID )
            {
                return type;
            }
        }

        throw new IllegalArgumentException( "Unknown MessageType id: " + msgID );
    }

    public static Class<? extends Message> getPackagerByID( int msgID )
    {
        MessageType msgType = MessageType.values()[ msgID ];
        return msgType.getMessagePackager();
    }
}
