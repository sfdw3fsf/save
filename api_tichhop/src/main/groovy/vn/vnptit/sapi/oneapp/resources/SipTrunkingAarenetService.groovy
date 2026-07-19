package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.slurpersupport.GPathResult
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@RestController
@RequestMapping("siptrunking/aarenet")
class SipTrunkingAarenetService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    ObjectMapper objectMapper

    @Value("\${spring.httpservices.siptrunking.aarenet.url_hcm}")
    String url_hcm

    @Value("\${spring.httpservices.siptrunking.aarenet.url_hni}")
    String url_hni

    @Value("\${spring.httpservices.siptrunking.aarenet.username_hcm}")
    String username_hcm

    @Value("\${spring.httpservices.siptrunking.aarenet.password_hcm}")
    String password_hcm

    @Value("\${spring.httpservices.siptrunking.aarenet.username_hni}")
    String username_hni

    @Value("\${spring.httpservices.siptrunking.aarenet.password_hni}")
    String password_hni

    @PostMapping("/createAccount")
    CommonResponse createAccount(@RequestBody Map<String, Object> body) {
        try {
            if (body.type == "2" && body.routingTable == "Route to TSSE1B") {
                body.routingTable = "Route to TSSE"
            }
            String xmlBody = """<daml command="write">
                                    <account>
                                        <tenant>SIPTrunk</tenant>
                                        <accountName>${body.accountName}</accountName>
                                        <info>${body.info}</info>
                                        <username>${body.username}</username>
                                        <password>${body.password}</password>
                                        <routingTable>${body.routingTable}</routingTable>"""
            for (String rs : body.rulesets) {
                if (body.type == "2" && rs == "Rule add cpc=cnap to PAI") {
                    rs = "Route : Add cnap to PAI for Voice Brandname number"
                }
                xmlBody += "<ruleset>$rs</ruleset>"
            }
            xmlBody += """<emergencyLocation>Default Location</emergencyLocation>
                                        <maxChannels>${body.maxChannels}</maxChannels>
                                        <pricelist>pricelist_siptrunk_v8</pricelist>                                      
                                        <sendAoc>false</sendAoc>
                                        <specialArrangement>false</specialArrangement>
                                        <alarmOnExpiry>false</alarmOnExpiry>
                                        <maliciousCallerId>false</maliciousCallerId>
                                        <useMediaServer>false</useMediaServer>
                                        <sendingHoldStream>false</sendingHoldStream>
                                        <authByIpAndPort>false</authByIpAndPort>
                                        <authByIp>false</authByIp>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "createAccount")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/createAddress")
    CommonResponse createAddress(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>
                                        <number>${body.number}</number>
                                        <displayName>${body.displayName}</displayName>
                                        <domain>${body.type == "1" ? "10.163.201.4" : "10.164.201.4"}</domain>
                                        <account>${body.accountName}</account>
                                        <language>en</language>
                                        <showClip>true</showClip>
                                        <hideClip>false</hideClip>
                                        <mainNumber>false</mainNumber>
                                        <baseNumber>false</baseNumber>
                                        <registersViaMainNumber>false</registersViaMainNumber>
                                        <signalingOnly>false</signalingOnly>
                                        <blocked>false</blocked>
                                        <disabled>false</disabled>
                                        <uccUser>false</uccUser>
                                        <singleLocation>false</singleLocation>
                                        <preferredNumber>false</preferredNumber>
                                        <noOfferOnBusy>false</noOfferOnBusy>
                                        <callWaiting>false</callWaiting>
                                        <callHold>false</callHold>
                                        <priorityCall>false</priorityCall>
                                        <privateNumber>false</privateNumber>
                                        <balancedRouting>true</balancedRouting>
                                        <autoRecord>false</autoRecord>
                                        <busyIfAllDevicesAreBusy>false</busyIfAllDevicesAreBusy>
                                        <agentLoggedIn>false</agentLoggedIn>
                                        <noMiIfLocation>false</noMiIfLocation>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "createAddress")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // mạng 10.x.x.x (sipContact) thì SIP Endpoint : Private UDP 5060, userAgent = FPBX
    // còn tất cả là SIP Endpoint : Public UDP 5060, userAgent = Asterisk
    @PostMapping("/createSiptrunk")
    CommonResponse createSiptrunk(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="create">
                                    <sipTrunk>
                                        <name>${body.name}</name>
                                        <info>${body.info}</info>
                                        <group>SIPTrunk</group>
                                        <auth>IpAndPort</auth>
                                        <sipContact>${body.sipContact}</sipContact>
                                        <route1/>
                                        <route2/>                                      
                                        <endpoint>${body.endpoint}</endpoint>
                                        <q>${body.q}</q>
                                        <matchNumber>Preferred</matchNumber>
                                        <onNoMatch>Reject</onNoMatch>
                                        <natConfig>Automatic</natConfig>
                                    </sipTrunk>
                                </daml>"""
            return execute(body, xmlBody, "createSiptrunk")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/mapSiptrunkToAccount")
    CommonResponse mapSiptrunkToAccount(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="create">
                                    <accountSipTrunkMap account="${body.accountName}" sipTrunk="${body.sipTrunk}"/>
                                </daml>"""
            return execute(body, xmlBody, "mapSiptrunkToAccount")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/suspendAddress")
    CommonResponse suspendAddress(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>
                                        <number>${body.number}</number>
                                        <disabled>true</disabled>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "suspendAddress")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resumeAddress")
    CommonResponse resumeAddress(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>
                                        <number>${body.number}</number>
                                        <disabled>false</disabled>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "resumeAddress")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/deleteAddress")
    CommonResponse deleteAddress(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="delete">
                                    <address>
                                        <number>${body.number}</number>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "deleteAddress")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeMaxChannels")
    CommonResponse changeMaxChannels(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                        <maxChannels>${body.maxChannels}</maxChannels>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "changeMaxChannels")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/blockInternational")
    CommonResponse blockInternational(@RequestBody Map<String, Object> body) {
        try {
            CommonResponse readRes = readAccount(body)
            def rulesets = readRes?.data?.daml?.account?.ruleset ?: []
            if (!(rulesets instanceof List)) {
                rulesets = [rulesets]
            }
            Set<String> ruleSetSet = new LinkedHashSet<>(rulesets)
            ruleSetSet.add("NCOS : Block International call")
            ruleSetSet.add("Block : All outgoing International Calls")
            StringBuilder rulesetXml = new StringBuilder()

            ruleSetSet.each { rs ->
                rulesetXml.append("<ruleset>${rs}</ruleset>\n")
            }
            String xmlBody = """
            <daml command="write">
                <account>
                    <accountName>${body.accountName}</accountName>
                    <routingTable>SIPTRUNK_DNK_1900</routingTable>
                    ${rulesetXml.toString()}
                </account>
            </daml>
        """
            return execute(body, xmlBody, "blockInternational")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/blockInternational2")
    CommonResponse blockInternational2(@RequestBody Map<String, Object> body) {
        try {
            CommonResponse readRes = readAccount(body)
            def rulesets = readRes?.data?.daml?.account?.ruleset ?: []
            if (!(rulesets instanceof List)) {
                rulesets = [rulesets]
            }
            Set<String> ruleSetSet = new LinkedHashSet<>(rulesets)
            ruleSetSet.add("NCOS : Block International call")
            ruleSetSet.add("Block : All outgoing International Calls")
            StringBuilder rulesetXml = new StringBuilder()

            ruleSetSet.each { rs ->
                rulesetXml.append("<ruleset>${rs}</ruleset>\n")
            }
            String xmlBody = """
            <daml command="write">
                <account>
                    <accountName>${body.accountName}</accountName>
                    <routingTable>SIPTRUNK_DNK_1900</routingTable>
                    <ruleset>hiện hữu</ruleset>
                    ${rulesetXml.toString()}
                </account>
            </daml>
        """
            CommonResponse res = execute(body, xmlBody, "openInternational")
            res.data = [
                    requestXml: xmlBody,
                    response  : res.data
            ]
            return res
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/openInternational")
    CommonResponse openInternational(@RequestBody Map<String, Object> body) {
        try {
            CommonResponse readRes = readAccount(body)
            def rulesets = readRes?.data?.daml?.account?.ruleset ?: []
            if (!(rulesets instanceof List)) {
                rulesets = [rulesets]
            }
            Set<String> ruleSetSet = new LinkedHashSet<>(rulesets)
            ruleSetSet.remove("NCOS : Block International call")
            ruleSetSet.remove("Block : All outgoing International Calls")
            StringBuilder rulesetXml = new StringBuilder()
            ruleSetSet.each { rs ->
                rulesetXml.append("<ruleset>${rs}</ruleset>\n")
            }
            String xmlBody = """
            <daml command="write">
                <account>
                    <accountName>${body.accountName}</accountName>
                    <routingTable>SIPTRUNK_QTE</routingTable>
                    ${rulesetXml.toString()}
                </account>
            </daml>
        """
            return execute(body, xmlBody, "openInternational")

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/openInternational2")
    CommonResponse openInternational2(@RequestBody Map<String, Object> body) {
        try {
            CommonResponse readRes = readAccount(body)
            def rulesets = readRes?.data?.daml?.account?.ruleset ?: []
            if (!(rulesets instanceof List)) {
                rulesets = [rulesets]
            }
            Set<String> ruleSetSet = new LinkedHashSet<>(rulesets)
            ruleSetSet.remove("NCOS : Block International call")
            ruleSetSet.remove("Block : All outgoing International Calls")
            StringBuilder rulesetXml = new StringBuilder()
            ruleSetSet.each { rs ->
                rulesetXml.append("<ruleset>${rs}</ruleset>\n")
            }
            String xmlBody = """
            <daml command="write">
                <account>
                    <accountName>${body.accountName}</accountName>
                    <routingTable>SIPTRUNK_QTE</routingTable>
                    ${rulesetXml.toString()}
                </account>
            </daml>
        """
            CommonResponse res = execute(body, xmlBody, "openInternational")
            res.data = [
                    requestXml: xmlBody,
                    response  : res.data
            ]
            return res

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/block18001900")
    CommonResponse block18001900(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                        <routingTable>SIPTRUNK_DNK</routingTable>
                                        <ruleset>Block : All outgoing International Calls</ruleset>
                                        <ruleset>NCOS : Block International call</ruleset>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "block18001900")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/open18001900")
    CommonResponse open18001900(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                        <routingTable>SIPTRUNK_DNK_1900</routingTable>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "open18001900")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/registerVBN")
    CommonResponse registerVBN(@RequestBody Map<String, Object> body) {
        try {
            String vbrRuleset = body.type == "2" ? "Route : Add cnap to PAI for Voice Brandname number" : "Rule add cpc=cnap to PAI"
            String routingTable = body.type == "2" ? "Route to TSSE" : "Route to TSSE1B"

            String xmlBody = """<daml command="write">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                        <routingTable>${routingTable}</routingTable>
                                        <ruleset>Block : All outgoing International Calls</ruleset>
                                        <ruleset>NCOS : Block International call</ruleset>
                                        <ruleset>${vbrRuleset}</ruleset>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "registerVBN")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cancelVBN")
    CommonResponse cancelVBN(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                        <routingTable>SIPTRUNK_DNK_1900</routingTable>
                                        <ruleset>Block : All outgoing International Calls</ruleset>
                                        <ruleset>NCOS : Block International call</ruleset>
                                    </account>
                                </daml>"""
            return execute(body, xmlBody, "cancelVBN")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeTopStop")
    CommonResponse changeTopStop(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>        
                                        <number>${body.number}</number>
                                        <account>${body.number}</account>
                                        <accAddressTopStop>
                                            <valueMax>${body.valueMax}</valueMax>
                                            <monthlyReset>true</monthlyReset>
                                        </accAddressTopStop>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "changeTopStop")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/blockOutgoing")
    CommonResponse blockOutgoing(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>
                                        <number>${body.accountName}</number>
                                        <blocked>true</blocked>
                                        <disabled>false</disabled>
                                        <signalingOnly>false</signalingOnly>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "blockOutgoing")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/openOutgoing")
    CommonResponse openOutgoing(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                    <address>
                                        <number>${body.number}</number>
                                        <blocked>false</blocked>
                                    </address>
                                </daml>"""
            return execute(body, xmlBody, "openOutgoing")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/deleteAccount")
    CommonResponse deleteAccount(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="delete">
                                  <account>
                                    <accountName>${body.accountName}</accountName>
                                  </account> 
                                </daml>"""
            return execute(body, xmlBody, "deleteAccount")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/readTrunk")
    CommonResponse readTrunk(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="read">
                                    <sipTrunk name="${body.name}"/>
                                </daml>"""
            return execute(body, xmlBody, "readTrunk")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/readAccount")
    CommonResponse readAccount(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="read">
                                    <account>
                                        <accountName>${body.accountName}</accountName>
                                    </account> 
                                </daml>"""
            return execute(body, xmlBody, "readAccount")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/readAddress")
    CommonResponse readAddress(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="read">
                                  <address>
                                    <number>${body.number}</number>
                                  </address>
                                </daml>"""
            return execute(body, xmlBody, "readAddress")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/createCallForward")
    CommonResponse createCallForward(@RequestBody Map<String, Object> body) {
        try {
            String xmlBody = """<daml command="write">
                                <account>
                                    <accountName>${body.accountName}</accountName>
                                    <tenant>1800</tenant>
                                    <callForward>
                                        <name>${body.name}</name>
                                        <destPattern>${body.destPattern}</destPattern>
                                        <destReplace>${body.destReplace}</destReplace>
                                        <sourcePattern>${body.sourcePattern ?: ""}</sourcePattern>
                                        <priority>10</priority>
                                        <type>${body.type}</type>
                                        <account>${body.accountName}</account>
                                    </callForward>
                                </account>
                            </daml>"""
            return execute(body, xmlBody, "createCallForward")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse execute(def body, String xmlBody, String action) {
        Map<String, Object> logData = [:]
        logData["class"] = "SipTrunkingService"
        logData["action"] = action
        logData["body"] = objectMapper.writeValueAsString(body)

        HttpURLConnection connection = null
        OutputStream os = null
        InputStream inputStream = null
        try {
            URL url = new URL(body.type == "1" ? url_hni : url_hcm)
            connection = (HttpURLConnection) url.openConnection()

            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/xml")
            connection.setRequestProperty("Accept", "application/xml")
            connection.setDoOutput(true)
            connection.setConnectTimeout(30000)
            connection.setReadTimeout(30000)

            // Gửi request đầu tiên để lấy WWW-Authenticate header
            connection.connect()

            // Ghi XML body
            os = connection.getOutputStream()
            byte[] input = xmlBody.getBytes(StandardCharsets.UTF_8)
            os.write(input, 0, input.length)
            os.flush()
            os.close()
            os = null

            int responseCode = connection.getResponseCode()
            logData["responseCode1"] = responseCode

            if (responseCode == 401) {
                // Lấy WWW-Authenticate header
                String authHeader = connection.getHeaderField("WWW-Authenticate")
                if (authHeader != null && authHeader.startsWith("Digest")) {
                    // Parse digest challenge
                    Map<String, String> digestParams = parseDigestChallenge(authHeader)

                    // Tạo Authorization header
                    String authResponse = createDigestResponse(body.type, digestParams, "POST", "/dataaccesscenter/daml")
                    logData["authResponse"] = authResponse

                    // Đóng connection cũ
                    connection.disconnect()

                    // Tạo connection mới với Authorization header
                    connection = (HttpURLConnection) url.openConnection()
                    connection.setRequestMethod("POST")
                    connection.setRequestProperty("Content-Type", "application/xml")
                    connection.setRequestProperty("Accept", "application/xml")
                    connection.setRequestProperty("Authorization", authResponse)
                    connection.setDoOutput(true)
                    connection.setConnectTimeout(300000)
                    connection.setReadTimeout(300000)

                    // Gửi lại với authorization
                    os = connection.getOutputStream()
                    os.write(input, 0, input.length)
                    os.flush()
                    os.close()
                    os = null

                    responseCode = connection.getResponseCode()
                    logData["responseCode2"] = responseCode
                }
            }

            // Đọc response
            if (responseCode == 200) {
                inputStream = connection.getInputStream()
                String result = readResponse(inputStream)
                logData["data"] = result
                try {
                    def root = new XmlSlurper().parseText(result)
                    def jResult = [(root.name()): xmlNodeToMap(root)]
                    logData["jResult"] = objectMapper.writeValueAsString(jResult)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: jResult)
                } catch (Exception ignored) {
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
                }
            } else {
                inputStream = connection.getErrorStream()
                String error = readResponse(inputStream)
                logData["error"] = error
                try {
                    def doc = Jsoup.parse(error)
                    def messageParagraph = doc.select('p').find { it.select('b')?.text() == 'Message' }
                    def messageText = messageParagraph?.ownText()?.trim()
                    logData["jErrorHtml"] = objectMapper.writeValueAsString(messageText)
                    return new CommonResponse(errorCode: 1, faultCode: responseCode, faultString: messageText, data: null)
                } catch (Exception ignored) {
                    try {
                        def root = new XmlSlurper().parseText(error)
                        def jError = [(root.name()): xmlNodeToMap(root)]
                        logData["jError"] = objectMapper.writeValueAsString(jError)
                        return new CommonResponse(errorCode: 1, faultCode: responseCode, faultString: jError, data: null)
                    } catch (Exception ex) {
                        return new CommonResponse(errorCode: 1, faultCode: responseCode, faultString: error, data: null)
                    }
                }
            }
        } catch (Exception e) {
            logData["error"] = e.message
            return CommonResponse.resolveException(e, loggingManaged)
        } finally {
            loggingManaged.info(logData)
            if (os != null) {
                try {
                    os.close()
                } catch (IOException ignored) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (IOException ignored) {
                }
            }
            if (connection != null) {
                connection.disconnect()
            }
        }
    }

    static Map<String, String> parseDigestChallenge(String authHeader) {
        Map<String, String> params = [:]
        String challenge = authHeader.substring(7) // Remove "Digest "

        String[] pairs = challenge.split(",")
        pairs.each { pair ->
            String[] keyValue = pair.trim().split("=", 2)
            if (keyValue.length == 2) {
                String key = keyValue[0].trim()
                String value = keyValue[1].trim().replaceAll("\"", "")
                params[key] = value
            }
        }
        return params
    }

    String createDigestResponse(String type, Map<String, String> params, String method, String uri) throws NoSuchAlgorithmException {
        String realm = params.realm
        String nonce = params.nonce
        String qop = params.qop
        String opaque = params.opaque

        // Generate client nonce
        String cnonce = generateNonce()
        String nc = "00000001"

        // Calculate response
        String ha1 = type == "1" ? md5("${username_hni}:${realm}:${password_hni}") : md5("${username_hcm}:${realm}:${password_hcm}")
        String ha2 = md5("${method}:${uri}")

        String response
        if (qop != null && qop.contains("auth")) {
            response = md5("${ha1}:${nonce}:${nc}:${cnonce}:${qop}:${ha2}")
        } else {
            response = md5("${ha1}:${nonce}:${ha2}")
        }

        // Build authorization header
        StringBuilder auth = new StringBuilder("Digest ")
        auth.append("username=\"${type == "1" ? username_hni : username_hcm}\", ")
        auth.append("realm=\"${realm}\", ")
        auth.append("nonce=\"${nonce}\", ")
        auth.append("uri=\"${uri}\", ")

        if (qop != null) {
            auth.append("qop=${qop}, ")
            auth.append("nc=${nc}, ")
            auth.append("cnonce=\"${cnonce}\", ")
        }

        auth.append("response=\"${response}\"")

        if (opaque != null) {
            auth.append(", opaque=\"${opaque}\"")
        }

        return auth.toString()
    }

    static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5")
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8))
        StringBuilder sb = new StringBuilder()
        digest.each { b ->
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    static String generateNonce() {
        return String.valueOf(System.currentTimeMillis())
    }

    static String readResponse(InputStream inputStream) throws IOException {
        if (inputStream == null) return null

        StringBuilder response = new StringBuilder()
        BufferedReader reader = null

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
            String line
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n")
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (IOException ignored) {
                }
            }
        }

        return response.toString()
    }

    static def xmlNodeToMap(node) {
        def map = [:]
        if (node instanceof GPathResult && node.attributes()) {
            map.putAll(node.attributes())
        }

        node.children().each { child ->
            def key = child.name()
            def value = child.children().size() > 0 ? xmlNodeToMap(child) : child.text()
            if (map.containsKey(key)) {
                if (!(map[key] instanceof List)) {
                    map[key] = [map[key]]
                }
                map[key] << value
            } else {
                map[key] = value
            }
        }

        return map
    }
}