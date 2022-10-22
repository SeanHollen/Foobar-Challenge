import java.util.Arrays;

public class Solution {
    public static String[] solution(String[] l) {
        Version[] versions = new Version[l.length];
        for (int i = 0; i < l.length; i++) {
            versions[i] = getVersion(l[i]);
        }
        Arrays.sort(versions);
        for (int i = 0; i < versions.length; i++) {
            l[i] = versions[i].asString();
        }
        return l;
    }
    
    private static Version getVersion(String asString) {
        String[] split = asString.split("\\.");
        if (split.length == 3) {
            return new Version(asString, 
                Integer.parseInt(split[0]), 
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]));
        } else if (split.length == 2) {
            return new Version(asString, 
                Integer.parseInt(split[0]), 
                Integer.parseInt(split[1]));
        } else {
            return new Version(asString, 
                Integer.parseInt(split[0]));
        }
    }
    
    private static class Version implements Comparable<Version> {

        String asString;
        int major;
        int minor;
        int revision;

        public Version(String asString, int major, int minor, 
                int revision) {
            this.asString = asString;
            this.major = major;
            this.minor = minor;
            this.revision = revision;
        }

        public Version(String asString, int major, int minor) {
            this(asString, major, minor, -1);
        }

        public Version(String asString, int major) {
            this(asString, major, -1, -1);
        }

        public String asString() {
            return this.asString;
        }

        public int compareTo(Version other) {
            if (this.major != other.major) {
                return this.major - other.major;
            } else if (this.minor != other.minor) {
                return this.minor - other.minor;
            } else {
                return this.revision - other.revision;
            }
        }
    }
}