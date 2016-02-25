package edu.itu.localjack.common;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.itu.util.DateUtils2;




//import com.google.gson.JsonObject;

//import javax.json.JsonArray;
//import javax.json.JsonReader;

public class JsonUtil {

	/**
	 * javax.json.JsonObject is so weak and i can not bear it!!! <br>
	 * {'a':3} can use getInt() but {'a':'3'} can not. wtf!!!<br>
	 * but Jersey to only support to javax.json.JsonObject.<br>
	 * so I must to add these methods. It's toooooood annoy.
	 * 
	 * @param jjo
	 * @param key
	 * @return
	 */
	public static int getInt(javax.json.JsonObject jjo, String key) {
		return convertJxObjObjToGsonObj(jjo).get(key).getAsInt();
	}
	public static Gson getItuGson() {
		Gson gson = new GsonBuilder().setDateFormat(DateUtils2.FOMAT_DATE).create();
		return gson;
	}
	
	public static float getFloat(javax.json.JsonObject jjo, String key) {
		return convertJxObjObjToGsonObj(jjo).get(key).getAsFloat();
	}
	
	public static boolean getBoolean(javax.json.JsonObject jjo, String key) {
		return convertJxObjObjToGsonObj(jjo).get(key).getAsBoolean();
	}
	
	public static String getString(javax.json.JsonObject jjo, String key) {
		return convertJxObjObjToGsonObj(jjo).get(key).getAsString();
	}
	
	public static javax.json.JsonObject getJsonObject(javax.json.JsonArray jja, int index){
		com.google.gson.JsonArray garr = convertStringToGsonArr(jja.toString());
		return convertGsonObjToJxObj(garr.get(index).getAsJsonObject());
	}
	

	public static javax.json.JsonArray convertGsonArrToJxArr(com.google.gson.JsonArray ja) {
		javax.json.JsonReader jReader = javax.json.Json.createReader(new StringReader(ja.toString()));
		javax.json.JsonArray jxa = jReader.readArray();
		jReader.close();
		return jxa;
	}

	public static javax.json.JsonArray convertStringToJxArr(String sjson) {
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		com.google.gson.JsonElement tradeElement = parser.parse(sjson);
		com.google.gson.JsonArray ja = tradeElement.getAsJsonArray();
		
		return convertGsonArrToJxArr(ja);
	}

	public static javax.json.JsonObject convertGsonObjToJxObj(com.google.gson.JsonObject jo) {
		javax.json.JsonReader jReader = javax.json.Json.createReader(new StringReader(jo.toString()));
		javax.json.JsonObject jxo = jReader.readObject();

		jReader.close();
		return jxo;
	}

	/**
	 * convert javax.json.JsonObject to gson.JsonObject.
	 * 
	 * @param jo
	 * @return
	 */
	public static com.google.gson.JsonObject convertJxObjObjToGsonObj(javax.json.JsonObject jo) {
		return convertStringToGsonObj(jo.toString());
	}

	public static javax.json.JsonObject convertStringToJxObj(String sjson) {
		com.google.gson.JsonObject asJsonObject = convertStringToGsonObj(sjson);
//		asJsonObject.get("").get
		return convertGsonObjToJxObj(asJsonObject);
	}

	public static com.google.gson.JsonObject convertStringToGsonObj(String sjson) {
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		com.google.gson.JsonElement tradeElement = parser.parse(sjson);
		com.google.gson.JsonObject asJsonObject = tradeElement.getAsJsonObject();
		return asJsonObject;
	}
	
	public static com.google.gson.JsonArray convertStringToGsonArr(String sjson) {
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		com.google.gson.JsonElement tradeElement = parser.parse(sjson);
		com.google.gson.JsonArray asJsonArr = tradeElement.getAsJsonArray();
		return asJsonArr;
	}
}
