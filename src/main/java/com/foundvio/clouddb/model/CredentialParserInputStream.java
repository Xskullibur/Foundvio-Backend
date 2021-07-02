package com.foundvio.clouddb.model;

import com.huawei.agconnect.server.commons.credential.ClientIdCredential;
import com.huawei.agconnect.server.commons.credential.CredentialService;
import com.huawei.agconnect.server.commons.exception.AGCException;
import com.huawei.agconnect.server.commons.json.JSONObject;
import com.huawei.agconnect.server.commons.json.JSONUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CredentialParserInputStream {

    public static CredentialService toCredential(InputStream inputStream) throws AGCException {
        try {
            Throwable var2 = null;

            try {
                byte[] bytes = new byte[inputStream.available()];
                int num = inputStream.read(bytes);
                if (num <= 0) {
                    throw new AGCException("no content can be read from the stream:");
                } else {
                    String contentStr = new String(bytes, StandardCharsets.UTF_8);
                    JSONObject credential = new JSONObject(JSONUtils.escapeInjection(contentStr));
                    String fileType = credential.getString("type");
                    byte var9 = -1;
                    switch (fileType.hashCode()) {
                        case -140275027:
                            if (fileType.equals("team_client_id")) {
                                var9 = 1;
                            }
                            break;
                        case -100212631:
                            if (fileType.equals("project_client_id")) {
                                var9 = 0;
                            }
                    }

                    switch (var9) {
                        case 0:
                        case 1:
                            CredentialService var10 = buildClientIdCredential(bytes);
                            return var10;
                        default:
                            throw new AGCException("invalid credentail type.");
                    }
                }
            } catch (Throwable var20) {
                var2 = var20;
                throw var20;
            } finally {
                if (inputStream != null) {
                    if (var2 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var19) {
                            var2.addSuppressed(var19);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }
        } catch (IOException var22) {
            throw new AGCException("Failed to parse credential file.", var22);
        }
    }


    private static CredentialService buildClientIdCredential(byte[] bytes) {
        return (CredentialService) JSONUtils.readValue(new String(bytes, StandardCharsets.UTF_8), ClientIdCredential.class);
    }

}
