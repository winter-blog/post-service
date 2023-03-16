package com.devwinter.postservice.adapter.input.api.document;

public enum PostApiDocumentInfo {
    IMAGE_UPLOAD("이미지", "이미지 업로드", "이미지 업로드", "이미지 업로드 API", true),
    POST_REGISTER("게시글", "게시글 등록", "게시글 등록", "게시글 등록 API", true),
    POST_DELETE("게시글", "게시글 삭제", "게시글 삭제", "게시글 삭제 API", true),
    POST_DETAIL_FIND("게시글", "게시글 상세 조회", "게시글 상세 조회", "게시글 상세 조회 API", true)
    ;

    private final String tag;
    private final String identifier;
    private final String summary;
    private final String description;
    private final boolean certification;

    PostApiDocumentInfo(String tag, String identifier, String summary, String description, boolean certification) {
        this.tag = tag;
        this.identifier = identifier;
        this.summary = summary;
        this.description = description;
        this.certification = certification;
    }

    public String getTag() {
        return tag;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCertification() {
        return certification;
    }
}
