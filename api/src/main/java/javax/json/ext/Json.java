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

package javax.json.ext;

import javax.json.JsonArray;
import javax.json.JsonConfiguration;
import javax.json.JsonFeature;
import javax.json.JsonObject;
import javax.json.ext.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.*;

/**
 *
 * @author Jitendra Kotamraju
 */
public class Json {

    public static JsonParser createParser(Reader reader) {
        return JsonProvider.provider().createParser(reader);
    }

    public static JsonParser createParser(Reader reader, JsonConfiguration config) {
        return JsonProvider.provider().createParser(reader, config);
    }

    public static JsonParser createParser(InputStream in) {
        return JsonProvider.provider().createParser(in);
    }

    public static JsonParser createParser(InputStream in, JsonConfiguration config) {
        return JsonProvider.provider().createParser(in, config);
    }

    public static JsonParser createParser(JsonObject obj) {
        return JsonProvider.provider().createParser(obj);
    }

    public static JsonParser createParser(JsonObject obj, JsonConfiguration config) {
        return JsonProvider.provider().createParser(obj, config);
    }

    public static JsonParser createParser(JsonArray array) {
        return JsonProvider.provider().createParser(array);
    }

    public static JsonParser createParser(JsonArray array, JsonConfiguration config) {
        return JsonProvider.provider().createParser(array, config);
    }


    public static JsonGenerator createGenerator(Writer writer) {
        return JsonProvider.provider().createGenerator(writer);
    }

    public static JsonGenerator createGenerator(Writer writer, JsonConfiguration config) {
        return JsonProvider.provider().createGenerator(writer, config);
    }

    public static JsonGenerator createGenerator(OutputStream out, String encoding) {
        return JsonProvider.provider().createGenerator(out, encoding);
    }

    public static JsonGenerator createGenerator(OutputStream out, String encoding, JsonConfiguration config) {
        return JsonProvider.provider().createGenerator(out, encoding, config);
    }


    private void testParser() {
        JsonParser parser = Json.createParser(new StringReader("[]"));
        parser.close();
    }

    private void testParserWithConfig() {
        JsonConfiguration config = new JsonConfiguration();
        JsonParser parser = Json.createParser(new StringReader("[]"), config);
        parser.close();
    }

    private void testGenerator() {
        JsonGenerator generator = Json.createGenerator(new StringWriter());
        generator.beginArray().endArray();
        generator.close();
    }

    private void testGeneratorWithConfig() {
        JsonConfiguration config = new JsonConfiguration().with(JsonFeature.PRETTY_PRINTING);
        JsonGenerator generator = Json.createGenerator(new StringWriter(), config);
        generator.beginArray().endArray();
        generator.close();
    }

}