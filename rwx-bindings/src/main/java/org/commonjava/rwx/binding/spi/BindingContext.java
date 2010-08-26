/*
 *  Copyright (c) 2010 Red Hat, Inc.
 *  
 *  This program is licensed to you under Version 3 only of the GNU
 *  General Public License as published by the Free Software 
 *  Foundation. This program is distributed in the hope that it will be 
 *  useful, but WITHOUT ANY WARRANTY; without even the implied 
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 *  PURPOSE.
 *  
 *  See the GNU General Public License Version 3 for more details.
 *  You should have received a copy of the GNU General Public License 
 *  Version 3 along with this program. 
 *  
 *  If not, see http://www.gnu.org/licenses/.
 */

package org.commonjava.rwx.binding.spi;

import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.binding.mapping.FieldBinding;

import java.lang.reflect.Field;

public interface BindingContext
{

    Field findField( final FieldBinding binding, final Class<?> parentCls )
        throws BindException;

    Binder newBinder( final Binder parent, final Class<?> type )
        throws BindException;

    Binder newBinder( final Binder parent, final Field field )
        throws BindException;

}