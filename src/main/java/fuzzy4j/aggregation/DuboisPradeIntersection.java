/*
 * Copyright (c) 2013, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Implements the Dubois-Prade aggregation operator.
 *
 * Defined as: <code>t_dp(a, b) = (a * b) / max(a, b, p)</code>
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class DuboisPradeIntersection implements Norm {

    /**
     * Construct dubois prade using the parametricfactory interface.
     */
    public static final ParametricFactory<DuboisPradeIntersection> FACTORY = new ParametricFactory<DuboisPradeIntersection>() {
        @Override
        public DuboisPradeIntersection create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("one parameter expected (p in [0, 1])");
            return new DuboisPradeIntersection(params[0]);
        }
    };

    public final double p;

    public DuboisPradeIntersection(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("requires p in [0, 1]");
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        ParametersUtil.assertTwoParameters(DuboisPradeIntersection.class, "apply", values);
        return (values[0] * values[1]) / Math.max(Math.max(values[0], values[1]), p);
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return new DeMorganDuality(this);
    }

    public String toString() {
        return type().toString() + "_dp";
    }
}
