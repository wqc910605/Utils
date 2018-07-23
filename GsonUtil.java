
/**
 * 封装的是使用Gson解析json的方法
 * @author Administrator
 *
 */
public class GsonUtil {

    private static       Gson       mGson       = null;
    private static       JsonParser mGsonParser = null;
    private static final String     TAG         = GsonUtil.class.getSimpleName();

    public static Gson getGson() {
        if (mGson == null) {
            synchronized (GsonUtil.class) {
                if (mGson == null) {
                    mGson = new GsonBuilder().setExclusionStrategies(new FooAnnotationExclusionStrategy())
                            //							.setDateFormat("yyyy-MM-dd")
                            //							.setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .serializeNulls()//序列化null
                            .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
                            .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                            .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                            .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                            .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                            .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                            .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                            .registerTypeAdapter(long.class, new LongDefault0Adapter())
                            .create();
                }
            }
        }
        return mGson;
    }

    public static JsonParser getGsonParser() {
        if (mGsonParser == null) {
            synchronized (GsonUtil.class) {
                if (mGsonParser == null) {
                    mGsonParser = new JsonParser();
                }
            }
        }
        return mGsonParser;
    }


    public static String formatObjectToJson(Object object) {
        return getGson().toJson(object);
    }

    public static <T> T parseJsonToBean(String json, Class<T> tClass) {
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return getGson().fromJson(json, tClass);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static <T> List<T> parseJsonToList(String json, Class<T> tClass) {
        List<T> tList = new ArrayList<>();
        JsonElement jsonElementSrc = getGsonParser().parse(json);
        if (jsonElementSrc.isJsonArray()) {
            JsonArray jsonArray = jsonElementSrc.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                tList.add(getGson().fromJson(jsonElement, tClass));
            }
        } else if (jsonElementSrc.isJsonObject()) {
            tList.add(getGson().fromJson(jsonElementSrc, tClass));
        } else if (jsonElementSrc.isJsonNull()) {
            Log.e(TAG, "json is null!");
        } else if (jsonElementSrc.isJsonPrimitive()) {
            JsonPrimitive asJsonPrimitive = jsonElementSrc.getAsJsonPrimitive();
            Log.e(TAG, "json is jsonPrimitive,json=" + asJsonPrimitive.toString());
        }
        return tList;
    }

    public static <T> List<T> parseJsonToListByType(String json, Class<T> clazz) {
        List<T> list = null;
        if (json != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = getGson().fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    public static <T> List<Map<String, T>> parseJsonToMapList(String json) {
        return getGson().fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    public static <T> Map<String, T> parseJsonToMap(String json) {
        return getGson().fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }


    public static <T> T parseJSON(String json, Class<T> clazz) {
        T info = getGson().fromJson(json, clazz);
        return info;
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = getGson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(getGson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


    /** @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> BaseBean<T> parseJson2Bean(String json, Class<T> clazz) {
        BaseBean<T> tBaseBean = new BaseBean<>();
        List<T> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            long timestamp = jsonObject.getLong("timestamp");

            tBaseBean.setCode(code);
            tBaseBean.setMsg(msg);
            tBaseBean.setTimestamp(timestamp);

            if (!TextUtils.isEmpty(data)) {
                Object jsonTokener = new JSONTokener(data).nextValue();
                if (jsonTokener instanceof JSONObject) {//对象型
                    T t = parseJSON(data, clazz);
                    tBaseBean.setData(t);
                } else if (jsonTokener instanceof JSONArray) {
                    list = jsonToArrayList(data, clazz);
                    tBaseBean.setDataList(list);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tBaseBean;
    }

    public static class DateDeserializer implements JsonDeserializer<Date> {

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            long time;
            if ((json.toString().contains("-") && json.getAsJsonPrimitive().isString()) || TextUtils.isEmpty(json.getAsJsonPrimitive().getAsString().trim())) {
                try {
                    String jsonTrim = json.toString().trim();
                    if (jsonTrim.contains(" ")) {
                        date = sdf2.parse(json.getAsJsonPrimitive().getAsString());
                    } else {
                        date = sdf.parse(json.getAsJsonPrimitive().getAsString());
                    }
                } catch (ParseException e) {
                    Log.e(TAG, e.toString());
                    date = new Date();
                }
                time = date.getTime();
            } else {
                time = json.getAsJsonPrimitive().getAsLong();
            }
            return new Date(time);
        }
    }

    public static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

    public static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                    return 0D;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return 0l;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }


    //过滤不解析的属性
    public @interface FooAnnotation {
        // some implementation here
    }

    // Excludes any field (or class) that is tagged with an "@FooAnnotation"
    private static class FooAnnotationExclusionStrategy implements ExclusionStrategy {
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;//clazz.getAnnotation(FooAnnotation.class) != null;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            String fieldName = f.getName();
            Class<?> theClass = f.getDeclaringClass();
            return isFieldInSuperclass(theClass, fieldName);//f.getAnnotation(FooAnnotation.class) != null;
        }

        private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
            Class<?> superclass = subclass.getSuperclass();
            Field field;
            while(superclass != null) {
                field = getField(superclass, fieldName);

                if(field != null)
                    return true;

                superclass = superclass.getSuperclass();
            }
            return false;
        }

        private Field getField(Class<?> theClass, String fieldName) {
            try {
                return theClass.getDeclaredField(fieldName);
            } catch(Exception e) {
                return null;
            }
        }
    }
}
