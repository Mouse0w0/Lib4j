package com.github.mouse0w0.lib4j.registry;

import com.google.common.reflect.TypeToken;

public interface RegistryEntry<T> {

    NamespacedKey getRegistryName();

    Class<T> getRegistryType();

    T setRegistryName(NamespacedKey location);

    default T setRegistryName(String name) {
        return setRegistryName(new NamespacedKey(name));
    }

    abstract class Impl<T extends RegistryEntry<T>> implements RegistryEntry<T> {

        private final TypeToken<T> token = new TypeToken<T>(getClass()) {
        };
        private NamespacedKey location;

        @Override
        public final NamespacedKey getRegistryName() {
            return location;
        }

        @SuppressWarnings("unchecked")
        @Override
        public final T setRegistryName(NamespacedKey location) {
            if (this.location != null) throw new Error("Duplicated register");
            this.location = location;
            return (T) this;
        }

        public final T setRegistryName(String domain, String path) {
            return setRegistryName(new NamespacedKey(domain, path));
        }

        public final T setRegistryName(String path) {
            throw new UnsupportedOperationException();
            //TODO:
            //return setRegistryName(new ResourceLocation(domain, path));
        }

        @SuppressWarnings("unchecked")
        @Override
        public final Class<T> getRegistryType() {
            return (Class<T>) token.getRawType();
        }

        ;
    }
}
