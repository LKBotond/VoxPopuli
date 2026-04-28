import { useState } from "react";
import { buildResponseTrees } from "../../utils/comments";
import type { CommentRequest, CommentResponse } from "../../contracts/Comment";
import { sendMessage } from "../../api/VoxPopuliApi";
import * as UI from "../Index";
import type { CommentMessage } from "../../types/MessageTypes";

export interface CommentViewProps {
  comments: CommentResponse[];
  userAlias: string;
}

export function CommentView({ comments, userAlias }: CommentViewProps) {
  const [commentList, setCommentList] = useState(comments);
  const roots = buildResponseTrees(commentList);

  const buildCommentRequest = (
    parentId: string | undefined,
    content: string,
    userAlias: string,
  ): CommentRequest => {
    return {
      parentId,
      content,
      alias: userAlias,
      sourceLinkHash: window.location.href,
      updatedAt: new Date().toISOString(),
    };
  };

  const buildCommentMessage = (request: CommentRequest): CommentMessage => {
    return {
      action: "comment",
      payload: request,
    };
  };

  const handleComment = async (
    parentId: string | undefined,
    content: string,
    userAlias: string,
  ) => {
    const commentRequest = buildCommentMessage(
      buildCommentRequest(parentId, content, userAlias),
    );
    const response: CommentResponse = await sendMessage(commentRequest);
    setCommentList((prev) => [...prev, response]);
  };

  return (
    <UI.Section className="grid-cols-5">
      <UI.Div className="col-span-3"></UI.Div>
      <UI.Div className="col-span-2 bg-gray-900">
        <UI.H>Vox Populi</UI.H>
        <UI.P>Let the people hear your voice</UI.P>
        <UI.CommentForm
          handleComment={(parentId, content) => {
            handleComment(parentId, content, userAlias);
          }}
        ></UI.CommentForm>

        {roots.map((rootNode) => (
          <UI.Comment
            key={rootNode.commentId}
            comment={rootNode}
            onReply={(parentId, content) => {
              handleComment(parentId, content, userAlias);
            }}
          />
        ))}
      </UI.Div>
    </UI.Section>
  );
}
