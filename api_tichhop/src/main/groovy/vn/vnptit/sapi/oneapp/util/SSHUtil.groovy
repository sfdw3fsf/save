package vn.vnptit.sapi.oneapp.util

import com.jcraft.jsch.ChannelShell
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.LoggingManaged

import java.util.regex.Pattern

@Component
class SSHUtil {

    @Autowired
    LoggingManaged loggingManaged

    static Session connect(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch()
            Session session = jsch.getSession(username, host, port)
            session.setPassword(password)
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()
            return session
        } catch (Exception ignored) {
            return null
        }
    }

    static void disconnect(Session session) {
        try {
            session.disconnect()
        } catch (Exception ignored) {
        }
    }

    String runIpwCli(String requestId, Session session, String cliUsername, String sshPassword, List<String> commands) {
        ChannelShell channel = null
        try {
            channel = (ChannelShell) session.openChannel("shell")
            channel.setPty(true)
            OutputStream out = channel.outputStream
            InputStream inp = channel.inputStream
            channel.connect()

            String prompt = "IPWorks>"
            StringBuilder buffer = new StringBuilder()

            def log = { msg, loggingManaged ->
                println "[DEBUG] ${new Date()} - ${msg}"
                loggingManaged.info(["requestId": requestId, "msg": msg])
            }

            def readUntil = { List<String> keywords, int maxWaitMs = 15000 ->
                long end = System.currentTimeMillis() + maxWaitMs
                while (System.currentTimeMillis() < end) {
                    if (inp.available() > 0) {
                        byte[] b = new byte[inp.available()]
                        inp.read(b)
                        buffer.append(new String(b))
                        for (k in keywords) {
                            if (buffer.toString().contains(k)) {
                                String result = buffer.toString()
                                buffer.setLength(0)
                                return result
                            }
                        }
                    } else {
                        Thread.sleep(100)
                    }
                }
                throw new RuntimeException("Timeout waiting for: ${keywords}\nBuffer: ${buffer}")
            }

            String cliCmd = "sudo /opt/ipworks/cli/scripts/ipwcli"
            out.write("${cliCmd}\n".bytes)
            out.flush()

            String firstResp = readUntil([
                    "Login:",
                    "incorrect password",
                    "command not found",
                    "Sorry, try again"
            ])

            if (firstResp.contains("incorrect password") || firstResp.contains("Sorry, try again")) {
                throw new RuntimeException("Sudo password incorrect")
            }

            if (firstResp.contains("command not found")) {
                throw new RuntimeException("Cannot start ipwcli. Output: ${firstResp}")
            }

            if (!firstResp.contains("Login:")) {
                throw new RuntimeException("Unexpected response: ${firstResp}")
            }

            out.write("${cliUsername}\n".bytes)
            out.flush()
            readUntil(["Password:"])

            out.write("${sshPassword}\n".bytes)
            out.flush()
            readUntil(["Login to server successful."])

            StringBuilder allResults = new StringBuilder()
            commands.each { cmd ->
                log("Running command: ${cmd}", loggingManaged)
                out.write("${cmd}\n".bytes)
                out.flush()
                String rawOutput = readUntil([prompt])

                def cleaned = rawOutput
                        .replaceAll("(?s)^.*?${Pattern.quote(cmd)}\\s*", "")  // bỏ phần trước lệnh
                        .replaceAll("(?m)\\s*${Pattern.quote(prompt)}\\s*\$", "") // bỏ prompt ở cuối
                        .trim()

                log("Running command: ${cmd} - resp: ${cleaned}", loggingManaged)
                allResults.append(cleaned).append("\n")
            }

            return allResults.toString().trim()

        } finally {
            if (channel && channel.isConnected()) channel.disconnect()
        }
    }

    String configureSBG(Session session, String ip) {
        ChannelShell channel = null
        Map<String, Object> logConfigure = ["ip": ip]
        try {
            channel = (ChannelShell) session.openChannel("shell")
            channel.setPty(true)

            OutputStream out = channel.outputStream
            InputStream inp = channel.inputStream

            channel.connect()
            Thread.sleep(300)

            // Clear buffer đầu
            while (inp.available() > 0) {
                inp.read()
            }

            // Tạo delimiter unique
            String delimiter = "CMD_DELIMITER_" + System.currentTimeMillis()

            // Thực hiện các lệnh
            String result = executeCommandAndWait(out, inp, "/opt/com/bin/cliss")
            logConfigure["cliss"] = result

            result = executeCommandAndWait(out, inp, "show-table -r ManagedElement=SBGE,SbgFunction=1,IBCF=1 -m AddressToNetwork -p id,subnetAddress -s", delimiter)
            logConfigure["showTable"] = result

            // Tách dòng
            def lines = result.readLines()

            // Lọc những dòng bắt đầu bằng dấu "|" có dữ liệu (bỏ header và ====)
            def dataLines = lines.findAll { it ==~ /\|\s*\d+\s*\|.*\|/ }

            // Parse ra map [id: , subnetAddress: ]
            def listData = dataLines.collect { line ->
                def parts = line.split(/\|/)*.trim().findAll { it }
                [id: parts[0] as Integer, subnetAddress: parts[1]]
            }

            if (listData.size() == 0) {
                logConfigure["resp"] = "Error_ShowTable"
                return "Error_ShowTable"
            }

            def item = listData.find { it -> it["subnetAddress"] == ip }
            if (item) { // Neu da ton tai thi return thanh cong luon
                logConfigure["resp"] = "Success_Exists_${item.id}"
                return "Success_Exists_${item.id}"
            }

            // Chưa tồn tại thì khai mới
            Integer idNew = listData[listData.size() - 1].id + 1
            logConfigure["id"] = idNew
            logConfigure["step1"] = executeCommandAndWait(out, inp, "configure")
            logConfigure["step2"] = executeCommandAndWait(out, inp, "ManagedElement=SBGE,SbgFunction=1,IBCF=1,AddressToNetwork=${idNew}")
            logConfigure["step3"] = executeCommandAndWait(out, inp, "foreignNetwork=2")
            logConfigure["step4"] = executeCommandAndWait(out, inp, "subnetAddress=\"${ip}\"")
            logConfigure["step5"] = executeCommandAndWait(out, inp, "subnetMaskLength=32")
            logConfigure["step6"] = executeCommandAndWait(out, inp, "commit")

            logConfigure["resp"] = "Success_New_${idNew}"
            return "Success_New_${idNew}"
        } catch (Exception e) {
            e.printStackTrace()
            logConfigure["resp"] = "Error_${e.message}"
            return "Error_${e.message}"
        } finally {
            loggingManaged.info(logConfigure)
            if (channel != null && channel.isConnected()) {
                channel.disconnect()
            }
        }
    }

    // Thực hiện lệnh chờ Prompt
    static String executeCommandAndWait(OutputStream out, InputStream inp, String command, String prompt = ">", long timeoutMs = 3000) {
        out.write("${command}\n".bytes)
        out.flush()

        StringBuilder buffer = new StringBuilder()
        byte[] tmp = new byte[1024]
        long endTime = System.currentTimeMillis() + timeoutMs

        while (System.currentTimeMillis() < endTime) {
            while (inp.available() > 0) {
                int len = inp.read(tmp)
                if (len > 0) {
                    buffer.append(new String(tmp, 0, len))
                    if (buffer.toString().contains("\n${prompt}")) {  // thấy prompt là dừng ngay
                        return cleanOutput(buffer.toString(), command, prompt)
                    }
                }
            }
            Thread.sleep(30)
        }
        return cleanOutput(buffer.toString(), command, prompt)
    }

    // Làm sạch kết quả: bỏ lệnh & prompt
    static String cleanOutput(String raw, String command, String prompt) {
        raw.readLines()
                .collect { it.trim() }
                .findAll { line -> !(line.isEmpty() || line == command || line == prompt) }
                .join("\n")
    }

}