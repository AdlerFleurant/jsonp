/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2013 Oracle and/or its affiliates. All rights reserved.
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

package javax.json;

import javax.json.stream.JsonGenerator;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Factory to create {@link javax.json.stream.JsonGenerator} instances. If a factory
 * instance is configured with some configuration, that would be
 * used to configure the created generator instances.
 *
 * <p>
 * {@link javax.json.stream.JsonGenerator} can also be created using {@link Json Json}'s
 * {@code createGenerator} methods. If multiple generator instances are created,
 * then creating them using a generator factory is preferred.
 *
 * <p>
 * <b>For example:</b>
 * <pre>
 * <code>
 * JsonGeneratorFactory factory = Json.createGeneratorFactory();
 * JsonGenerator generator1 = factory.createGenerator(...);
 * JsonGenerator generator2 = factory.createGenerator(...);
 * </code>
 * </pre>
 *
 * <p> All of the methods in this class are safe for use by multiple concurrent
 * threads.</p>
 *
 * @author Jitendra Kotamraju
 */
public interface JsonWriterFactory {

    /**
     * Creates a JSON generator which can be used to write JSON text to the
     * specified character stream. The created generator is
     * configured with the factory configuration.
     *
     * @param writer i/o writer to which JSON is written
     */
    JsonWriter createWriter(Writer writer);

    /**
     * Creates a JSON generator which can be used to write JSON text to the
     * specified byte stream. Characters written to the stream are encoded
     * into bytes using UTF-8 encoding. The created generator is
     * configured with the factory configuration.
     *
     * @param out i/o stream to which JSON is written
     */
    JsonWriter createWriter(OutputStream out);

    /**
     * Creates a JSON generator which can be used to write JSON text to the
     * specified byte stream. Characters written to the stream are encoded
     * into bytes using the specified charset. The created generator is
     * configured with the factory configuration.
     *
     * @param out i/o stream to which JSON is written
     * @param charset a charset
     */
    JsonWriter createWriter(OutputStream out, Charset charset);

    /**
     * Returns read-only map of supported provider specific configuration
     * properties that are used to configure the created JSON generators.
     * If there are any specified configuration properties that are not
     * supported by the provider, they won't be part of the returned map.
     *
     * @return a map of supported provider specific properties that are used
     * to configure the created generators; may be empty but not null.
     */
    Map<String, ?> getConfigInUse();

}