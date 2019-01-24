package cz.org.appointment.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class JsonUtil {

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
            .create();


    public static String toJson(Object target) {
        return gson.toJson(target);
    }


    public static Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
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
