package temp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/30 0:53
 */
public class Style {
    private static Logger log = LoggerFactory.getLogger(Style.class);

    public static void main(String[] args) {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }
}
