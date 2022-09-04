import java.sql.Timestamp;
import java.time.Instant;

public class LogEntry {
    private final Timestamp timestamp;
    private final String file;
    private final boolean result;

    public LogEntry(String file, boolean result) {
        this.timestamp = Timestamp.from(Instant.now());
        this.file = file;
        this.result = result;
    }

    @Override
    public String toString() {
        return timestamp.toString() + " " + file + " Result: " + (result ? "Success" : "Failed");
    }
}
