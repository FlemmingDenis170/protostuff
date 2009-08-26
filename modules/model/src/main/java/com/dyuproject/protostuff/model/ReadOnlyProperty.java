//================================================================================
//Copyright (c) 2009, David Yu
//All rights reserved.
//--------------------------------------------------------------------------------
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
// 1. Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
// 3. Neither the name of protostuff nor the names of its contributors may be used
//    to endorse or promote products derived from this software without
//    specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
//================================================================================


package com.dyuproject.protostuff.model;

import java.lang.reflect.InvocationTargetException;

import com.dyuproject.protostuff.util.PropertyAccessor.GetMethod;
import com.dyuproject.protostuff.util.PropertyAccessor.HasMethod;
import com.dyuproject.protostuff.util.PropertyAccessor.RepeatedHasMethod;

/**
 * @author David Yu
 * @created Aug 26, 2009
 */

public class ReadOnlyProperty  extends Property
{
    
    public static final Property.Factory FACTORY = new Property.Factory()
    {        
        public Property create(PropertyMeta propertyMeta)
        {
            return new ReadOnlyProperty(propertyMeta);
        }
    };
    
    private GetMethod _messageGet, _builderGet;
    private HasMethod _messageHas, _builderHas;
    
    public ReadOnlyProperty(PropertyMeta propertyMeta)
    {
        super(propertyMeta);
        
        _messageGet = new GetMethod();
        _builderGet = new GetMethod();
        
        if(propertyMeta.isRepeated())
        {
            _messageHas = new RepeatedHasMethod();
            _builderHas = new RepeatedHasMethod();                
        }
        else
        {
            _messageHas = new HasMethod();
            _builderHas = new HasMethod();   
        }
        
        _messageHas.init(propertyMeta, propertyMeta.getMessageClass());
        _messageGet.init(propertyMeta, propertyMeta.getMessageClass());
        
        _builderHas.init(propertyMeta, propertyMeta.getBuilderClass());
        _builderGet.init(propertyMeta, propertyMeta.getBuilderClass());
    }
    
    public Object getValue(Object target) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        if(_propertyMeta.getMessageClass()==target.getClass())
            return _messageHas.hasValue(target) ? _messageGet.getValue(target) : null;
        else if(_propertyMeta.getBuilderClass()==target.getClass())
            return _builderHas.hasValue(target) ? _builderGet.getValue(target) : null;
            
        return null;
    }
    
    public Object removeValue(Object target) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        throw new UnsupportedOperationException();
    }
    
    public boolean setValue(Object target, Object value) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        throw new UnsupportedOperationException();
    }
    
    public boolean replaceValueIfNone(Object target, Object value) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        throw new UnsupportedOperationException();
    }
    
    public Object replaceValueIfAny(Object target, Object value) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        throw new UnsupportedOperationException();
    }
}