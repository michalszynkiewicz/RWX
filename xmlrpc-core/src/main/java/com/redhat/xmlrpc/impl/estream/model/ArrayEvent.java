/*
 *  Copyright (C) 2010 John Casey.
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.redhat.xmlrpc.impl.estream.model;

import com.redhat.xmlrpc.vocab.EventType;
import com.redhat.xmlrpc.vocab.ValueType;

public class ArrayEvent
    implements Event<Integer>
{

    private final EventType eventType;

    private final int index;

    private final Object value;

    private final ValueType valueType;

    public ArrayEvent( final EventType eventType )
    {
        if ( EventType.START_ARRAY != eventType && EventType.END_ARRAY != eventType
            && EventType.END_ARRAY_ELEMENT != eventType )
        {
            throw new IllegalArgumentException( "Event type must be start/end for array or complex array element." );
        }

        this.eventType = eventType;
        index = -1;
        value = null;
        valueType = null;
    }

    public ArrayEvent( final int index )
    {
        eventType = EventType.START_ARRAY_ELEMENT;

        this.index = index;
        value = null;
        valueType = null;
    }

    public ArrayEvent( final int index, final Object value, final ValueType valueType )
    {
        this.index = index;
        this.value = value;
        this.valueType = valueType;

        eventType = EventType.ARRAY_ELEMENT;
    }

    @Override
    public EventType getEventType()
    {
        return eventType;
    }

    public int getIndex()
    {
        return index;
    }

    public Object getValue()
    {
        return value;
    }

    public ValueType getValueType()
    {
        return valueType;
    }

    @Override
    public boolean eventEquals( final EventType eType, final Integer key, final Object value, final ValueType vType )
    {
        if ( eventType != eType )
        {
            return false;
        }
        else if ( index > -1 && !Integer.valueOf( index ).equals( key ) )
        {
            return false;
        }
        else if ( valueType != null && valueType != vType )
        {
            return false;
        }
        else if ( this.value != null && !this.value.equals( value ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "ArrayEvent [eventType=" + eventType + ", index=" + index + ", value=" + value + ", valueType="
            + valueType + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( eventType == null ) ? 0 : eventType.hashCode() );
        result = prime * result + index;
        result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
        result = prime * result + ( ( valueType == null ) ? 0 : valueType.hashCode() );
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final ArrayEvent other = (ArrayEvent) obj;
        if ( eventType == null )
        {
            if ( other.eventType != null )
            {
                return false;
            }
        }
        else if ( !eventType.equals( other.eventType ) )
        {
            return false;
        }
        if ( index != other.index )
        {
            return false;
        }
        if ( value == null )
        {
            if ( other.value != null )
            {
                return false;
            }
        }
        else if ( !value.equals( other.value ) )
        {
            return false;
        }
        if ( valueType == null )
        {
            if ( other.valueType != null )
            {
                return false;
            }
        }
        else if ( !valueType.equals( other.valueType ) )
        {
            return false;
        }
        return true;
    }

}