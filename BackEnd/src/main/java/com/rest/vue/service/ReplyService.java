package com.rest.vue.service;

import com.rest.vue.entities.Category;
import com.rest.vue.entities.CategoryDTO;
import com.rest.vue.entities.Reply;
import com.rest.vue.entities.ReplyDTO;

import java.util.List;

public interface ReplyService {


    Reply createReply(Long id, String content);

    Reply save(Reply reply);

    List<ReplyDTO> createListReplyDTO(List<Reply> list);

    ReplyDTO makeReplyDTO(Reply saved);
}
