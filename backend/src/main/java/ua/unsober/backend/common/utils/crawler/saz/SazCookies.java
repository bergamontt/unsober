package ua.unsober.backend.common.utils.crawler.saz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class SazCookies {
    private Map<String, String> cookies;
}