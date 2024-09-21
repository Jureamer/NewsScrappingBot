package com.example.mknewsscrappingbot.common.constant;

public class MessageConstants {
    public static final String UNKNOWN_COMMAND = "알 수 없는 명령어입니다. '/help' 을 입력하여 사용 가능한 명령어를 확인해 주세요.";
//    public static final String AVAILABLE_COMMAND = "사용 가능한 명령어: /뉴스";
    public static final String NEWS_FETCHING_ERROR = "뉴스를 가져오는 중 오류가 발생했습니다.";
    public static final String NEED_CATEGORY = "카테고리를 입력해 주세요. 예: !뉴스 경제";
    public static final String WAIT_FOR_MESSAGE = "메시지 대기";
    public static final String NEWS_PRINT_INFO = "뉴스를 출력합니다.";

    public static String getNewsFetchingMessage(String name, String category) {
        return "[" + name + "] " + category + " 뉴스를 가져옵니다.";
    }

    public static String getAvailableCategoryMessage(String category) {
        return "사용 가능한 카테고리: " + category;
    }
}
