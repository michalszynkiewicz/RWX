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
package org.commonjava.rwx.binding.internal.xbr.helper;

import static org.commonjava.rwx.binding.anno.AnnotationUtils.getContainsType;

import org.apache.xbean.recipe.MapRecipe;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.internal.xbr.XBRBinderInstantiator;
import org.commonjava.rwx.binding.internal.xbr.XBRBindingContext;
import org.commonjava.rwx.binding.spi.Binder;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.spi.XmlRpcListener;
import org.commonjava.rwx.vocab.ValueType;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class MapBinder
    extends AbstractBinder
    implements Binder
{

    private final MapRecipe recipe;

    private String currentMember;

    private final Converter bindVia;

    private final Set<String> seenKeys = new HashSet<String>();

    public MapBinder( final Binder parent, final Class<?> mapType, final Field field, final XBRBindingContext context )
    {
        super( parent, getContainsType( field ), context );
        recipe = new MapRecipe( mapType );
        Converter bv = null;
        if ( field != null )
        {
            bv = field.getAnnotation( Converter.class );
            if ( bv == null )
            {
                bv = field.getType().getAnnotation( Converter.class );
            }
        }
        this.bindVia = bv;
    }

    @Override
    protected Binder endStructInternal()
        throws XmlRpcException
    {
        setValue( recipe.create(), ValueType.STRUCT );
        return this;
    }

    @Override
    protected Binder startStructMemberInternal( final String key )
        throws XmlRpcException
    {
        final Binder binder;
        if ( bindVia != null )
        {
            binder = XBRBinderInstantiator.newValueBinder( bindVia, this, getType(), getBindingContext() );
        }
        else
        {
            binder = getBindingContext().newBinder( this, getType() );
        }

        if ( binder != null )
        {
            currentMember = key;
            return binder;
        }

        return this;
    }

    @Override
    protected Binder valueInternal( final Object value, final ValueType type )
        throws XmlRpcException
    {
        if ( currentMember != null && !seenKeys.contains( currentMember ) )
        {
            recipe.put( currentMember, value );
            seenKeys.add( currentMember );
        }

        return this;
    }

    @Override
    public XmlRpcListener structMember( final String key, final Object value, final ValueType type )
        throws XmlRpcException
    {
        if ( !seenKeys.contains( key ) )
        {
            recipe.put( key, value );
            seenKeys.add( key );
        }

        return this;
    }

    @Override
    protected Binder endStructMemberInternal()
        throws XmlRpcException
    {
        currentMember = null;
        return this;
    }

}
