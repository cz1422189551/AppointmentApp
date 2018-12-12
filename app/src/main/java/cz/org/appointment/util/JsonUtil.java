package cz.org.appointment.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class JsonUtil {

    static Gson gson = new GsonBuilder().create();


    public static String toJson(Object target) {
        return gson.toJson(target);
    }


    public static Object fromJson(String json, Class<?> targetClazz, Class GenericClazz) {
        return fromJson(json, targetClazz, new Class[]{GenericClazz});
    }

    public static Object fromJson(String json,Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Object fromJson(String json, Class<?> targetClazz, Class[] genericClazz) {

        ParameterizedTypeImpl subType = null;
        ParameterizedTypeImpl resultType = null;
        if (genericClazz.length >= 2) {
            for (int i = 0; i < genericClazz.length; i = i + 2) {
                subType = new ParameterizedTypeImpl(genericClazz[i], new Type[]{genericClazz[i + 1]});
            }
            resultType = new ParameterizedTypeImpl(targetClazz, new Type[]{subType});
        } else {
            resultType = new ParameterizedTypeImpl(targetClazz, new Type[]{genericClazz[0]});
        }
        return gson.fromJson(json, resultType);
    }


    static class ParameterizedTypeImpl implements ParameterizedType {

        private final Class raw;

        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }


}
