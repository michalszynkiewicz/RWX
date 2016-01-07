/**
 * Copyright (C) 2012 Red Hat, Inc. (jdcasey@commonjava.org)
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
package org.commonjava.rwx.binding.testutil;

import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.binding.mapping.Mapping;
import org.commonjava.rwx.binding.spi.Binder;
import org.commonjava.rwx.binding.spi.BindingContext;
import org.commonjava.rwx.binding.spi.value.AbstractSimpleValueBinder;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.spi.XmlRpcListener;
import org.commonjava.rwx.vocab.ValueType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class TestDateConverter
    extends AbstractSimpleValueBinder
{

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public TestDateConverter( final Binder parent, final Class<?> type, final BindingContext context )
    {
        super( parent, type, context );
    }

    @Override
    public void generate( final XmlRpcListener listener, final Object value, final Map<Class<?>, Mapping<?>> recipes )
        throws XmlRpcException
    {
        listener.value( new SimpleDateFormat( FORMAT ).format( value ), ValueType.DATETIME );
    }

    @Override
    public Binder value( final Object value, final ValueType type )
        throws XmlRpcException
    {
        if ( value == null || value.equals( "" ) )
        {
            return null;
        }

        final String strValue = String.valueOf( value );

        try
        {
            getParent().value( new SimpleDateFormat( FORMAT ).parse( strValue ), ValueType.DATETIME );
        }
        catch ( final ParseException e )
        {
            throw new BindException( "Conversion failed. Cannot parse date from: " + strValue + "\nReason: "
                + e.getMessage(), e );
        }

        return getParent();
    }

}
