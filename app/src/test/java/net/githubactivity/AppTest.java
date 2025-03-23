package net.githubactivity;

import org.junit.Test;

import java.io.IOException;
import java.io.InterruptedIOException;

import static org.junit.Assert.*;

public class AppTest {
    @Test public void noUserTest() throws IOException, InterruptedException {
        try {
            assertThrows(InterruptedIOException.class, () -> HttpConnection.response("asddsadsddas"));
        } catch (Exception ignored) {}
    }
}
