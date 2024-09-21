package com.example.mknewsscrappingbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class HelpCommand extends AbstractCommand{
    public HelpCommand() {
        super(null, null, "HELP");
    }

    @Override
    public void execute(TextChannel textChannel, String content) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("도움말")
                .setDescription("사용 가능한 명령어 목록입니다.")
                .addField("매일경제: 카테고리별 조회 수 Top 10 뉴스를 출력합니다.", "/mk {카테고리} [경제, 비즈니스, 사회, 세계, 부동산, 주식, IT, 정치, 문화, 스포츠]", false)
                .addBlankField(false)
                .addField("한국경제: 카테고리별 최신뉴스 Top 10 뉴스를 출력합니다.", "/hk {카테고리} [오피니언, 경제, 정치, 사회, 증권, 부동산, 국제, IT, 생활문화, 스포츠, 연예]", false)
                .addBlankField(false)
                .addField("조선일보: 카테고리별 조회 수 Top 5 ~ 10 뉴스를 출력합니다.", "/cs {카테고리} [정치, 사회, 국제, 건강, 재테크, 스포츠, 문화, 연예, 쇼핑]", false)
                .addBlankField(false)
                .addField("중앙일보: 카테고리별 조회 수 Top 5 뉴스를 출력합니다.", "/ja {카테고리} [경제, 사회, 국제, 정치, 문화, 스포츠, 라이프, 피플]", false)
                .addBlankField(false)
                .addField("동아일보: 카테고리별 조회 수 Top 5 뉴스를 출력합니다.", "/da {카테고리} [정치, 경제, 사회, 국제, 문화, 연예, 스포츠, 헬스]", false);

        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
