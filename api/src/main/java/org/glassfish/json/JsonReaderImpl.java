/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.json;

import java.io.InputStream;
import java.io.Reader;
import javax.json.*;
import javax.json.stream.DefaultJsonParser;
import javax.json.stream.JsonParser;

/**
 * @author Jitendra Kotamraju
 */
public class JsonReaderImpl {
    private final Reader reader;
    private final InputStream in;

    public JsonReaderImpl(Reader reader) {
        this.reader = reader;
        this.in = null;
    }

    public JsonReaderImpl(InputStream in) {
        this.reader = null;
        this.in = in;
    }

    public JsonObject readObject() {
        return (JsonObject)read(JsonParser.Event.START_OBJECT);
    }

    public JsonArray readArray() {
        return (JsonArray)read(JsonParser.Event.START_ARRAY);
    }

    public JsonText readValue() {
        return read(null);
    }

    private JsonText read(JsonParser.Event firstEvent) {
        JsonParser parser = (reader != null)
                ? new DefaultJsonParser(reader)
                : new DefaultJsonParser(in);
        Object builder = new JsonBuilder();
        String key = null;
        try {
            for(JsonParser.Event e : parser) {
                if (builder instanceof JsonBuilder && firstEvent != null && firstEvent != e) {
                    throw new JsonException("Expecting "+firstEvent+" But got="+e);
                }
                switch (e) {
                    case START_ARRAY:
                        if (builder instanceof JsonBuilder) {
                            builder =  ((JsonBuilder)builder).beginArray();
                        } else if (builder instanceof JsonArrayBuilder) {
                            builder = ((JsonArrayBuilder)builder).beginArray();
                        } else {
                            builder = ((JsonObjectBuilder)builder).beginArray(key);
                        }
                        break;
                    case START_OBJECT:
                        if (builder instanceof JsonBuilder) {
                            builder =  ((JsonBuilder)builder).beginObject();
                        } else if (builder instanceof JsonArrayBuilder) {
                            builder = ((JsonArrayBuilder)builder).beginObject();
                        } else {
                            builder = ((JsonObjectBuilder)builder).beginObject(key);
                        }
                        break;
                    case KEY_NAME:
                        key = parser.getString();
                        break;
                    case VALUE_STRING:
                        String  string = parser.getString();
                        if (builder instanceof JsonArrayBuilder) {
                            ((JsonArrayBuilder)builder).add(string);
                        } else {
                            ((JsonObjectBuilder)builder).add(key, string);
                        }
                        break;
                    case VALUE_NUMBER:
                        JsonNumber number = parser.getNumber();
                        if (builder instanceof JsonArrayBuilder) {
                            ((JsonArrayBuilder)builder).add(number);
                        } else {
                            ((JsonObjectBuilder)builder).add(key, number);
                        }
                        break;
                    case VALUE_TRUE:
                        if (builder instanceof JsonArrayBuilder) {
                            ((JsonArrayBuilder)builder).add(true);
                        } else {
                            ((JsonObjectBuilder)builder).add(key, true);
                        }
                        break;
                    case VALUE_FALSE:
                        if (builder instanceof JsonArrayBuilder) {
                            ((JsonArrayBuilder)builder).add(false);
                        } else {
                            ((JsonObjectBuilder)builder).add(key, false);
                        }
                        break;
                    case VALUE_NULL:
                        if (builder instanceof JsonArrayBuilder) {
                            ((JsonArrayBuilder)builder).addNull();
                        } else {
                            ((JsonObjectBuilder)builder).addNull(key);
                        }
                        break;
                    case END_OBJECT:
                        builder = ((JsonObjectBuilder)builder).endObject();
                        break;
                    case END_ARRAY:
                        builder = ((JsonArrayBuilder)builder).endArray();
                        break;
                    default:
                        throw new JsonException("Unknown Event="+e);
                }
            }
        } finally {
            parser.close();
        }

        return ((JsonBuilder.JsonBuildable)builder).build();
    }

    public void close() {
    }
}
