package dart.insight.global.azure;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AuthHelper {

    private List<String> scopes = Arrays.asList("https://graph.microsoft.com/.default");

    private static TokenCredentialAuthProvider authProvider = null;

    private GraphServiceClient initializeGraphAuth(String clientId, String userName, String userPassword) {
        // Create the auth provider
        UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
                .clientId(clientId)
                .username(userName)
                .password(userPassword)
                .build();

        authProvider = new TokenCredentialAuthProvider(scopes, usernamePasswordCredential);

        // Create the graph client
        GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(authProvider)
                        .buildClient();

        // Create default logger to only log errors
        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.ERROR);

        return graphClient;
    }

    public GraphServiceClient getGraphClient(String clientId, String userName, String userPassword) {
        return initializeGraphAuth(clientId, userName, userPassword);
    }

//    public String getUserAccessToken()
//    {
//        initializeGraphAuth();
//        try {
//            URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
//            return authProvider.getAuthorizationTokenAsync(meUrl).get();
//        } catch(Exception ex) {
//            return null;
//        }
//    }

//    public User getUser() {
//        initializeGraphAuth();
//        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();
//
//        // GET /me to get authenticated user
//        User me = graphClient
//                .me()
//                .buildRequest()
//                .select("displayName,mailboxSettings")
//                .get();
//
//        System.out.println(me.displayName);
//        return me;
//    }
}
