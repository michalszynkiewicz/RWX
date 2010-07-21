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

package com.redhat.xmlrpc.binding.testutil;

import static com.redhat.xmlrpc.binding.testutil.recipe.RecipeEventUtils.parameter;

import com.redhat.xmlrpc.binding.anno.Request;
import com.redhat.xmlrpc.binding.recipe.ArrayRecipe;
import com.redhat.xmlrpc.binding.recipe.FieldBinding;
import com.redhat.xmlrpc.binding.recipe.Recipe;
import com.redhat.xmlrpc.impl.estream.model.Event;
import com.redhat.xmlrpc.impl.estream.model.RequestEvent;
import com.redhat.xmlrpc.impl.estream.testutil.ExtList;
import com.redhat.xmlrpc.vocab.ValueType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Request( method = "getUser" )
public class InheritedPersonRequest
    extends SimplePersonRequest
{
    @Override
    public Map<Class<?>, Recipe<?>> recipes()
    {
        final Map<Class<?>, Recipe<?>> recipes = new HashMap<Class<?>, Recipe<?>>();

        final ArrayRecipe recipe = new ArrayRecipe( InheritedPersonRequest.class, new Integer[0] );
        recipe.addFieldBinding( 0, new FieldBinding( "userId", String.class, true ) );
        recipes.put( InheritedPersonRequest.class, recipe );

        return recipes;
    }

    @Override
    public List<Event<?>> events()
    {
        final ExtList<Event<?>> check = new ExtList<Event<?>>();

        check.withAll( new RequestEvent( true ), new RequestEvent( "getUser" ) );

        check.withAll( parameter( 0, "foo", ValueType.STRING ) );
        check.with( new RequestEvent( false ) );

        return check;
    }

}