package com.example.authserver.config.federated_identity;

@FunctionalInterface
public interface OAuthConsumer<T> {

    void accept(T t, String authProvider);

}
