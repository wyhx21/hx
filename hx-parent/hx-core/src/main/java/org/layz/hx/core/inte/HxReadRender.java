package org.layz.hx.core.inte;

import org.layz.hx.base.exception.HxRuntimeException;

public interface HxReadRender extends ReadRender{

    @Override
    default void exceptionHandler(RuntimeException e, int rowIndex) throws RuntimeException {
        if(HxRuntimeException.class.isInstance(e)) {
            HxRuntimeException exception = (HxRuntimeException) e;
            exception.setMessgae(String.format("第[%s]行，%s",rowIndex, e.getMessage()));
        }
        throw e;
    }
}
