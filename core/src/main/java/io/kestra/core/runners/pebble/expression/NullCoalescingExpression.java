package io.kestra.core.runners.pebble.expression;

import io.pebbletemplates.pebble.error.AttributeNotFoundException;
import io.pebbletemplates.pebble.node.expression.BinaryExpression;
import io.pebbletemplates.pebble.node.expression.Expression;
import io.pebbletemplates.pebble.template.EvaluationContextImpl;
import io.pebbletemplates.pebble.template.PebbleTemplateImpl;

public class NullCoalescingExpression extends BinaryExpression<Object> {
    public NullCoalescingExpression() {
    }

    public NullCoalescingExpression(Expression<?> left, Expression<?> right) {
        super(left, right);
    }

    @Override
    public Object evaluate(PebbleTemplateImpl self, EvaluationContextImpl context) {
        try {
            var result = getLeftExpression().evaluate(self, context);
            if (result != null) {
                return result;
            } else {
                return getRightExpression().evaluate(self, context);
            }
        } catch (AttributeNotFoundException e) {
            return getRightExpression().evaluate(self, context);
        }
    }

    @Override
    public String toString() {
        return String.format("%s ?? %s", getLeftExpression(), getRightExpression());
    }

}
