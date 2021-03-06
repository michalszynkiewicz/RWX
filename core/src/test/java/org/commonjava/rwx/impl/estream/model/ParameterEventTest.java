/**
 * Copyright (C) 2010 Red Hat, Inc. (jdcasey@commonjava.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.rwx.impl.estream.model;

import static org.junit.Assert.assertEquals;

import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.estream.model.ParameterEvent;
import org.commonjava.rwx.impl.estream.testutil.ExtListOfArrays;
import org.commonjava.rwx.vocab.EventType;
import org.commonjava.rwx.vocab.ValueType;
import org.junit.Test;


import java.util.List;

public class ParameterEventTest
    extends AbstractEventTest<Integer>
{

    @Test
    public void matchParameterValueInfo()
    {
        final ParameterEvent event = new ParameterEvent( 0, "foo", ValueType.STRING );

        assertEquals( ValueType.STRING, event.getValueType() );
        assertEquals( "foo", event.getValue() );
        assertEquals( 0, event.getIndex() );
        assertEquals( EventType.PARAMETER, event.getEventType() );
    }

    @Test
    public void matchParameterStartInfo()
    {
        final ParameterEvent event = new ParameterEvent( 0 );

        assertEquals( EventType.START_PARAMETER, event.getEventType() );
        assertEquals( 0, event.getIndex() );
    }

    @Test
    public void matchEndParameter()
    {
        assertEvent( new ParameterEvent(), EventType.END_PARAMETER, null, null, null, true );
    }

    @Test
    public void mismatchStartVsEndParameterTypes()
    {
        assertEvent( new ParameterEvent( 0 ), EventType.END_STRUCT, null, null, null, false );
    }

    @Test
    public void mismatchParameterIndex()
    {
        assertEvent( new ParameterEvent( 0 ), EventType.START_PARAMETER, 1, null, null, false );
    }

    @Test
    public void matchParameterValue()
    {
        assertEvent( new ParameterEvent( 0, "foo", ValueType.STRING ), EventType.PARAMETER, 0, "foo", ValueType.STRING,
                     true );
    }

    @Test
    public void mismatchParameterValue()
    {
        assertEvent( new ParameterEvent( 0, "foo", ValueType.STRING ), EventType.PARAMETER, 0, "bar", ValueType.STRING,
                     false );
    }

    @Test
    public void matchParameterValueType()
    {
        assertEvent( new ParameterEvent( 0, "foo", ValueType.STRING ), EventType.PARAMETER, 0, "foo", ValueType.STRING,
                     true );
    }

    @Test
    public void mismatchParameterValueType()
    {
        assertEvent( new ParameterEvent( 0, "foo", ValueType.STRING ), EventType.PARAMETER, 0, "bar", ValueType.BASE64,
                     false );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    protected List<Event<Integer>[]> constructEqualInstanceSets()
    {
        return new ExtListOfArrays<Event<Integer>>( 2 ).with( new ParameterEvent(), new ParameterEvent() )
                                                       .with( new ParameterEvent( 0 ), new ParameterEvent( 0 ) )
                                                       .with( new ParameterEvent( 0, "foo", ValueType.STRING ),
                                                              new ParameterEvent( 0, "foo", ValueType.STRING ) );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    protected List<Event<Integer>[]> constructUnequalInstanceSets()
    {
        return new ExtListOfArrays<Event<Integer>>( 2 ).with( new ParameterEvent(), new ParameterEvent( 0 ) )
                                                       .with( new ParameterEvent( 0 ), new ParameterEvent( 1 ) )
                                                       .with( new ParameterEvent( 0, "foo", ValueType.STRING ),
                                                              new ParameterEvent( 0, "foo1", ValueType.STRING ) )
                                                       .with( new ParameterEvent(),
                                                              new ParameterEvent( 0, "foo", ValueType.STRING ) )
                                                       .with( new ParameterEvent( 0 ),
                                                              new ParameterEvent( 0, "foo", ValueType.STRING ) );
    }

}
