/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.takes.facets.fallback;

import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.Response;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;
import org.takes.rs.RsText;
import org.takes.tk.TkFailure;

/**
 * Test case for {@link TkFallback}.
 * @author Dmitry Zaytsev (dmitry.zaytsev@gmail.com)
 * @version $Id$
 * @since 0.9.6
 */
public final class TkFallbackTest {
    /**
     * TkFallback can fall back.
     * @throws IOException If some problem inside
     */
    @Test
    public void fallsBack() throws IOException {
        final String err = "message";
        MatcherAssert.assertThat(
            new RsPrint(
                new TkFallback(
                    new TkFailure(err),
                    new Fallback() {
                        @Override
                        public Response act(final RqFallback req) {
                            return new RsText(
                                req.throwable().getMessage()
                            );
                        }
                    }
                ).act(new RqFake("GET"))
            ).printBody(),
            Matchers.equalTo(err)
        );
    }
}
