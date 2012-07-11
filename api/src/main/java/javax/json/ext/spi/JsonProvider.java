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

package javax.json.ext.spi;

import org.glassfish.json.JsonProviderImpl;

import javax.json.JsonArray;
import javax.json.JsonConfiguration;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Service provider for JSON objects.
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonProvider {

    protected JsonProvider() {
    }

    /**
     *
     * Creates a JSON provider object.
     * <p>
     * The algorithm used to locate the provider subclass to use consists
     * of the following steps:
     * <p>
     * <ul>
     * <li>
     *   If a resource with the name of
     *   <code>META-INF/services/javax.json.ext.spi.JsonProvider</code>
     *   exists, then its first line, if present, is used as the UTF-8 encoded
     *   name of the implementation class.
     * </li>
     * <li>
     *   Otherwise, a default implementation class name is used.
     * </li>
     * </ul>
     *
     * @return a JSON provider
     */
    public static JsonProvider provider() {
        ServiceLoader<JsonProvider> loader = ServiceLoader.load(JsonProvider.class);
        Iterator<JsonProvider> it = loader.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return new JsonProviderImpl();
    }

    public abstract JsonParser createParser(Reader reader);

    public abstract JsonParser createParser(Reader reader, JsonConfiguration config);

    public abstract JsonParser createParser(InputStream in);

    public abstract JsonParser createParser(InputStream in, JsonConfiguration config);

    public abstract JsonParser createParser(JsonArray array);

    public abstract JsonParser createParser(JsonArray array, JsonConfiguration config);

    public abstract JsonParser createParser(JsonObject object);

    public abstract JsonParser createParser(JsonObject object, JsonConfiguration config);


    public abstract JsonGenerator createGenerator(Writer writer);

    public abstract JsonGenerator createGenerator(Writer writer, JsonConfiguration config);

    public abstract JsonGenerator createGenerator(OutputStream out, String encoding);

    public abstract JsonGenerator createGenerator(OutputStream out, String encoding, JsonConfiguration config);

}