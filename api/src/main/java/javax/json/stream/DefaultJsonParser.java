/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
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

package javax.json.stream;

import org.glassfish.json.JsonParserImpl;

import javax.json.*;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

/**
 *
 * @author Jitendra Kotamraju
 */
public class DefaultJsonParser implements JsonParser {
    private final JsonParserImpl impl;

    public DefaultJsonParser(Reader reader) {
        impl = new JsonParserImpl(reader);
    }

    public DefaultJsonParser(Reader reader, JsonConfiguration config) {
        impl = new JsonParserImpl(reader);
    }

    public DefaultJsonParser(InputStream in) {
        impl = new JsonParserImpl(in);
    }

    public DefaultJsonParser(InputStream in, JsonConfiguration config) {
        impl = new JsonParserImpl(in);
    }

    public DefaultJsonParser(JsonObject object) {
        impl = new JsonParserImpl(object);
    }

    public DefaultJsonParser(JsonObject object, JsonConfiguration config) {
        impl = new JsonParserImpl(object);
    }

    public DefaultJsonParser(JsonArray array) {
        impl = new JsonParserImpl(array);
    }

    public DefaultJsonParser(JsonArray array, JsonConfiguration config) {
        impl = new JsonParserImpl(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString() {
        return impl.getString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNumber.JsonNumberType getNumberType() {
        return impl.getNumberType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNumber getNumber() {
        return impl.getNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends JsonValue> T getJsonValue(Class<T> clazz) {
        return impl.getJsonValue(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Event> iterator() {
        return impl.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        impl.close();
    }

    private void test() throws Exception {
        Reader reader = new StringReader("{}");
        JsonParser parser = new DefaultJsonParser(reader);
        for(Event event : parser) {
        }
        parser.close();
        reader.close();
    }

    private void test1() throws Exception {
        JsonObject object = new JsonBuilder().beginObject().endObject().build();
        JsonParser parser = new DefaultJsonParser(object);
        Iterator<Event> it = parser.iterator();
        Event event = it.next(); // START_OBJECT
        event = it.next();       // END_OBJECT
        parser.close();
    }

    private void test2() throws Exception {
        JsonArray array = new JsonBuilder().beginArray().endArray().build();
        JsonParser parser = new DefaultJsonParser(array);
        Iterator<Event> it = parser.iterator();
        Event event = it.next(); // START_ARRAY
        event = it.next();       // END_ARRAY
        parser.close();
    }

}