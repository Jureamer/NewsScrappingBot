package com.example.mknewsscrappingbot.data;

public class MessageConstants {
    public static final String UNKNOWN_COMMAND = "알 수 없는 명령어입니다.";
    public static final String AVAILABLE_CATEGORY = "사용 가능한 카테고리: 경제, 비즈니스, IT, 사회, 세계, 부동산, 주식, 정치, 문화, 스포츠 ";
    public static final String NEWS_FETCHING_ERROR = "뉴스를 가져오는 중 오류가 발생했습니다.";
    public static final String NEED_CATEGORY = "카테고리를 입력해 주세요. 예: !뉴스 경제";
    public static final String WAIT_FOR_MESSAGE = "메시지 대기 중";

    public static String getNewsFetchingMessage(String category) {
        return category + " 뉴스를 가져옵니다.";
    }
}
