package com.github.slick.middleware;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestSimple<R, P> extends Request {
    private P data;
    private Callback<R> callback;
    abstract public R target(P data);

    public RequestSimple<R, P> with(P data) {
        this.data = data;
        if (!(data instanceof RequestData)) {
            requestData = new RequestData().putParameter(data);
        }
        return this;
    }

    public RequestSimple<R, P> through(Middleware... middleware) {
        this.middleware = middleware;
        return this;
    }

    @Override
    public void next() {
        if (middlewareStack.size() > 0) {
            middlewareStack.pop().handle(this, requestData == null ? (RequestData) data : requestData);
        }
        middlewareBackStack++;
        if (!(middlewareBackStack - 1 == this.middleware.length && !tooLateAlreadyFinished)) {
            return;
        }

        if (this != routerStack.pop()) throw new AssertionError();
        final R response = target(data);
        if (callback != null) {
            callback.onPass(response);
        }
        tooLateAlreadyFinished = true;
    }

    public void destination(Callback<R> callback) {
        this.callback = callback;
    }
}
