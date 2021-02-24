package com.rest.vue.service;

import com.rest.vue.entities.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ReplyService {

    Reply createReply(Long id, String content);

    Reply save(Reply reply);

    List<ReplyDTO> createListReplyDTO(List<Reply> list);

    ReplyDTO makeReplyDTO(Reply saved);

    Reply updateReply(Long id, String content);

    boolean deleteReply(Long id, HttpServletRequest request);
}
