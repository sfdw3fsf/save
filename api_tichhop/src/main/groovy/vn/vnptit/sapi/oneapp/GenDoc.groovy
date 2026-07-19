package vn.vnptit.sapi.oneapp

import groovy.text.SimpleTemplateEngine
import io.micrometer.core.instrument.util.StringEscapeUtils
import org.apache.commons.configuration.PropertiesConfiguration
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.runtime.GStringImpl
import org.json.JSONArray
import org.json.JSONObject

class GenDoc {
    public static void main(String[] args) {
        def eng = new SimpleTemplateEngine()
        PropertiesConfiguration props = new PropertiesConfiguration().tap {
            setDelimiterParsingDisabled(true);
            setEncoding("UTF-8");
            load(GenDoc.class.getClassLoader().getResourceAsStream("executor.properties"));
        }

        Map<String, JSONObject> cachedBean = [:]

        Set<String> tags = []
        JSONObject definition = new JSONObject();
        JSONObject routes = new JSONObject();
        definition.put("BaseResponse", buildDefinition("BaseResponse", "error,error_code,message,message_detail,request_id,data"))

        JSONObject swagger = new JSONObject("""{
  "swagger": "2.0",
  "info": {
    "description": "ONE BSS EXEC",
    "version": "1.0.0",
    "title": "one bss exec",
    "contact": {},
    "license": {
      "name": "vnptit-erp",
      "url": "http://unlicense.org"
    }
  },
  "host": "localhost:8088",
  "basePath": "/",
  "tags":[],
  "paths":{},
  "definitions":{}
}""")

        def reg = /('?\$\{?([a-z_A-Z0-9]*)(\.[a-z_A-Z0-9\(\)]*)?\}?[', ]?)/

        props.getKeys().findAll { it.split("\\.").size() == 1 }.each { String apiName ->

            String sql = props.getString(apiName)

            def m = sql =~ reg
            Set<String> sParam = []
            m.eachWithIndex { im, i ->
                sParam.add(im[2])
            }

            JSONObject input = buildDefinition(apiName + "_input", sParam.join(","))
            definition.put(apiName + "_input", input)
            JSONObject route = buildRoute("executor", apiName, sql, "post", apiName + "_input", "BaseResponse")
            routes.put("/executor/" + apiName, route)
        }

        tags.each { swagger.getJSONArray("tags").put(new JSONObject().put("name", it).put("description", it)) }
        swagger.put("paths", routes)
        swagger.put("definitions", definition)

        println swagger.toString()
    }

    static JSONObject buildRoute(String dir, String name, String desc, String method, String inputBean, String outputBean) {
        JSONObject jo = new JSONObject()

        jo.put(method, new JSONObject("""{
    "tags": [
      "${dir.replace("/", "-")}"
    ],
    "description": "${StringEscapeUtils.escapeJson(desc)}",
    "operationId": "${(dir + "/" + name + "/" + method).replace("/", "_")}",
    "consumes": [
      "application/json"
    ],
    "produces": [
      "application/json"
    ],
    "parameters": [{
        "in": "body",
        "name": "$inputBean",
        "description": "",
        "required": true,
        "schema": {
          "\$ref": "#/definitions/$inputBean"
        }
    }],
    "responses": {
      "200": {
        "description": "OK",
        "schema": {
          "\$ref": "#/definitions/$outputBean"
        }
      }
    }
}"""))

        jo
    }

    static JSONObject buildRouteGet(String dir, String name, String desc, String method, JSONArray params, String outputBean) {
        JSONObject jo = new JSONObject()

        jo.put(method, new JSONObject("""{
    "tags": [
      "${dir.replace("/", "-")}"
    ],
    "description": "${StringEscapeUtils.escapeJson(desc)}",
    "operationId": "${(dir + "/" + name + "/" + method).replace("/", "_")}",
    "consumes": [
      "application/json"
    ],
    "produces": [
      "application/json"
    ],
    "parameters": ${params.toString()},
    "responses": {
      "200": {
        "description": "OK",
        "schema": {
          "\$ref": "#/definitions/$outputBean"
        }
      }
    }
}"""))

        jo
    }

    static JSONArray buildRouteParam(String inputcsv) {
        JSONArray jo = new JSONArray()
        inputcsv.split(",").collect { it.trim() }.each {
            if (it)
                jo.put(new JSONObject("""{"in":"query", "type":"string", "required": false}""").put("name", it.trim()))
        }
        jo
    }

    static JSONObject buildDefinition(String name, String inputs) {
        JSONObject jo = new JSONObject()
        JSONObject joProps = new JSONObject()

        inputs.split(",").each {
            if (!StringUtils.isNumeric(it)) {
                joProps.put(it.trim(), new JSONObject().put("type", "string"))
            }
        }
        if (name == 'BaseResponse')
            joProps.put("data", new JSONObject().put("type", "array").put("items", new JSONObject().put("type", "object")))

        jo.put("title", name)
        jo.put("type", "object")
        jo.put("properties", joProps)

        jo
    }
}
