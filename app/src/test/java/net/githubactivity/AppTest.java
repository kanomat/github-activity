package net.githubactivity;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AppTest {
    @Test public void noUserTest() throws IOException, InterruptedException {
        assertNull(GithubParser.parseGithubJson(HttpConnection.response("")));
    }
}
